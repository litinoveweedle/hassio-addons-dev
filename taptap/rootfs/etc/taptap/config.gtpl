[MQTT]
SERVER = {{ .mqtt_server }}
PORT = {{ .mqtt_port }}
QOS = {{ .mqtt_qos }}
TIMEOUT = {{ .mqtt_timeout }}
{{ if ne .mqtt_user "null" }}
USER = {{ .mqtt_user }}
{{ else }}
USER = 
{{ end }}
{{ if ne .mqtt_pass "null" }}
PASS = {{ .mqtt_pass }}
{{ else }}
PASS = 
{{ end }}


[TAPTAP]
LOG_LEVEL = {{ .taptap_log_level }}
BINARY = /usr/bin/taptap/taptap
{{ if ne .taptap_serial "null" }}
SERIAL = {{ .taptap_serial }} 
ADDRESS =
{{ else if ne .taptap_address "null" }}
SERIAL =
ADDRESS = {{ .taptap_address }}
{{ else }}
SERIAL =
ADDRESS =
{{ end }}
{{ if .taptap_port }}
PORT = {{ .taptap_port }}
{{ else }}
PORT = 502
{{ end }}
MODULES_SERIALS = {{ .taptap_modules_serials }}
TOPIC_PREFIX = {{ .taptap_topic_prefix }}
TOPIC_NAME = {{ .taptap_topic_name }}
{{ if .taptap_timeout }}
TIMEOUT = {{ .taptap_timeout }}
{{ else }}
TIMEOUT = 180
{{ end }}
{{ if .taptap_update }}
UPDATE = {{ .taptap_update }}
{{ else }}
UPDATE = 10
{{ end }}
PERSISTENT_FILE = /data/taptap.json


[HA]
{{ if .ha_discovery_prefix }}
DISCOVERY_PREFIX = {{ .ha_discovery_prefix }}
{{ else }}
DISCOVERY_PREFIX = homeassistant
{{ end }}
DISCOVERY_LEGACY = false
{{ if .ha_birth_topic }}
BIRTH_TOPIC = {{ .ha_birth_topic }}
{{ else }}
BIRTH_TOPIC = homeassistant/status
{{ end }}
{{ if .ha_entity_availability }}
ENTITY_AVAILABILITY = {{ .ha_entity_availability }}
{{ else }}
ENTITY_AVAILABILITY = true
{{ end }}


[RUNTIME]
MAX_ERROR = 0
STATE_FILE = /run/taptap/taptap.state
