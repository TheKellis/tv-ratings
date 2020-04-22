FROM openjdk:8-jdk
USER root
WORKDIR /app
RUN apt-get update
RUN apt-get -yq install wget sqlite3
EXPOSE 8080
RUN bash -c "bash"