@file:JvmName("FiltersKt")
package org.patchwork

fun filterBooksByAuthor(books: List<Book>, author: String): List<Book> =
    books.filter { it.author.contains(author.trim(), ignoreCase = true) }

fun filterBooksByTitle(books: List<Book>, title: String): List<Book> =
    books.filter { it.title.contains(title.trim(), ignoreCase = true) }

fun filterBooksByISBN(books: List<Book>, isbn: String): List<Book> =
    books.filter { it.isbn.replace("-", "").equals(isbn.trim().replace("-", ""), ignoreCase = true) }

fun getCheckedOutBooks(books: List<Book>): List<Book> =
    books.filter { !it.isAvailable }


