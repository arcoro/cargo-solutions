FROM gradle:8.8.0-jdk22-alpine AS build
WORKDIR /app
COPY . ./
RUN chmod +x ./gradlew