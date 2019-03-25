# FROM openjdk:11-jdk
FROM maven:3.6.0-jdk-11

RUN mvn clean package -DskipTests

COPY target/*.jar /usr/share/service/application.jar
CMD ["/usr/bin/java", "-jar", "/usr/share/service/application.jar"]
