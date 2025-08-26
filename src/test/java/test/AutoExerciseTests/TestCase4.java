package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import test.Tags;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase4 extends BaseTest {

    @Test
    @Order(4)
    @Owner("Eldiyar")
    @Description("Logout User")
    @Severity(SeverityLevel.BLOCKER)
    void logOutTest() {

        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        var login = home
                .clickSignUpLoginTab()
                .waitForPageLoaded();

        step("Verify 'Login to your account' is visible", () -> {
            softAssert.assertThat(login.titlesInAllPages("Login to your account"))
                    .as("'Login to your account' is visible")
                    .isEqualTo("Login to your account");
        });

        var afterLogin = login
                .inputLoginEmail("10@10.10")
                .inputLoginPassword("1")
                .clickLoginButton();

        var homeAfterContinue = afterLogin
                .waitForPageLoaded();

        String banner = homeAfterContinue.getLoggedInBannerText();
        step("Verify that 'Logged in as username' is visible", () -> {
            softAssert.assertThat(banner)
                    .isEqualTo("Logged in as 1");
        });

        var logout = homeAfterContinue
                .clickLogOutTab().waitForPageLoaded();

        step("Verify that user is navigated to login page", () -> {
            softAssert.assertThat(logout.isPageTabActive("Signup / Login")).
                    as("User is navigated to login page")
                    .isTrue();
        });

        softAssert.assertAll();
    }
}
