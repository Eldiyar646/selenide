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

public class TestCase6 extends BaseTest {

    @Test
    @Order(6)
    @Owner("Eldiyar")
    @Description("Contact Us Form")
    @Severity(SeverityLevel.BLOCKER)
    void contactUsFormTest() {
        var home = open(HomePage.class)
                .waitForPageLoaded();

        assertThat(home.isPageTabActive("Home"))
                .as("Home tab should be active")
                .isTrue();

        var contactUs = home.clickContactUsTab();

        assertThat(contactUs.isContactUsTabActive())
                .as("Contact Us tab should be active")
                .isTrue();
        assertThat(contactUs.isGetInTouchTitleVisible())
                .as("'Get In Touch' is visible")
                .isTrue();

        contactUs
                .inputName().inputEmail()
                .inputSubject()
                .inputMessage()
                .uploadFile().clickSubmitButton()
                .acceptAlert();

        assertThat(contactUs.isSuccessMessageVisible())
                .as("'Success! Your details have been submitted successfully.' is visible")
                .isTrue();

        assertThat(contactUs.isHomeButtonVisible())
                .as("Home button is visible")
                .isTrue();

        contactUs.clickHomeButton().waitForPageLoaded();

    }
}