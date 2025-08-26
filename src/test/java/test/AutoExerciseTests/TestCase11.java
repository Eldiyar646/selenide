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


@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase11 extends BaseTest {

    @Test
    @Order(11)
    @Owner("Eldiyar")
    @DisplayName("Verify Subscription in Cart page")
    @Severity(SeverityLevel.BLOCKER)
    void verifySubscriptionOnCartTest() {

        Faker faker = new Faker();
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home tab should be active")
                    .isTrue();
        });


        var cartPage = home
                .clickCartTab()
                .waitForPageLoaded();

        step("Verify text SUBSCRIPTION", () -> {
            softAssert.assertThat(home.isTitleVisible("Subscription"))
                    .as("Text SUBSCRIPTION is visible")
                    .isTrue();
        });

        cartPage
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
