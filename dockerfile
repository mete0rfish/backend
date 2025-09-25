# Stage 1: Build the application
FROM gradle:jdk17 AS builder
WORKDIR /app
COPY . .
RUN cd server && ./gradlew build -x test

# Stage 2: Create the final image
FROM eclipse-temurin:17.0.8_7-jdk
WORKDIR /app
COPY --from=builder /app/server/build/libs/*SNAPSHOT.jar onetool-server.jar
ENTRYPOINT ["java", "-jar", "onetool-server.jar"]