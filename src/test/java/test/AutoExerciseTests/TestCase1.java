package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.data.UserData;
import base.TestFlags;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import test.Tags;

import static com.selenide.data.UserData.faker;
import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)

public class TestCase1 extends BaseTest {

    UserData user = new UserData();

    @Test
    @Order(1)
    @Owner("Eldiyar")
    @DisplayName("Test Case 1: Register User")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Signup / Login' button
            5. Verify 'New User Signup!' is visible
            6. Enter name and email address
            7. Click 'Signup' button
            8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
            9. Fill details: Title, Name, Email, Password, Date of birth
            10. Select checkbox 'Sign up for our newsletter!'
            11. Select checkbox 'Receive special offers from our partners!'
            12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
            13. Click 'Create Account button'
            14. Verify that 'ACCOUNT CREATED!' is visible
            15. Click 'Continue' button
            16. Verify that 'Logged in as username' is visible
            17. Click 'Delete Account' button
            18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button""")
    void registerUser() {
        var softAssert = new SoftAssertions();

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

        String generatedName = faker.name().firstName();
        String generatedEmail = faker.internet().emailAddress();

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
                        .as("New User Signup! is visible")
                        .isEqualToIgnoringCase("New User Signup!");

                var afterSignup = signup
                        .inputSignUpName(generatedName)
                        .inputSignUpEmail(generatedEmail)
                        .clickSignUpButton();

                step("Verify that 'Enter Account Information' is visible", () -> {
                    softAssert.assertThat(signup.titlesInAllPages("Enter Account Information"))
                            .as("Enter Account Information is visible")
                            .isEqualToIgnoringCase("Enter Account Information");

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
                                .as("Account Created! is visible")
                                .isEqualToIgnoringCase("Account Created!");

                        var homeAfterContinue = accountCreated.clickContinueButton().waitForPageLoaded();

                        String banner = homeAfterContinue.getLoggedInBannerText();
                        step("Verify that 'Logged in as username' is visible", () -> {
                            softAssert.assertThat(banner)
                                    .as("'Logged in as <username>' is visible")
                                    .isEqualToIgnoringCase("Logged in as " + generatedName);

                            if (!TestFlags.shouldKeepAccount()) {
                                var delete = homeAfterContinue.clickDeletedAccountTab().waitForPageLoaded();

                                step("Verify that 'Account Deleted!' is visible", () -> {
                                    softAssert.assertThat(delete.titlesInAllPages("Account Deleted!"))
                                            .as("'Account Deleted!' is visible")
                                            .isEqualToIgnoringCase("Account Deleted!");

                                    delete.clickContinueButton();
                                });
                            }
                        });
                    });
                });
            });
        });
        softAssert.assertAll();
    }
}





