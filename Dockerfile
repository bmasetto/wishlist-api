FROM openjdk:16-alpine3.13

ARG JAR_FILE=app/spring-app/target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]