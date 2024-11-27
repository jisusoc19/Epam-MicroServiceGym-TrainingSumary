FROM openjdk:23-jdk-slim
ARG JAR_FILE=target/TaskMicro-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} TaskMicro.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/TaskMicro.jar"]