# Stage 1: Build the React app using Node.js
FROM node:18 AS build

RUN apt-get update && apt-get install -y git

# Copy the package.json and install dependencies
COPY package*.json ./
RUN npm install

# Set the environment variable to handle OpenSSL issues
ENV NODE_OPTIONS=--openssl-legacy-provider

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Use Nginx to serve the static files
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]