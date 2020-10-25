# Alpine Linux with OpenJDK JRE
FROM adoptopenjdk/openjdk11:alpine-slim

# port
EXPOSE 8080/tcp

# Build Arguments
ARG ARTIFACT_ID=java
ARG VERSION=0.0.1

# copy JAR into image
RUN mkdir /opt/app
COPY target/${ARTIFACT_ID}-${VERSION}.jar /opt/app/service.jar

# run application with this projection line
CMD java -jar $JAVA_OPTIONS /opt/app/service.jar