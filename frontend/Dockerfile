# Use lightweight Node.js 18 (Alpine version)
FROM node:20-alpine AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json first (for better caching)
COPY package*.json ./

# Install dependencies with a clean cache
RUN npm ci --omit=dev

# Copy the rest of the application files
COPY . .

# Build the React ap
RUN npm run build

# Use a lightweight web server (Nginx) to serve the built app
FROM nginx:alpine

# Set the working directory inside the container
WORKDIR /usr/share/nginx/html

# Remove default Nginx static files
RUN rm -rf ./*

# Copy the built React app from the previous stage
COPY --from=build /app/build .

# Expose port 80 for web traffic
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
