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

    private static String BASE_URL;
    private static boolean HEADLESS;

    public <T> T open(Class<T> clazz) {
        return Selenide.open(BASE_URL, clazz);
    }

    @BeforeAll
    static void setup() {
        loadProperties();

        Configuration.browser = "chrome";
        Configuration.headless = HEADLESS;
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";

        // уникальный профиль Chrome для CI
        Configuration.browserCapabilities.setCapability("goog:chromeOptions",
                new org.openqa.selenium.chrome.ChromeOptions()
                        .addArguments("--disable-dev-shm-usage")
                        .addArguments("--no-sandbox")
                        .addArguments("--window-size=1366,768")
                        .addArguments("--user-data-dir=/tmp/chrome-profile-" + System.currentTimeMillis())
                        .addArguments(HEADLESS ? "--headless=new" : "")
        );

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    private static void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("app.properties не найден");
            }
            props.load(input);
            BASE_URL = props.getProperty("base.url");
            HEADLESS = Boolean.parseBoolean(props.getProperty("headless.mode", "true"));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке app.properties", e);
        }
    }

    @AfterAll
    static void tearDown() {
        Selenide.closeWebDriver();
        SelenideLogger.removeListener("AllureSelenide");
    }
}

//private final String BASE_URL = "https://www.amazon.com/";
