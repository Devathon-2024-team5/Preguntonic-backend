FROM openjdk:17-jdk-slim-buster

WORKDIR /service

EXPOSE 8080

COPY target/preguntonic-*.jar /service/bin/preguntonic.jar

ENTRYPOINT ["java", "-jar", "/service/bin/preguntonic.jar"]
