import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import io.mockk.*
import org.patchwork.*
import src.captureOutput
import java.io.File

class LibraryFunctionTest : StringSpec({

    val books = mutableListOf(
        Book("Test Book", "Author A", "1234567890", true)
    )
    val filePath = "test_books.json"

    beforeTest {
        clearAllMocks()
        mockkStatic("src/main/kotlin/Main.kt") // <-- Adjust this based on actual package
        mockkStatic("src/main/kotlin/Search.kt") // if admin/search defined here
    }

    afterTest {
        File(filePath).delete()
    }

    "calls search when user inputs s, then exits" {
        val inputs = listOf("s", "e").iterator()
        val userInput = { inputs.next() }

        every { search(books, any()) } just Runs
        every { admin(books, any()) } just Runs
        every { updateBooks(filePath, books) } just Runs

        val output = captureOutput {
            library(books, filePath, userInput)
        }

        verify(exactly = 1) { search(books, any()) }
        verify(exactly = 0) { admin(books, any()) }
        verify(exactly = 1) { updateBooks(filePath, books) }

        output shouldContain "Welcome to the library"
        output shouldContain "Thanks for visiting the library"
    }

    "calls admin when user inputs a, then exits" {
        val inputs = listOf("a", "e").iterator()
        val userInput = { inputs.next() }

        every { search(books, any()) } just Runs
        every { admin(books, any()) } just Runs
        every { updateBooks(filePath, books) } just Runs

        val output = captureOutput {
            library(books, filePath, userInput)
        }

        verify(exactly = 0) { search(books, any()) }
        verify(exactly = 1) { admin(books, any()) }
        verify(exactly = 1) { updateBooks(filePath, books) }

        output shouldContain "Welcome to the library"
        output shouldContain "Thanks for visiting the library"
    }

    "handles invalid input and still exits" {
        val inputs = listOf("wrong", "e").iterator()
        val userInput = { inputs.next() }

        every { search(books, any()) } just Runs
        every { admin(books, any()) } just Runs
        every { updateBooks(filePath, books) } just Runs

        val output = captureOutput {
            library(books, filePath, userInput)
        }

        output shouldContain "Invalid option"
        verify { updateBooks(filePath, books) }
    }
})