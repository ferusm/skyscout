import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import model.UserDataContainer

class UserDataContainerTest : StringSpec({
    "UserDataContainer should register UserData for user with email" {
        val container = UserDataContainer()
        container.addEmails("test", listOf("test@test.test"))
        val printer = DummySpyPrinter()
        container.print(printer)
        val users = printer.users!!
        users.size shouldBe 1
        val user = users.first()
        user.name shouldBe "test"
        val emails = user.emails
        emails.size shouldBe 1
        emails.first() shouldBe "test@test.test"
    }

    "UserDataContainer should replace existing user with new one when email duplicated" {
        val container = UserDataContainer()
        container.addEmails("test", listOf("test@test.test"))
        container.addEmails("test2", listOf("test2@test.test", "test@test.test"))
        val printer = DummySpyPrinter()
        container.print(printer)
        val users = printer.users!!
        users.size shouldBe 1
        val user = users.first()
        user.name shouldBe "test2"
        val emails = user.emails
        emails.size shouldBe 2
        emails.containsAll(listOf("test@test.test", "test2@test.test")) shouldBe true
    }

    "UserDataContainer should combine all users with same emails into a new one" {
        val container = UserDataContainer()
        container.addEmails("test", listOf("test@test.test"))
        container.addEmails("test2", listOf("test2@test.test"))
        container.addEmails("test3", listOf("test@test.test", "test2@test.test"))
        val printer = DummySpyPrinter()
        container.print(printer)
        val users = printer.users!!
        users.size shouldBe 1
        val user = users.first()
        user.name shouldBe "test3"
        val emails = user.emails
        emails.size shouldBe 2
        emails.containsAll(listOf("test@test.test", "test2@test.test")) shouldBe true
    }
})