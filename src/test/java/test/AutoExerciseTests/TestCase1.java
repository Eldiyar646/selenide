package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.data.UserData;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import test.Tags;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase1 extends BaseTest {
    Faker faker = new Faker();
    UserData user = new UserData();

    @Test
    @Order(1)
    @Owner("Eldiyar")
    @DisplayName("Register User")
    @Severity(SeverityLevel.BLOCKER)
    void registerTest() {

        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setCompanyName(faker.company().name());
        user.setAddress1(faker.address().streetAddress());
        user.setAddress2(faker.address().secondaryAddress());
        user.setCountry("United States");
        user.setState(faker.address().state());
        user.setCity(faker.address().city());
        user.setZipCode(faker.address().zipCode());
        user.setPhone(faker.phoneNumber().cellPhone());

        var softAssert = new SoftAssertions();

        String generatedName = faker
                .name().firstName();
        String generatedEmail = faker
                .internet().emailAddress();

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
                    .as("New User Signup! is visible")
                    .isEqualToIgnoringCase("New User Signup!");
        });

        var afterSignup = signup
                .inputSignUpName(generatedName)
                .inputSignUpEmail(generatedEmail)
                .clickSignUpButton();

        step("Verify that 'Enter Account Information' is visible", () -> {
            softAssert.assertThat(signup.titlesInAllPages("Enter Account Information"))
                    .as("Enter Account Information is visible")
                    .isEqualToIgnoringCase("Enter Account Information");
        });

        var accountCreated = afterSignup.waitForPageLoaded()
                .chooseTitle("Mr").inputPassword("123")
                .markCheckBoxes(true, true)
                .selectRandomDateMonthYear()
                .inputUserFirstName(user).inputUserLastName(user)
                .inputCompanyName(user).inputUserAddress1(user)
                .inputUserAddress2(user).inputUserCountry()
                .inputUserState(user).inputUserCity(user)
                .inputUserZipCode(user).inputUserMobilePhone(user)
                .clickCreateAccountButton().waitForPageLoaded();

        step("Verify that 'Account Created!' is visible", () -> {
            softAssert.assertThat(accountCreated.titlesInAllPages("Account Created!"))
                    .as("   Account Created! is visible")
                    .isEqualToIgnoringCase("Account Created!");
        });

        var homeAfterContinue = accountCreated.clickContinueButton().waitForPageLoaded();

        String banner = homeAfterContinue.getLoggedInBannerText();
        step("Verify that 'Logged in as username' is visible", () -> {
            softAssert.assertThat(banner)
                    .as("'Logged in as <username>' is visible")
                    .isEqualToIgnoringCase("Logged in as " + generatedName);
        });

        var delete = homeAfterContinue.clickDeletedAccountTab().waitForPageLoaded();

        step("Verify that 'Account Deleted!' is visible", () -> {
            softAssert.assertThat(delete.titlesInAllPages("Account Deleted!"))
                    .as("'Account Deleted!' is visible")
                    .isEqualToIgnoringCase("Account Deleted!");
        });
        delete.clickContinueButton();

        softAssert.assertAll();
    }
}





