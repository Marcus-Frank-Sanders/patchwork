@file:JvmName("AdminKt")
package org.patchwork

fun admin(books: List<Book>, userInput: () -> String) {
    var stayInAdmin = true
    while (stayInAdmin) {
        println(
            "\nAdministration Menu:" +
                    "\nType: t (Checked out books total), s (Summary), or b (Back):"
        )
        when (userInput().lowercase().trim()) {
            "t" -> println("Total checked out books: ${getCheckedOutBooks(books).count()}")
            "s" -> {
                println("\nChecked out books:")
                getCheckedOutBooks(books).forEachIndexed { i, book ->
                    println("[$i] - ${book.title} by ${book.author} (ISBN: ${book.isbn})")
                }
            }
            "b" -> {
                println("Returning to main menu.")
                stayInAdmin = false
            }
            else -> println("Invalid input.")
        }
    }
}

fun checkBookStatus(filteredBooks: List<Book>): List<Book> {
    val availableBooks = mutableListOf<Book>()
    if (filteredBooks.isEmpty()) {
        println("No books found for your search.")
        return availableBooks
    }

    var index = 0
    filteredBooks.forEach { book ->
        when {
            !book.isAvailable -> println("[Checked Out] ${book.title} by ${book.author} (ISBN: ${book.isbn})")
            book.isReference -> println("[Reference Only] ${book.title} by ${book.author} (ISBN: ${book.isbn})")
            else -> {
                println("[Index: $index] ${book.title} by ${book.author} (ISBN: ${book.isbn})")
                availableBooks.add(book)
                index++
            }
        }
    }
    return availableBooks
}

