package org.patchwork

fun main() {
    val userInput: () -> String = { readln() }

    val filePath = getBookFilePath()
    val books = repository(filePath).books().toMutableList()

    library(books, filePath, userInput)
}




