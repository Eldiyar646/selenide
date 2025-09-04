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

public class TestCase5 extends BaseTest {

    @Test
    @Order(5)
    @Owner("Eldiyar")
    @DisplayName("Test Case 5: Register User with existing email")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Signup / Login' button
            5. Verify 'New User Signup!' is visible
            6. Enter name and already registered email address
            7. Click 'Signup' button
            8. Verify error 'Email Address already exist!' is visible""")
    void registerUserWithExistingEmail() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var signup = home
                    .clickSignUpLoginTab()
                    .waitForPageLoaded();

            step("Verify 'New User Signup!' is visible", () -> {
                softAssert.assertThat(signup.titlesInAllPages("New User Signup!"))
                        .as("'New User Signup!' is visible")
                        .isEqualTo("New User Signup!");

                signup
                        .inputSignUpName("1")
                        .inputSignUpEmail("01@02.01")
                        .clickSignUpButton();

                step("Verify error 'Email Address already exist!' is visible", () -> {
                    softAssert.assertThat(signup.getMessageText("Email Address already exist!"))
                            .isEqualTo("Email Address already exist!");
                });
            });
        });
        softAssert.assertAll();
    }
}
