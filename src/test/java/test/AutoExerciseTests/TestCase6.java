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

public class TestCase6 extends BaseTest {

    @Test
    @Order(6)
    @Owner("Eldiyar")
    @DisplayName("Test Case 6: Contact Us Form")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Contact Us' button
            5. Verify 'GET IN TOUCH' is visible
            6. Enter name, email, subject and message
            7. Upload file
            8. Click 'Submit' button
            9. Click OK button
            10. Verify success message 'Success! Your details have been submitted successfully.' is visible
            11. Click 'Home' button and verify that landed to home page successfully""")
    void contactUsForm() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var contactUs = home.clickContactUsTab();

            step("Verify 'Get In Touch' is visible", () -> {
                softAssert.assertThat(home.titlesInAllPages("Get In Touch"))
                        .as("'Get In Touch' is visible")
                        .isEqualToIgnoringCase("Get In Touch");

                contactUs
                        .inputName().inputEmail()
                        .inputSubject()
                        .inputMessage()
                        .uploadFile().clickSubmitButton()
                        .acceptAlert();

                step("Verify success message 'Success! Your details have been submitted successfully.' is visible", () -> {
                    softAssert.assertThat(contactUs.getMessageText("Success! Your details have been submitted successfully."))
                            .isEqualTo("Success! Your details have been submitted successfully.");

                    contactUs.clickHomeButton().waitForPageLoaded();

                    step("Verify that home page is visible successfully", () -> {
                        softAssert.assertThat(home.isPageTabActive("Home"))
                                .as("Home page is loaded and visible successfully")
                                .isTrue();
                    });
                });
            });
        });
        softAssert.assertAll();
    }
}