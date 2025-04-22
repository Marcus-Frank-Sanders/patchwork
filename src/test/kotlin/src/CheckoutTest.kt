package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.patchwork.Book
import org.patchwork.checkoutBooks
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class CheckoutTest : StringSpec({

    val books = mutableListOf(
        Book("Author One", "Book One", "1111111111"),
        Book("Author Two", "Book Two", "2222222222" ),
        Book("Author Three", "Book Three", "3333333333")
    )

    val allBooks = books.toMutableList()

    "should print 'No books available to check out.' when no books are selected" {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        checkoutBooks(emptyList(), allBooks) { "1" }

        outputStream.toString() shouldContain "No books available to check out."
    }

    "should print 'Checking out cancelled.' when user cancels" {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        checkoutBooks(books, allBooks, { "c" })

        outputStream.toString() shouldContain "Checking out cancelled."
    }

    "should print 'No valid selections.' when user selects invalid indexes" {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        checkoutBooks(books, allBooks, { "99" })

        outputStream.toString() shouldContain "No valid selections."
    }

    "should successfully checkout valid books and update their availability" {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        checkoutBooks(books, allBooks, { "0,1" })

        outputStream.toString() shouldContain "Checked out books:"
        outputStream.toString() shouldContain "Book One by Author One"
        outputStream.toString() shouldContain "Book Two by Author Two"

        allBooks[0].isAvailable shouldBe false
        allBooks[1].isAvailable shouldBe false
    }


    "should not checkout books when user input is canceled" {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        checkoutBooks(books, allBooks) { "c" }

        outputStream.toString() shouldContain "Checking out cancelled."
    }
})