# Library (CLI App)

Welcome to the **Library**, a simple Kotlin-based console application that allows users to:

- Search for books by **Author**, **Title**, or **ISBN**
- Borrow available books (excluding reference books)
- Admin mode to view total and summary of books that are checked out

## Tech Stack

- **Language:** Kotlin
- **Build Tool:** Gradle (Kotlin DSL)
- **Serialization:** Kotlinx Serialization
- **Data Storage:** Local JSON file (`books.json`)
- **Testing:**
    - [Kotest](https://kotest.io/) for unit test coverage
    - [Mockk](https://mockk.io/) for mocking interfaces and handling functions with side effects

## Getting Started

### Prerequisites

Make sure you have the following installed:

- [JDK 17+](https://adoptium.net/) (JDK 17–21 recommended)
- [Gradle](https://gradle.org/install/)
- IntelliJ IDEA (optional, but recommended)

## Project Setup

Clone the repository:

```bash
git clone git clone https://github.com/marcus-frank-sanders/patchwork.git
cd ../patchwork
```

---

## Build & Run

You can run the app using Gradle or the Gradle Wrapper:

#### Run with Gradle Wrapper

```bash
./gradlew clean build
```
```bash
./gradlew run
```

#### Make sure to set the environment variable if needed:

```dtd
export BOOKS_FILE_PATH=/path/to/books.json
```

#### Run Tests

```bash
./gradlew test
```

### Running with Docker

#### Build the Docker image

```bash
docker build -t patchwork-app .
```

#### Run the CLI app

```bash
docker docker run --rm -it patchwork-app
```

#### Run with a mounted books.json file

```bash
docker run --rm -it \
-v $(pwd)/books.json:/app/books.json \
-e BOOKS_FILE_PATH=/app/books.json \
patchwork-app
```

---

## Technical Debt

Due to the accelerated delivery timeline of this project, several areas of technical debt have emerged. These should be
addressed in future development cycles to improve maintainability, scalability, and testability.

### Identified Issues

- **Functions with Side Effects**  
  Some functions produce hidden or unintended side effects, making them harder to test, reason about, and reuse safely.

- **Tight Coupling of Display and Flow Logic**  
  Business logic and UI rendering are not consistently decoupled, leading to increased complexity and reduced
  flexibility.

- **Limited Delegation for Future Enhancements**  
  Some components lack proper abstraction or delegation patterns, making the system harder to extend or modify cleanly.

- **Inadequate Test Coverage**
    - **Unit Tests:** Minimal test coverage reduces confidence during refactoring and limits detection of regressions.
    - **End-to-End (Black Box) Tests:** There is a notable absence of comprehensive E2E tests to validate full system
      behavior from a user perspective.

---

## To-Do / Enhancements

- **Book Return Functionality**  
  Allow users to return borrowed books and update availability in `books.json`.

- **Admin Authentication**  
  Secure admin tasks with login credentials or a simple password-based gate.

- **Track Borrow & Due Dates**  
  Store the date when a book is borrowed and calculate its due date (e.g., 14 days later).

- **Admin: Fine Management**  
  Introduce fines for late returns and a system to view/pay them.

- **Log Transactions to File**  
  Record all borrowing and return activity to a separate log file (`logs.txt`), including timestamps.
