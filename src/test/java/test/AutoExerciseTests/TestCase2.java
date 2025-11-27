package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import base.TestFlags;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import test.Tags;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)

public class TestCase2 extends BaseTest {

    @Test
    @Order(2)
    @Owner("Eldiyar")
    @DisplayName("Test Case 2: Login User with correct email and password")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Signup / Login' button
            5. Verify 'Login to your account' is visible
            6. Enter correct email address and password
            7. Click 'login' button
            8. Verify that 'Logged in as username' is visible
            9. Click 'Delete Account' button
            10. Verify that 'ACCOUNT DELETED!' is visible""")
    void loginUserWithCorrectEmailAndPassword() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var login = home
                    .clickSignUpLoginTab()
                    .waitForPageLoaded();

            step("Verify 'Login to your account' is visible", () -> {
                softAssert.assertThat(login.titlesInAllPages("Login to your account"))
                        .as("'Login to your account' is visible")
                        .isEqualToIgnoringCase("Login to your account");

                var afterLogin = login
                        .inputLoginEmail(System.getProperty("LOGIN_EMAIL", "qa+tc2@ex.com"))
                        .inputLoginPassword(System.getProperty("LOGIN_PASSWORD", "1"))
                        .clickLoginButton();

                var homeAfterContinue = afterLogin
                        .waitForPageLoaded();

                String banner = homeAfterContinue.getLoggedInBannerText();
                step("Verify that 'Logged in as username' is visible", () -> {
                    // Check that banner contains "Logged in as" followed by a username
                    softAssert.assertThat(banner)
                            .as("Should contain 'Logged in as' text. Found: '" + banner + "'")
                            .containsIgnoringCase("Logged in as");
                    
                    // Check that there's text after "Logged in as" (the username)
                    String[] parts = banner.split("(?i)logged in as");
                    softAssert.assertThat(parts.length)
                            .as("Should have text after 'Logged in as'. Banner: '" + banner + "'")
                            .isGreaterThan(1);
                    
                    String username = parts[1].trim();
                    softAssert.assertThat(username)
                            .as("Username should not be empty. Found: '" + username + "'")
                            .isNotEmpty();

                    if (!TestFlags.shouldKeepAccount()) {
                        var delete = homeAfterContinue
                                .clickDeletedAccountTab()
                                .waitForPageLoaded();

                        step("Verify that 'Account Deleted!' is visible", () -> {
                            softAssert.assertThat(delete.titlesInAllPages("Account Deleted!"))
                                    .as("'Account Deleted!' is visible")
                                    .isEqualToIgnoringCase("Account Deleted!");

                            delete.clickContinueButton();
                        });
                    }

                });
            });
        });
        softAssert.assertAll();
    }
}