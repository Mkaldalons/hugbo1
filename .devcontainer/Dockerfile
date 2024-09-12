FROM node:16 AS devcontainer

# Install Java for Spring Boot development
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Set up the working directory
WORKDIR /workspace

# Install NPM dependencies for the frontend
COPY frontend/package*.json ./frontend/
RUN npm install --prefix frontend

# Set up the Spring Boot environment
COPY backend/pom.xml backend/
WORKDIR /workspace/backend
RUN mvn dependency:resolve

# Final working directory
WORKDIR /workspace