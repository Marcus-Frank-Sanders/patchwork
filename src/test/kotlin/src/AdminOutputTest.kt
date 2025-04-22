package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import org.patchwork.Book
import org.patchwork.getCheckedOutBooks
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class AdminOutputTest : StringSpec({
    "admin summary should print checked out books with title and ISBN" {
        val books = listOf(
            Book("Author A", "Book A", "111", isAvailable = false),
            Book("Author B", "Book B", "222", isAvailable = true),
        )

        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        getCheckedOutBooks(books).forEachIndexed { i, book ->
            println("[$i] - ${book.title} by ${book.author} (ISBN: ${book.isbn})")
        }

        val output = outContent.toString().trim()

        output shouldContain "Book A by Author A (ISBN: 111)"
        output shouldContain "[0] - Book A"
        output shouldNotContain "Book B"
    }
})