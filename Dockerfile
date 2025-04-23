# --- Builder Stage ---
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app
COPY . .

# Build the JAR that includes dependencies
RUN ./gradlew clean jar

# --- Runtime Stage ---
FROM eclipse-temurin:21-jdk AS runtime

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/patchwork-1.0-SNAPSHOT.jar /app/patchwork.jar
COPY src/main/resources/books.json /app/books.json

# Set an env var (optional, your app reads this)
ENV BOOKS_FILE_PATH=/app/books.json

# Run the Kotlin app
CMD ["java", "-jar", "/app/patchwork.jar"]
