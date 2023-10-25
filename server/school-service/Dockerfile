FROM openjdk:17-ea-11-jdk-slim

VOLUME /tmp

COPY build/libs/school-service-1.0.jar SchoolService.jar

ENTRYPOINT ["java", "-jar", "SchoolService.jar"]