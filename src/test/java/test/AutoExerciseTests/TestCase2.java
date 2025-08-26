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

public class TestCase2 extends BaseTest {

    @Test
    @Order(2)
    @Owner("Eldiyar")
    @Description("Login user with correct email and password")
    @Severity(SeverityLevel.BLOCKER)
    void correctLoginTest() {

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
                    .isEqualToIgnoringCase("Login to your account");
        });

        var afterLogin = login
                .inputLoginEmail("88@88.88")
                .inputLoginPassword("1")
                .clickLoginButton();

        var homeAfterContinue = afterLogin
                .waitForPageLoaded();

        String banner = homeAfterContinue.getLoggedInBannerText();
        step("Verify that 'Logged in as username' is visible", () -> {
            softAssert.assertThat(banner)
                    .isEqualToIgnoringCase("Logged in as 8");
        });

        var delete = homeAfterContinue
                .clickDeletedAccountTab()
                .waitForPageLoaded();

        step("Verify that 'Account Deleted!' is visible", () -> {
            softAssert.assertThat(delete.titlesInAllPages("Account Deleted!"))
                    .as("'Account Deleted!' is visible")
                    .isEqualToIgnoringCase("Account Deleted!");
        });

        delete.clickContinueButton();

        softAssert.assertAll();
    }
}
