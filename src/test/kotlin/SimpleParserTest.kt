import exception.ParseException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import parser.SimpleParser

class SimpleParserTest : StringSpec({
    "SimpleParser should accept input like: 'test -> vasya@pupkin.com'" {
        val parser = SimpleParser()
        val input = arrayOf("test -> vasya@pupkin.com")
        val result = parser.parse(input)
        val spyPrinter = DummySpyPrinter()
        result.print(spyPrinter)
        val user = spyPrinter.users!!.first()
        user.name shouldBe "test"
        user.emails.first() shouldBe "vasya@pupkin.com"
    }

    "SimpleParser should accept row with multiple email addresses: 'test -> vasya@pupkin.com, vasya@rupkin.com, igor@rupkin.com'" {
        val parser = SimpleParser()
        val input = arrayOf("test -> vasya@pupkin.com, vasya@rupkin.com, igor@rupkin.com")
        val result = parser.parse(input)
        val spyPrinter = DummySpyPrinter()
        result.print(spyPrinter)
        val user = spyPrinter.users!!.first()
        user.name shouldBe "test"
        user.emails.containsAll(listOf("vasya@pupkin.com", "vasya@rupkin.com", "igor@rupkin.com")) shouldBe true
    }
    "SimpleParser should parse multiple rows" {
        val parser = SimpleParser()
        val input = arrayOf(
            "test -> vasya@pupkin.com, vasya@rupkin.com, igor@rupkin.com",
            "test2 -> vasya2@pupkin.com, vasya2@rupkin.com, igor2@rupkin.com"
        )
        val result = parser.parse(input)
        val spyPrinter = DummySpyPrinter()
        result.print(spyPrinter)
        spyPrinter.users!!.map { data -> data.name }.containsAll(listOf("test", "test2")) shouldBe true
        spyPrinter.users!!.forEach { data ->
            if (data.name == "test") {
                data.emails.containsAll(listOf("vasya@pupkin.com", "vasya@rupkin.com", "igor@rupkin.com")) shouldBe true
            } else {
                data.emails.containsAll(
                    listOf(
                        "vasya2@pupkin.com",
                        "vasya2@rupkin.com",
                        "igor2@rupkin.com"
                    )
                ) shouldBe true
            }
        }
    }
    "Simple parser should throw ParseException when input row is blank" {
        val parser = SimpleParser()
        val input = arrayOf("")
        shouldThrow<ParseException> {
            parser.parse(input)
        }.message shouldBe "Input row cannot be blank"
    }

    "Simple parser should throw ParseException when input row ends with ','" {
        val parser = SimpleParser()
        val input = arrayOf("test -> vasya@pupkin.com,")
        shouldThrow<ParseException> {
            parser.parse(input)
        }.message shouldBe "Unexpected symbol ',' before end of line"
    }

    "Simple parser should throw ParseException when email address has wrong format" {
        val parser = SimpleParser()
        val input1 = arrayOf("test -> vasyapupkin.com")
        shouldThrow<ParseException> {
            parser.parse(input1)
        }.message shouldBe "Unexpected substring: 'vasyapupkin.com'. Check e-mail format"
        val input2 = arrayOf("test -> vasyapupkin.com, vasya@pupkin.com")
        shouldThrow<ParseException> {
            parser.parse(input2)
        }.message shouldBe "Unexpected substring: 'vasyapupkin.com'. Check e-mail format"
    }

    "Simple parser should throw ParseException when input like: 'name > vasya@pupkin.com'" {
        val parser = SimpleParser()
        val input = arrayOf("name > vasya@pupkin.com")
        shouldThrow<ParseException> {
            parser.parse(input)
        }.message shouldBe "Symbol '>' must be after '-'"
    }

    "Simple parser should throw ParseException when input like: '- vasya@pupkin.com'" {
        val parser = SimpleParser()
        val input = arrayOf("- vasya@pupkin.com")
        shouldThrow<ParseException> {
            parser.parse(input)
        }.message shouldBe "Username cannot be blank"
    }

    "Simple parser should throw ParseException when input like: ', vasya@pupkin.com'" {
        val parser = SimpleParser()
        val input = arrayOf(", vasya@pupkin.com")
        shouldThrow<ParseException> {
            parser.parse(input)
        }.message shouldBe "Username must be before any email"
    }

    "Simple parser should throw ParseException when input like: 'r'" {
        val parser = SimpleParser()
        val input = arrayOf("r")
        shouldThrow<ParseException> {
            parser.parse(input)
        }.message shouldBe "Username must be before any email"
    }
})