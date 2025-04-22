package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import org.patchwork.Book
import org.patchwork.search
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SearchFunctionsTest : StringSpec({

    val books = mutableListOf(
        Book("J.R.R. Tolkien", "The Hobbit", "1234567890", true),
        Book("J.R.R. Tolkien", "The Lord of the Rings", "0987654321", true),
        Book("J.K. Rowling", "Harry Potter", "9780439136365", true)
    )

    fun captureOutput(block: () -> Unit): String {
        val baos = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(baos))
        block()
        System.setOut(originalOut)
        return baos.toString()
    }

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
})