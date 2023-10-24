FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/chat-service-1.0.jar ChatService.jar

ENTRYPOINT ["java", "-jar", "ChatService.jar"]
