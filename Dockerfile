FROM amazoncorretto:17-alpine3.19-jdk

WORKDIR /service

EXPOSE 8080

COPY target/preguntonic-*.jar /service/bin/preguntonic.jar

HEALTHCHECK --interval=30s --timeout=10s \
  CMD curl -sSfI --retry 5 http://localhost:8080/actuator/health || exit 1

ENV TZ="UTC"

# Volume containing the H2 data
VOLUME /usr/lib/h2

ENTRYPOINT ["java", "-jar", "/service/bin/preguntonic.jar"]
