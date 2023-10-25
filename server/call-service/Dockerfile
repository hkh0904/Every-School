FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/call-service-1.0.jar CallService.jar

ENTRYPOINT ["java", "-jar", "CallService.jar"]