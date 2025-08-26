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


public class TestCase5 extends BaseTest {

    @Test
    @Order(5)
    @Owner("Eldiyar")
    @Description("Register User with existing email")
    @Severity(SeverityLevel.BLOCKER)
    void registerExistingEmailTest() {

        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        var signup = home
                .clickSignUpLoginTab()
                .waitForPageLoaded();

        step("Verify 'New User Signup!' is visible", () -> {
            softAssert.assertThat(signup.titlesInAllPages("New User Signup!"))
                    .as("'New User Signup!' is visible")
                    .isEqualTo("New User Signup!");
        });

        signup
                .inputSignUpName("1")
                .inputSignUpEmail("10@10.10")
                .clickSignUpButton();

        step("Verify error 'Email Address already exist!' is visible", () -> {
            softAssert.assertThat(signup.getMessageText("Email Address already exist!"))
                    .isEqualTo("Email Address already exist!");
        });

        softAssert.assertAll();
    }
}
