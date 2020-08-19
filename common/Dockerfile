FROM openjdk:8-jdk-alpine
RUN  apk alpine-linux-openjdk8 upgrade && apk update && apk upgrade && apk add netcat-openbsd && apk add tar

RUN mkdir -p /usr/local/paperless-common
RUN mkdir -p /usr/local/dockerize

COPY docker/dockerize-alpine.tar.gz /usr/local/dockerize/dockerize-alpine.tar.gz
RUN tar -C /usr/local/bin -xzvf /usr/local/dockerize/dockerize-alpine.tar.gz \
    && rm /usr/local/dockerize/dockerize-alpine.tar.gz

ADD target/paperless-common-services-0.0.1-SNAPSHOT.jar /usr/local/paperless-common
ADD docker/run.sh run.sh
RUN chmod +x run.sh
CMD /run.sh