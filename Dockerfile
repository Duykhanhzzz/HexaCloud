# syntax=docker/dockerfile:1
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/certs/ca.pem /app/certs/ca.pem   
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
