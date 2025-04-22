package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.patchwork.*

class SearchFunctionsTest : StringSpec({

    val books = mutableListOf(
        Book("Author One", "Book One", "1111111111", true),
        Book("Author Two", "Book Two", "2222222222", false),
        Book("Author One", "Book Three", "3333333333", true),
        Book("Author Three", "Book Four", "4444444444", true)
    )

    val userInput: () -> String = { "a" }

    "search should handle search by author" {
        val searchInput = "Author One"
        val filteredBooks = mutableListOf<Book>()

        searchByAuthor(searchInput, books, userInput)

        val availableBooks = filterBooksByAuthor(books, searchInput).filter { it.isAvailable }
        availableBooks.shouldHaveSize(2)
        availableBooks[0].title shouldBe "Book One"
        availableBooks[1].title shouldBe "Book Three"
    }

    "searchByTitle should filter books by title" {
        val title = "Book Two"
        val filteredBooks = filterBooksByTitle(books, title)
        val availableBooks = checkBookStatus(filteredBooks)

        availableBooks.shouldHaveSize(1)
        availableBooks[0].title shouldBe "Book Two"
    }

    "searchByISBN should filter books by ISBN" {
        val isbn = "2222222222"
        val filteredBooks = filterBooksByISBN(books, isbn)
        val availableBooks = checkBookStatus(filteredBooks)

        availableBooks.shouldHaveSize(1)
        availableBooks[0].isbn shouldBe "2222222222"
    }
})
