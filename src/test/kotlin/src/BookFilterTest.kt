package src

import com.patchwork.Book
import com.patchwork.filterBooksByAuthor
import com.patchwork.filterBooksByISBN
import com.patchwork.filterBooksByTitle
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class BookFilterTest : StringSpec({

    val books = listOf(
        Book("J.R.R. Tolkien", "The Hobbit", "1234567890", true, false),
        Book("J.R.R. Tolkien", "The Lord of the Rings", "1234567890123", false, false),
        Book("George Orwell", "1984", "0987654321", true, false),
        Book("Aldous Huxley", "Brave New World", "1122334455", true, true),
    )

    "should filter books by author" {
        val result = filterBooksByAuthor(books, "tolkien")
        result.shouldHaveSize(2)
        result.map { it.title } shouldContainExactly listOf("The Hobbit", "The Lord of the Rings")
    }

    "should filter books by title" {
        val result = filterBooksByTitle(books, "new world")
        result.shouldHaveSize(1)
        result[0].author shouldBe "Aldous Huxley"
    }

    "should filter books by full ISBN match" {
        val result = filterBooksByISBN(books, "1234567890")
        result.shouldHaveSize(1)
        result[0].title shouldBe "The Hobbit"
    }

    "should return empty list if no ISBN matches" {
        val result = filterBooksByISBN(books, "0000000000")
        result.shouldHaveSize(0)
    }

    "should ignore case in author search" {
        val result = filterBooksByAuthor(books, "GEORGE orwell")
        result.shouldHaveSize(1)
        result[0].title shouldBe "1984"
    }

    "should ignore case in title search" {
        val result = filterBooksByTitle(books, "BRAVE new World")
        result.shouldHaveSize(1)
        result[0].title shouldBe "Brave New World"
    }

})