FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/openai-service-1.0.jar OpenaiService.jar

ENTRYPOINT ["java", "-jar", "OpenaiService.jar"]
