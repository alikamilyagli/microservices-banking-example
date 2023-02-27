FROM joengenduvel/jre17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8003
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/application.jar"]
