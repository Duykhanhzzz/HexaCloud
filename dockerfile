# syntax=docker/dockerfile:1
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
<<<<<<< HEAD
ENTRYPOINT ["java", "-jar", "app.jar"]
=======
ENTRYPOINT ["java", "-jar", "app.jar"]
>>>>>>> 50be77e0df70c43922d8c8ad68a2ba9371bf70a0
