package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter

public class BaseTest {

    private final String BASE_URL = System.getProperty("BASE_URL", "https://automationexercise.com");

    public <T> T open(Class<T> clazz) {
        return Selenide.open(BASE_URL, clazz);
    }

    @BeforeAll
    static void setup() {
        // Настройки Selenide
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("HEADLESS", "true"));
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";

        // Allure listener
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

//private final String BASE_URL = "https://www.amazon.com/";
