# FROM openjdk:11-jdk
FROM maven:3.6.0-jdk-11

COPY . /app
WORKDIR /app

RUN mvn clean package -DskipTests

RUN cp target/*.jar target/application.jar

CMD ["/usr/bin/java", "-jar", "target/application.jar"]
