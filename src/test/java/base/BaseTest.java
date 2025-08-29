package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

@Getter

public class BaseTest {

    private final String BASE_URL = "https://www.automationexercise.com/";
    //private final String BASE_URL = "https://www.amazon.com/";

    public <T> T open(Class<T> clazz) {
        return Selenide.open(BASE_URL, clazz);
    }

    @BeforeAll
    static void setup() {
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.headless = false;
        Configuration.pageLoadStrategy = "eager";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @AfterAll
    static void tearDown() {
        Selenide.closeWebDriver();
        SelenideLogger.removeListener("AllureSelenide");
    }
}