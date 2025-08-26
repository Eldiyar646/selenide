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

public class TestCase6 extends BaseTest {

    @Test
    @Order(6)
    @Owner("Eldiyar")
    @Description("Contact Us Form")
    @Severity(SeverityLevel.BLOCKER)
    void contactUsFormTest() {

        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        var contactUs = home.clickContactUsTab();

        step("Verify 'Get In Touch' is visible", () -> {
            softAssert.assertThat(home.titlesInAllPages("Get In Touch"))
                    .as("'Get In Touch' is visible")
                    .isEqualToIgnoringCase("Get In Touch");
        });

        contactUs
                .inputName().inputEmail()
                .inputSubject()
                .inputMessage()
                .uploadFile().clickSubmitButton()
                .acceptAlert();

        step("Verify success message 'Success! Your details have been submitted successfully.' is visible", () -> {
            softAssert.assertThat(contactUs.getMessageText("Success! Your details have been submitted successfully."))
                    .isEqualTo("Success! Your details have been submitted successfully.");
        });

        contactUs.clickHomeButton().waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        softAssert.assertAll();

    }
}