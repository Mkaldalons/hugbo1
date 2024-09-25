# Stage 1: Build the application using Maven (or use Gradle depending on your setup)
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

RUN apt-get update && apt-get install -y git

# Stage 2: Use a lightweight JDK image to run the Spring Boot app
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]