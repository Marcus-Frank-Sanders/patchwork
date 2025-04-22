package org.patchwork

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import io.mockk.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class LibraryTest : StringSpec({

    val books = mutableListOf(Book("Mockingbird", "Harper Lee", "9780061120084", true))
    val filePath = "src/test/resources/mock_books.json"

    fun captureOutput(block: () -> Unit): String {
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        block()
        System.setOut(originalOut)
        return outputStream.toString()
    }

    beforeTest {
        mockkStatic("org.patchwork.LibraryKt")
        mockkStatic("org.patchwork.SearchKt")
        mockkStatic("org.patchwork.AdminKt")
        mockkStatic("org.patchwork.RepositoryKt")

        every { search(any(), any()) } just Runs
        every { admin(any(), any()) } just Runs
        every { updateBooks(any(), any()) } just Runs
    }

    afterTest {
        unmockkAll()
    }

    "calls search when user types 's'" {
        val inputs = listOf("s", "e").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            library(books, filePath, userInput)
        }

        output shouldContain "Welcome to the library"
        verify(exactly = 1) { search(books, any()) }
        verify(exactly = 1) { updateBooks(filePath, books) }
        verify(exactly = 0) { admin(any(), any()) }
    }

    "calls admin when user types 'a'" {
        val inputs = listOf("a", "e").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            library(books, filePath, userInput)
        }

        output shouldContain "Thanks for visiting the library"
        verify(exactly = 1) { admin(books, any()) }
        verify(exactly = 1) { updateBooks(filePath, books) }
        verify(exactly = 0) { search(any(), any()) }
    }

    "prints invalid option on unknown input" {
        val inputs = listOf("xyz", "e").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            library(books, filePath, userInput)
        }

        output shouldContain "Invalid option"
        verify(exactly = 1) { updateBooks(filePath, books) }
        verify(exactly = 0) { search(any(), any()) }
        verify(exactly = 0) { admin(any(), any()) }
    }
})


//    val books = mutableListOf(
//        Book("Test Book", "Author X", "1234567890", true)
//    )
//    val filePath = "test_books.json"
//
//    beforeTest {
//        clearAllMocks()
//
//        mockkStatic("org.patchwork.LibraryKt")
//        mockkStatic("org.patchwork.SearchKt")
//        mockkStatic("org.patchwork.AdminKt")
//    }
//
//    afterTest {
//        File(filePath).delete()
//    }
//
//    "should call search when user inputs 's'" {
//        val input = listOf("s", "e").iterator()
//        val userInput = { input.next() }
//
//        every { search(books, any()) } just Runs
//        every { admin(books, any()) } just Runs
//        every { updateBooks(filePath, books) } just Runs
//
//        val output = captureOutput {
//            library(books, filePath, userInput)
//        }
//
//        verify(exactly = 1) { search(books, any()) }
//        verify(exactly = 0) { admin(books, any()) }
//        verify(exactly = 1) { updateBooks(filePath, books) }
//
//        output shouldContain "Welcome to the library"
//        output shouldContain "Thanks for visiting the library"
//    }
//
//    "should call admin when user inputs 'a'" {
//        val input = listOf("a", "e").iterator()
//        val userInput = { input.next() }
//
//        every { search(books, any()) } just Runs
//        every { admin(books, any()) } just Runs
//        every { updateBooks(filePath, books) } just Runs
//
//        val output = captureOutput {
//            library(books, filePath, userInput)
//        }
//
//        verify(exactly = 0) { search(books, any()) }
//        verify(exactly = 1) { admin(books, any()) }
//        verify(exactly = 1) { updateBooks(filePath, books) }
//
//        output shouldContain "Welcome to the library"
//        output shouldContain "Thanks for visiting the library"
//    }
//
//    "should call updateBooks and exit on 'e'" {
//        val userInput = { "e" }
//
//        every { search(books, any()) } just Runs
//        every { admin(books, any()) } just Runs
//        every { updateBooks(filePath, books) } just Runs
//
//        val output = captureOutput {
//            library(books, filePath, userInput)
//        }
//
//        verify { updateBooks(filePath, books) }
//        output shouldContain "Thanks for visiting the library"
//    }
//
//    "should show invalid input message and re-prompt" {
//        val input = listOf("xyz", "e").iterator()
//        val userInput = { input.next() }
//
//        every { search(books, any()) } just Runs
//        every { admin(books, any()) } just Runs
//        every { updateBooks(filePath, books) } just Runs
//
//        val output = captureOutput {
//            library(books, filePath, userInput)
//        }
//
//        output shouldContain "Invalid option"
//        verify(exactly = 0) { search(any(), any()) }
//        verify(exactly = 0) { admin(any(), any()) }
//        verify(exactly = 1) { updateBooks(filePath, books) }
//    }
//})
