@file:JvmName("RepositoryKt")

package com.patchwork

import kotlinx.serialization.json.Json
import java.io.File

fun interface LoadRepository {
    fun books(): List<Book>
}

fun repository(filePath: String) = LoadRepository {
    val file = File(filePath)
    if (!file.exists()) return@LoadRepository emptyList()
    val content = file.readText()
    return@LoadRepository Json.decodeFromString(content)
}

private val jsonFormatter = Json { prettyPrint = true }
fun updateBooks(filePath: String, books: List<Book>) {
    val json = jsonFormatter.encodeToString(books)
    File(filePath).writeText(json)
}

fun getBookFilePath(): String {
    val path = System.getenv("BOOKS_FILE_PATH")
    return if (path != null) {
        println("Using books file: $path")
        path
    } else {
        println("BOOKS_FILE_PATH not set — falling back to local dev path")
        "src/main/resources/books.json"
    }
}


