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

public class TestCase3 extends BaseTest {

    @Test
    @Order(3)
    @Owner("Eldiyar")
    @Description("Login User with incorrect email and password")
    @Severity(SeverityLevel.BLOCKER)
    void inCorrectLoginTest() {

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
                    .as("Login to your account is visible")
                    .isEqualToIgnoringCase("Login to your account");
        });

        var afterLogin = login
                .inputLoginEmail("1@3.2")
                .inputLoginPassword("123")
                .clickLoginButton();

        step("Verify error 'Your email or password is incorrect!' is visible", () -> {
            softAssert.assertThat(login.getMessageText("Your email or password is incorrect!"))
                    .isEqualTo("Your email or password is incorrect!");
        });

        softAssert.assertAll();
    }

}