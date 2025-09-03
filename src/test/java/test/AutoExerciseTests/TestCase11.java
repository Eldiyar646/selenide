package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static io.qameta.allure.Allure.step;


@Tag(Tags.SMOKE)

public class TestCase11 extends BaseTest {

    @Test
    @Order(11)
    @Owner("Eldiyar")
    @DisplayName("Test Case 11: Verify Subscription in Cart page")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click 'Cart' button
            5. Scroll down to footer
            6. Verify text 'SUBSCRIPTION'
            7. Enter email address in input and click arrow button
            8. Verify success message 'You have been successfully subscribed!' is visible""")
    void verifySubscriptionInCartPage() {

        Faker faker = new Faker();
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var cartPage = home
                    .clickCartTab()
                    .waitForPageLoaded();

            step("Verify text SUBSCRIPTION", () -> {
                softAssert.assertThat(home.titlesInAllPages("Subscription"))
                        .as("Text SUBSCRIPTION is visible")
                        .isEqualToIgnoringCase("Subscription");

                cartPage
                        .enterEmailForSubscribe(faker.internet()
                                .emailAddress());

                step("Verify success message You have been successfully subscribed! is visible", () -> {
                    softAssert.assertThat(home.isSubscriptionAlertVisible())
                            .as("You have been successfully subscribed! is visible")
                            .isEqualTo("You have been successfully subscribed!");
                });
            });
        });
        softAssert.assertAll();
    }
}
