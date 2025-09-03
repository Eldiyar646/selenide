package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)

public class TestCase4 extends BaseTest {

    @Test
    @Order(4)
    @Owner("Eldiyar")
    @DisplayName("Test Case 4: Logout User")
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
            9. Click 'Logout' button
            10. Verify that user is navigated to login page""")
    void logoutUser() {
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
                        .isEqualTo("Login to your account");

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

                    var logout = homeAfterContinue
                            .clickLogOutTab().waitForPageLoaded();

                    step("Verify that user is navigated to login page", () -> {
                        softAssert.assertThat(logout.isPageTabActive("Signup / Login")).
                                as("User is navigated to login page")
                                .isTrue();
                    });
                });
            });
        });
        softAssert.assertAll();
    }
}