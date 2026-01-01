server {
    listen {{ .interface }}:{{ .port }} default_server;

    include /etc/nginx/includes/server_params.conf;
    include /etc/nginx/includes/proxy_params.conf;
    
    location /netflow {
        allow   172.30.32.2;
        deny    all;

        proxy_pass http://netflow;
    }

    location / {
        allow   172.30.32.2;
        deny    all;

        proxy_pass http://backend;
        
        #enable ntop in iframe
        proxy_hide_header X-Frame-Options;

        # ensure filter compatibility with compressed responses
        proxy_set_header Accept-Encoding "";
        # enable hassio ingress url substitution in all sources
        sub_filter_types *;
        # replace prefix path
        sub_filter "/ntopng_prefix" "$http_x_ingress_path";
        sub_filter "&#47;ntopng_prefix" "$http_x_ingress_path";
        sub_filter_once off;

        # in case substitution doesn't work (i.e. redirects)
        rewrite ^/ntopng_prefix/(.*) /$http_x_ingress_path/$1 break;
    }
}
