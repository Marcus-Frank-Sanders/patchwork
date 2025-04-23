@file:JvmName("LibraryKt")

package com.patchwork

fun library(books: MutableList<Book>, filePath: String, userInput: () -> String) {
    println("Welcome to the library!")

    var continueRunning = true
    while (continueRunning) {
        println(
            "\nAre you here to search for a book, or perform an administrative task?" +
                    "\nPlease type: s (Search), a (Admin), or e (Exit) to leave:"
        )

        when (userInput().lowercase().trim()) {
            "search", "s" -> search(books, userInput)
            "admin", "a" -> admin(books, userInput)
            "exit", "e" -> {
                println("Thanks for visiting the library. Goodbye!")
                updateBooks(filePath, books)
                continueRunning = false
            }

            else -> println("Invalid option. Please try again.")
        }
    }
}