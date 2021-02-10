FROM openjdk:10-jdk
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java" ,"-jar","/app.jar"]