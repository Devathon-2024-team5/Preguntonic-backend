FROM maven:3.9.3-eclipse-temurin-17-alpine@sha256:1cbc71cb8e2f594338f4b4cbca897b9f9ed6183e361489f1f7db770d57efe839 AS builder

WORKDIR /workdir

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.19-jdk

WORKDIR /service

EXPOSE 8080

COPY --from=builder /workdir/target/preguntonic-*.jar /service/bin/preguntonic.jar
#COPY target/preguntonic-*.jar /service/bin/preguntonic.jar

HEALTHCHECK --interval=30s --timeout=10s \
  CMD curl -sSfI --retry 5 http://localhost:8080/actuator/health || exit 1

ENV TZ="UTC"

# Volume containing the H2 data
VOLUME /usr/lib/h2

ENTRYPOINT ["java", "-jar", "/service/bin/preguntonic.jar"]
