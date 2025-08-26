package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)


public class TestCase1 extends BaseTest {
    Faker faker = new Faker();

    @Test
    @Order(1)
    @Owner("Eldiyar")
    @DisplayName("Register User")
    @Severity(SeverityLevel.BLOCKER)
    void registerTest() {
        String generatedName = faker
                .name().firstName();
        String generatedEmail = faker
                .internet().emailAddress();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        Allure.step("Verify Home page is visible", () -> {
            assertThat(home.isPageTabActive("Home"))
                    .as("'Home page is visible successfully' verified")
                    .isTrue();
        });

        var signup = home
                .clickSignUpLoginTab()
                .waitForPageLoaded();

        Allure.step("Verify 'New user signup!' is visible", () -> {
            assertThat(signup.isTitleVisible("New user signup!"))
                    .as("'New user signup! is visible' verified")
                    .isTrue();
        });

        var afterSignup = signup
                .inputSignUpName(generatedName)
                .inputSignUpEmail(generatedEmail)
                .clickSignUpButton();

        Allure.step("Verify that 'Enter account information' is visible", () -> {
            assertThat(signup.isTitleVisible("Enter account information"))
                    .as("'Enter account information is visible' verified")
                    .isTrue();
        });

        var accountCreated = afterSignup.waitForPageLoaded()
                .chooseTitle("Mr").inputPassword()
                .markCheckBoxes(true, true)
                .selectRandomDateMonthYear()
                .inputUserFirstName().inputUserLastName()
                .inputCompanyName().inputUserAddress1()
                .inputUserAddress2().inputUserCountry()
                .inputUserState().inputUserCity()
                .inputUserZipCode().inputUserMobilePhone()
                .clickCreateAccountButton().waitForPageLoaded();

        Allure.step("Verify that 'Account Created' is visible", () -> {
            assertThat(accountCreated.isTitleVisible("Account Created!"))
                    .as("'Account Created is visible' verified")
                    .isTrue();
        });

        var homeAfterContinue = accountCreated.clickContinueButton().waitForPageLoaded();

        String banner = homeAfterContinue.getLoggedInBannerText();
        Allure.step("Verify that 'Logged in as username' is visible", () -> {
            assertThat(banner)
                    .as("'Logged in as <username>'")
                    .isEqualTo("Logged in as " + generatedName);
        });

        var delete = homeAfterContinue.clickDeletedAccountTab().waitForPageLoaded();

        Allure.step("Verify that 'Account deleted!' is visible", () -> {
                    assertThat(delete.isTitleVisible("Account deleted!"))
                            .as("'Account deleted! is visible' verified")
                            .isTrue();
                });
                delete.clickContinueButton();
    }
}





