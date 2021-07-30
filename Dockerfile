FROM openjdk:16

ARG JAR_FILE=app/spring-app/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]