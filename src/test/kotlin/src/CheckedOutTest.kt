package src

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import org.patchwork.Book
import org.patchwork.getCheckedOutBooks

class CheckoutTest : StringSpec({

    val books = listOf(
        Book("Robert C. Martin", "Clean Code", "9780132350884", isReference = false, isAvailable = true),
        Book("Andy Hunt", "The Pragmatic Programmer", "9780201616224", isReference = false, isAvailable = false),
        Book("Erich Gamma", "Design Patterns", "9780201633610", isReference = false, isAvailable = false),
        Book("Dmitry Jemerov", "Kotlin in Action", "9781617293290", isReference = false, isAvailable = true),
    )

    "should return only checked out books" {
        val result = getCheckedOutBooks(books)

        result.shouldHaveSize(2)
        result.map { it.title } shouldContainExactly listOf(
            "The Pragmatic Programmer",
            "Design Patterns"
        )
    }

    "should return empty list when no books are checked out" {
        val result = getCheckedOutBooks(
            books.map { it.copy(isAvailable = true) }
        )

        result.shouldHaveSize(0)
    }

})