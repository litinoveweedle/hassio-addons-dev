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

        # replace hassio ingress url in all response content
        sub_filter_types *;
        # replace prefix path
        sub_filter "/ntopng_prefix" "$http_x_ingress_path";
        sub_filter "&#47;ntopng_prefix" "$http_x_ingress_path";
        sub_filter_once off;

        # replace hassio ingress url in the response headers
        absolute_redirect off;
	    proxy_redirect ~^.*/ntopng_prefix(.*)$ $http_x_ingress_path$1;
    }
}
