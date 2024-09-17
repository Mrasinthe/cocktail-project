# Use the official Node.js 20 image (alpine variant for smaller image size)
FROM node:20-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json first to leverage Docker layer caching
COPY package.json package-lock.json ./

# Install the project dependencies
RUN npm install

# Copy the rest of the application code to the container
COPY . .

# Build the React application
RUN npm run build

# Expose the port the app runs on (React development server runs on port 3000 by default)
EXPOSE 3000

# Start the application using the start script defined in package.json
CMD ["npm", "start"]
