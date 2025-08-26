package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
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

/*
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Scroll down to footer
5. Verify text 'SUBSCRIPTION'
6. Enter email address in input and click arrow button
7. Verify success message 'You have been successfully subscribed!' is visible
*/

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase10 extends BaseTest {

    @Test
    @Order(8)
    @Owner("Eldiyar")
    @DisplayName("Verify Subscription in home page")
    @Severity(SeverityLevel.BLOCKER)
    void verifySubscriptionTest() {
        Faker faker = new Faker();
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home tab should be active")
                    .isTrue();
        });

        home.subscriptionTitle();

        step("Verify text SUBSCRIPTION", () -> {
            softAssert.assertThat(home.isTitleVisible("Subscription"))
                    .as("Text SUBSCRIPTION is visible")
                    .isTrue();
        });

        home
                .enterEmailForSubscribe(faker.internet()
                        .emailAddress());

        step("Verify success message You have been successfully subscribed! is visible", () -> {
            softAssert.assertThat(home.isSubscriptionAlertVisible())
                    .as("You have been successfully subscribed! is visible")
                    .isTrue();
        });

        softAssert.assertAll();
    }
}