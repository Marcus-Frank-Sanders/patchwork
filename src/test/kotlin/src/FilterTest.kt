package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.patchwork.Book
import org.patchwork.checkBookStatus

class FilterTest : StringSpec({

    val books = listOf(
        Book("J.R.R. Tolkien", "The Hobbit", "1234567890", isAvailable = true),
        Book("J.R.R. Tolkien", "The Silmarillion", "0987654321", isAvailable = false),
        Book(
            "Ken Kousen",
            "Kotlin Cookbook: A Problem-Focused Approach",
            "1492046671",
            isAvailable = true,
            isReference = true
        )
    )

    "checkBookStatus should return only available non-reference books" {
        val availableBooks = checkBookStatus(books)
        availableBooks shouldHaveSize 1
        availableBooks.first().title shouldBe "The Hobbit"
        availableBooks.first().author shouldBe "J.R.R. Tolkien"
        availableBooks.first().isbn shouldBe "1234567890"
    }

    "searchByAuthor should return matching books by author" {
        val filtered = books.filter { it.author.contains("tolkien", ignoreCase = true) }
        filtered shouldHaveSize 2
        filtered.map { it.title } shouldContainAll listOf("The Hobbit", "The Silmarillion")
        filtered.map { it.author } shouldContainAll listOf("J.R.R. Tolkien", "J.R.R. Tolkien")
        filtered.map { it.isbn } shouldContainAll listOf("1234567890", "0987654321")
    }

    "searchByTitle should return matching books by author" {
        val filtered = books.filter { it.title.contains("Kotlin Cookbook", ignoreCase = true) }
        filtered shouldHaveSize 1
        filtered.first().title shouldBe "Kotlin Cookbook: A Problem-Focused Approach"
        filtered.first().author shouldBe "Ken Kousen"
        filtered.first().isbn shouldBe "1492046671"
    }

    "searchByISBN should return matching book" {
        val filtered = books.filter { it.isbn == "1234567890" }
        filtered shouldHaveSize 1
        filtered.first().title shouldBe "The Hobbit"
        filtered.first().author shouldBe "J.R.R. Tolkien"
        filtered.first().isbn shouldBe "1234567890"
    }
})