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

public class TestCase2 extends BaseTest {

    @Test
    @Order(2)
    @Owner("Eldiyar")
    @Description("Login user with correct email and password")
    @Severity(SeverityLevel.BLOCKER)
    void correctLoginTest() {
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
                .inputLoginPassword("1")
                .clickLoginButton();

        var homeAfterContinue = afterLogin.waitForPageLoaded();

        String banner = homeAfterContinue.getLoggedInBannerText();
        assertThat(banner)
                .isEqualTo("Logged in as 1");

        var delete = homeAfterContinue.clickDeletedAccountTab().waitForPageLoaded();

        assertThat(delete.isAccountDeletedMessageVisible())
                .as("'Account deleted!' should be visible")
                .isTrue();

        delete.clickContinueButton();

    }

}
