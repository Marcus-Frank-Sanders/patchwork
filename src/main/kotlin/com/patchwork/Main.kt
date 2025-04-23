package com.patchwork

import kotlin.system.exitProcess


fun main() {

    val userInput: () -> String = {
        readlnOrNull() ?: run {
            println("No interactive input detected. Exiting.")
            exitProcess(1)
        }
    }

    println("Working directory: ${System.getProperty("user.dir")}")

    val filePath = getBookFilePath()
    val books = repository(filePath).books().toMutableList()

    library(books, filePath, userInput)

}




