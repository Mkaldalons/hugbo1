FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

RUN apt-get update && apt-get install -y git

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Copy the database setup files
COPY database/database_setup.sh /app/database/database_setup.sh
COPY database/learningSquare.sql /app/database/learningSquare.sql
COPY database/insertData.sql /app/database/insertData.sql

# Install SQLite3 and make the script executable
RUN apt-get update && apt-get install -y sqlite3 && \
    chmod +x /app/database/database_setup.sh

# Run the database setup script
RUN /app/database/database_setup.sh

# Expose the application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]