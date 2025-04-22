@file:JvmName("SearchKt")
package org.patchwork

fun search(books: MutableList<Book>, userInput: () -> String) {
    var keepSearching = true
    while (keepSearching) {
        println(
            "\nSearch by: a (Author), t (Title), i (ISBN), or b (Back):"
        )
        when (userInput().lowercase().trim()) {
            "a", "author" -> {
                println("Enter author name:")
                searchByAuthor(userInput(), books, userInput)
            }
            "t", "title" -> {
                println("Enter title:")
                searchByTitle(userInput(), books, userInput)
            }
            "i", "isbn" -> {
                println("Enter ISBN:")
                searchByISBN(userInput(), books, userInput)
            }
            "b", "back" -> {
                println("Returning to main menu.")
                keepSearching = false
            }
            else -> println("Invalid search type. Try again.")
        }
    }
}

fun searchByAuthor(author: String, books: MutableList<Book>, userInput: () -> String) {
    val filtered = filterBooksByAuthor(books, author)
    val available = checkBookStatus(filtered)
    checkoutBooks(available, books, userInput)
}

fun searchByTitle(title: String, books: MutableList<Book>, userInput: () -> String ) {
    val filtered = filterBooksByTitle(books, title)
    val available = checkBookStatus(filtered)
    checkoutBooks(available, books, userInput)
}

fun searchByISBN(isbn: String, books: MutableList<Book>, userInput: () -> String) {
    if (isbn.matches(Regex("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?\$)[\\d-]+\$"))) {
        val filtered = filterBooksByISBN(books, isbn)
        val available = checkBookStatus(filtered)
        checkoutBooks(available, books, userInput)
    } else {
        println("\"$isbn\" is not a valid ISBN. Use 10 or 13 digits (dashes optional).")
    }
}