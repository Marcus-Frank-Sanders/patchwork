# Stage 1: Compile Kotlin with JDK 21
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Install Kotlin compiler
RUN apt-get update && apt-get install -y curl unzip && \
    curl -s https://get.sdkman.io | bash && \
    bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install kotlin"

# Copy source code
COPY src /app/src

# Compile Kotlin sources manually
RUN mkdir -p /app/out && \
    /root/.sdkman/candidates/kotlin/current/bin/kotlinc /app/src/main/kotlin -include-runtime -d /app/out/patchwork.jar

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/out/patchwork.jar /app/patchwork.jar
COPY src/main/resources/books.json /app/books.json

ENV BOOKS_FILE_PATH=/app/books.json

CMD ["java", "-jar", "patchwork.jar"]
