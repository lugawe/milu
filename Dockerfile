FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/milu-backend-0.1.jar /app/milu-backend-0.1.jar
COPY --from=build /app/target/libs /app/libs
COPY src/main/resources/example-server-h2.yml /app/example-server-h2.yml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/milu-backend-0.1.jar", "server", "/app/example-server-h2.yml"]
