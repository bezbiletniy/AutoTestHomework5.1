package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndRepMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//*[@data-test-id=\"city\"]//self::input").setValue(validUser.getCity());
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE, firstMeetingDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue(validUser.getName());
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue(validUser.getPhone());
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//span[text()='Запланировать']").click();
        $x("//*[@data-test-id=\"success-notification\"]").shouldBe(appear).shouldHave(text("Успешно!\n" + "Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(5));
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE, secondMeetingDate);
        $x("//span[text()='Запланировать']").click();
        $x("//*[@data-test-id=\"replan-notification\"]").shouldBe(appear).shouldHave(text("Необходимо подтверждение\n" + "У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(5));
        $x("//span[text()='Перепланировать']").click();
        $x("//*[@data-test-id=\"success-notification\"]").shouldBe(appear).shouldHave(text("Успешно!\n" + "Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(5));
    }
}