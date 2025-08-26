package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)


public class TestCase5 extends BaseTest {

    @Test
    @Order(5)
    @Owner("Eldiyar")
    @Description("Register User with existing email")
    @Severity(SeverityLevel.BLOCKER)
    void registerExistingEmailTest() {
        var home = open(HomePage.class)
                .waitForPageLoaded();

        assertThat(home.isPageTabActive("Home"))
                .as("Home page is visible successfully")
                .isTrue();

        var signup = home
                .clickSignUpLoginTab()
                .waitForPageLoaded();

        assertThat(signup.isTitleVisible("New User Signup!"))
                .as("Title 'New User Signup!' is visible")
                .isTrue();

        signup
                .inputSignUpName("1")
                .inputSignUpEmail("1@3.2")
                .clickSignUpButton();

        assertThat(signup.isEmailExistingMessageVisible())
                .as("error 'Email Address already exist!' is visible")
                .isTrue();
    }
}
