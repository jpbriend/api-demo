FROM openjdk:8-jdk-alpine

MAINTAINER Julien Corioland <jucoriol@microsoft.com>

ARG VCS_REF
ARG BUILD_DATE

# Metadata
LABEL org.label-schema.vcs-ref=$VCS_REF \
      org.label-schema.vcs-url="https://github.com/jcorioland/api-demo" \
      org.label-schema.build-date=$BUILD_DATE \
      org.label-schema.docker.dockerfile="/Dockerfile"

COPY ./target/api-demo-0.0.1-SNAPSHOT.jar /opt/app.jar

EXPOSE 8080
WORKDIR /opt

CMD ["java", "-jar", "app.jar"]