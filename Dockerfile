FROM eclipse-temurin:24-jre-alpine

WORKDIR /app

ENV TZ=America/Sao_Paulo

COPY target/logs-docker-api.jar logs-docker-api.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "logs-docker-api.jar"]