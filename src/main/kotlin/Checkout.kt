package org.patchwork

fun checkoutBooks(selectedBooks: List<Book>, allBooks: MutableList<Book>, userInput: () -> String) {
    if (selectedBooks.isEmpty()) {
        println("No books available to check out.")
        return
    }

    println("Enter index(es) to check out (comma-separated), or 'c' to cancel:")
    val input = userInput().trim()
    if (input.lowercase() in listOf("c", "cancel")) {
        println("Checking out cancelled.")
        return
    }

    val indexes = input.split(",").mapNotNull { it.trim().toIntOrNull() }
    val selected = indexes.mapNotNull { selectedBooks.getOrNull(it) }

    if (selected.isEmpty()) {
        println("No valid selections.")
        return
    }

    selected.forEach { selectedBook ->
        val index = allBooks.indexOfFirst { it.isbn == selectedBook.isbn }
        if (index != -1) {
            allBooks[index] = selectedBook.copy(isAvailable = false)
        }
    }
    println("Checked out books:")
    selected.forEach {
        println("- ${it.title} by ${it.author}")

    }
}