package test.AutoExerciseTests;


import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase3 extends BaseTest {

    @Test
    @Order(3)
    @Owner("Eldiyar")
    @Description("Login User with incorrect email and password")
    @Severity(SeverityLevel.BLOCKER)
    void inCorrectLoginTest() {
        var home = open(HomePage.class)
                .waitForPageLoaded();

        assertThat(home.isPageTabActive("Home"))
                .as("Home tab should be active")
                .isTrue();

        var login = home
                .clickSignUpLoginTab()
                .waitForPageLoaded();

        assertThat(login.isTitleVisible("Login to your account"))
                .as("'Login to your account' should be visible")
                .isTrue();

        var afterLogin = login
                .inputLoginEmail("1@1.2")
                .inputLoginPassword("123")
                .clickLoginButton();
        assertThat(login.isIncorrectMessageVisible())
                .as("'Your email or password is incorrect!' is visible")
                .isTrue();
    }

}