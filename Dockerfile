FROM openjdk:11-jdk

COPY target/*.jar /usr/share/service/application.jar
CMD ["/usr/bin/java", "-jar", "/usr/share/service/application.jar"]
