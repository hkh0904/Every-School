FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/consult-service-1.0.jar ConsultService.jar

ENTRYPOINT ["java", "-jar", "ConsultService.jar"]
