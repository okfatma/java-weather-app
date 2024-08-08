# Faz 1: Maven ile Build
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

# Faz 2: JAR dosyasını çalıştıran imaj
FROM openjdk:21
COPY --from=build /app/target/weather-app-0.0.1-SNAPSHOT.jar /app/weather-app.jar
CMD java -jar /app/weather-app.jar