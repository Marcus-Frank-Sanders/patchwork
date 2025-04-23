package src

import com.patchwork.Book
import com.patchwork.repository
import com.patchwork.updateBooks
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import java.io.File

class RepositoryTest : StringSpec({

    val testBooks = listOf(
        Book("Test Author", "Test Book", "1111111111", true),
        Book("Author Two", "Another Book", "2222222222", false)
    )

    val json = Json.encodeToString(testBooks)
    val testFilePath = "src/test/resources/test_books.json"

    afterTest {
        File(testFilePath).delete()
    }

    "repository loads books from existing JSON file" {
        File(testFilePath).writeText(json)

        val repo = repository(testFilePath)
        val loadedBooks = repo.books()

        loadedBooks shouldHaveSize 2
        loadedBooks[0].title shouldBe "Test Book"
        loadedBooks[1].author shouldBe "Author Two"
    }

    "repository returns empty list if file doesn't exist" {
        val repo = repository("nonexistent_file.json")
        repo.books().shouldBe(emptyList())
    }

    "updateBooks writes books to JSON file" {
        updateBooks(testFilePath, testBooks)

        val fileContent = File(testFilePath).readText()
        val decoded = Json.decodeFromString<List<Book>>(fileContent)

        decoded shouldHaveSize 2
        decoded[0].isbn shouldBe "1111111111"
    }
})




