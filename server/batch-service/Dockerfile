FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/batch-service-1.0.jar BatchService.jar

ENTRYPOINT ["java", "-jar", "BatchService.jar"]
