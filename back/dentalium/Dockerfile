# Etapa 1: Construccion del proyecto
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:17

WORKDIR /app

COPY --from=build /app/target/dentalium.jar ./dentalium.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx400m", "-Xms256m", "-XX:+UseContainerSupport", "-jar", "dentalium.jar"]
