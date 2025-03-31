# https://developers.home-assistant.io/docs/add-ons/configuration#add-on-dockerfile
ARG BUILD_FROM
FROM ${BUILD_FROM}

ARG BUILD_ARCH
ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION

ARG TAPTAP_VERSION
ARG TAPTAP_MQTT_VERSION

WORKDIR /tmp

# Install python/pip
RUN apk upgrade --no-cache && apk add --no-cache python3 py3-pip

# Get taptap binary
RUN \
    if [ "${BUILD_ARCH}" = "aarch64" ]; then \
        TAPTAP_ARCH="musl-arm64"; \
    elif [ "${BUILD_ARCH}" = "amd64" ]; then \
        TAPTAP_ARCH="musl-x86_64"; \
    elif [ "${BUILD_ARCH}" = "armhf" ]; then \
        TAPTAP_ARCH="musleabihf-arm"; \
    elif [ "${BUILD_ARCH}" = "armv7" ]; then \
        TAPTAP_ARCH="musleabihf-armv7"; \
    elif [ "${BUILD_ARCH}" = "i386" ]; then \
        TAPTAP_ARCH="musl-i686"; \
    else \
        exit 1; \
    fi \
    && mkdir /usr/bin/taptap \
    && chmod 755 /usr/bin/taptap \
    && curl -sSLf -o /tmp/taptap.tgz \
    "https://github.com/litinoveweedle/taptap/releases/download/v${TAPTAP_VERSION}/taptap-Linux-${TAPTAP_ARCH}.tar.gz" \
    && tar -xzvf taptap.tgz \
    && cp taptap /usr/bin/taptap/ \
    && chmod 755 /usr/bin/taptap/taptap \
    && rm -Rf /tmp/*

# Get taptap-mqtt bridge
RUN \
    mkdir /etc/taptap \
    && chmod 755 /etc/taptap \
    && curl -sSLf -o /tmp/taptap-mqtt.tgz \
    "https://github.com/litinoveweedle/taptap-mqtt/archive/refs/tags/v${TAPTAP_MQTT_VERSION}.tar.gz" \
    && tar -xzvf taptap-mqtt.tgz \
    && cp taptap-mqtt-*/taptap-mqtt.py /usr/bin/taptap/ \
    && chmod 755 /usr/bin/taptap/taptap-mqtt.py \
    && pip install -r taptap-mqtt-*/requirements.txt \
    && rm -Rf /tmp/*

# Add user for taptap daemon
RUN addgroup -S taptap && adduser -S taptap -G taptap -h /run/taptap -H

# Copy root filesystem
COPY rootfs /
RUN chmod 775 /etc

# Labels
LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION} \
    maintainer="Dominik Strnad <litinoveweedle@gmail.com>" \
    org.opencontainers.image.title="${BUILD_NAME}" \
    org.opencontainers.image.description="${BUILD_DESCRIPTION}" \
    org.opencontainers.image.authors="Dominik Strnad <litinoveweedle@gmail.com>" \
    org.opencontainers.image.licenses="Apache 2.0" \
    org.opencontainers.image.url="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.source="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.documentation="https://github.com/${BUILD_REPOSITORY}/blob/main/README.md" \
    org.opencontainers.image.created=${BUILD_DATE} \
    org.opencontainers.image.revision=${BUILD_REF} \
    org.opencontainers.image.version=${BUILD_VERSION}
