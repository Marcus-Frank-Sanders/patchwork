package src

import com.patchwork.Book
import com.patchwork.search
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SearchMenuTest : StringSpec({

    val books = mutableListOf(
        Book("J.R.R. Tolkien", "The Hobbit", "1234567890", true),
        Book("J.R.R. Tolkien", "The Lord of the Rings", "0987654321", true),
        Book("J.K. Rowling", "Harry Potter", "9780439136365", true)
    )

    "search by author calls searchByAuthor and checkoutBooks" {
        val inputs = listOf("a", "J.R.R. Tolkien", "b").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            search(books, userInput)
        }

        output shouldContain "Enter author name:"
        output shouldContain "Search by: a (Author), t (Title), i (ISBN), or b (Back):"
        output shouldContain "Returning to main menu."
    }

    "search by title works and returns to menu" {
        val inputs = listOf("t", "The Hobbit", "b").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            search(books, userInput)
        }

        output shouldContain "Enter title:"
        output shouldContain "Returning to main menu."
    }

    "search by ISBN works with valid ISBN" {
        val inputs = listOf("i", "9780439136365", "b").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            search(books, userInput)
        }

        output shouldContain "Enter ISBN:"
        output shouldContain "Returning to main menu."
    }

    "invalid ISBN shows error message" {
        val inputs = listOf("i", "123-ABC", "b").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            search(books, userInput)
        }

        output shouldContain "\"123-ABC\" is not a valid ISBN"
    }

    "invalid menu input is handled" {
        val inputs = listOf("z", "b").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            search(books, userInput)
        }

        output shouldContain "Invalid search type. Try again."
        output shouldContain "Returning to main menu."
    }

    "back option exits search loop immediately" {
        val inputs = listOf("b").iterator()
        val userInput = { inputs.next() }

        val output = captureOutput {
            search(books, userInput)
        }

        output shouldContain "Returning to main menu."
    }
})

private fun captureOutput(block: () -> Unit): String {
    val baos = ByteArrayOutputStream()
    val original = System.out
    System.setOut(PrintStream(baos))
    block()
    System.setOut(original)
    return baos.toString()
}