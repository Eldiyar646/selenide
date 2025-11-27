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

public class TestCase3 extends BaseTest {

    @Test
    @Order(3)
    @Owner("Eldiyar")
    @DisplayName("Test Case 3: Login User with incorrect email and password")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Signup / Login' button
            5. Verify 'Login to your account' is visible
            6. Enter incorrect email address and password
            7. Click 'login' button
            8. Verify error 'Your email or password is incorrect!' is visible""")
    void loginUserWithIncorrectEmailAndPassword() {
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
                        .as("Login to your account is visible")
                        .isEqualToIgnoringCase("Login to your account");

                login
                        .inputLoginEmail(System.getProperty("LOGIN_EMAIL", "qa+tc2@ex.com"))
                        .inputLoginPassword("wrongpassword")
                        .clickLoginButton();

                step("Verify error 'Your email or password is incorrect!' is visible", () -> {
                    softAssert.assertThat(login.getMessageText("Your email or password is incorrect!"))
                            .isEqualTo("Your email or password is incorrect!");
                });
            });
        });
        softAssert.assertAll();
    }
}