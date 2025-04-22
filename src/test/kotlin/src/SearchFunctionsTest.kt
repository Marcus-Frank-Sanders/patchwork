package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import io.mockk.*
import org.patchwork.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SearchFunctionsTest : StringSpec({

    val books = mutableListOf(
        Book("Title A", "Author A", "111-111-1111", true),
        Book("Title B", "Author B", "2222222222", false)
    )
    val filtered = listOf(books[0])
    val available = listOf(books[0])
    val mockInput = { "0" }

    beforeTest {
        clearAllMocks()
        mockkStatic("org.patchwork.BookSearchKt")
    }

    "searchByAuthor filters and proceeds to checkout" {
        every { filterBooksByAuthor(books, "Author A") } returns filtered
        every { checkBookStatus(filtered) } returns available
        every { checkoutBooks(available, books, mockInput) } just Runs

        searchByAuthor("Author A", books, mockInput)

        verify { filterBooksByAuthor(books, "Author A") }
        verify { checkBookStatus(filtered) }
        verify { checkoutBooks(available, books, mockInput) }
    }

    "searchByTitle filters and proceeds to checkout" {
        every { filterBooksByTitle(books, "Title A") } returns filtered
        every { checkBookStatus(filtered) } returns available
        every { checkoutBooks(available, books, mockInput) } just Runs

        searchByTitle("Title A", books, mockInput)

        verify { filterBooksByTitle(books, "Title A") }
        verify { checkBookStatus(filtered) }
        verify { checkoutBooks(available, books, mockInput) }
    }

    "searchByISBN with valid input filters and proceeds to checkout" {
        val isbn = "111-111-1111"
        every { filterBooksByISBN(books, isbn) } returns filtered
        every { checkBookStatus(filtered) } returns available
        every { checkoutBooks(available, books, mockInput) } just Runs

        searchByISBN(isbn, books, mockInput)

        verify { filterBooksByISBN(books, isbn) }
        verify { checkBookStatus(filtered) }
        verify { checkoutBooks(available, books, mockInput) }
    }

    "searchByISBN with invalid format prints error and does not proceed" {
        val invalidIsbn = "bad_isbn"

        val output = captureOutput {
            searchByISBN(invalidIsbn, books, mockInput)
        }

        output shouldContain "\"bad_isbn\" is not a valid ISBN"
        verify(exactly = 0) { checkoutBooks(any(), any(), any()) }
    }


})

fun captureOutput(block: () -> Unit): String {
    val baos = ByteArrayOutputStream()
    val original = System.out
    System.setOut(PrintStream(baos))
    block()
    System.setOut(original)
    return baos.toString()
}
