# Stage 1: Build the React app using Node.js
FROM node:18 AS build

# Install Git
RUN apt-get update && apt-get install -y git

RUN npm install -g npm

# Set the working directory for the build
WORKDIR /app

# Set the environment variable to handle OpenSSL issues
ENV NODE_OPTIONS=--openssl-legacy-provider

# Copy the package.json and package-lock.json (if available) before other files
# This takes advantage of Docker layer caching for npm installs
COPY package*.json ./

# Install NPM dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React app
RUN npm run build

# Stage 2: Use Nginx to serve the static files
FROM nginx:alpine

# Copy built files from the previous build stage to the Nginx web directory
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80 to the host machine
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]