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

public class TestCase17 extends BaseTest {

    @Test
    @Order(17)
    @Owner("Eldiyar")
    @DisplayName("TestCase 17: Remove Products From Cart")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            "1. Launch browser
            "2. Navigate to url 'http://automationexercise.com'
            "3. Verify that home page is visible successfully
            "4. Add products to cart
            "5. Click 'Cart' button
            "6. Verify that cart page is displayed
            "7. Click 'X' button corresponding to particular product
            "8. Verify that product is removed from the cart""")
    void removeProductsFromCart() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var cartpage = home
                    .clickAddToCart("Blue Top")
                    .clickContinue()
                    .clickCartTab();

            step("Verify that cart page is displayed", () -> {
                softAssert.assertThat(cartpage.isPageTabActive("Cart"))
                        .as("Cart page is displayed")
                        .isTrue();

                step("Verify that product is removed from the cart", () -> {
                    String actualMessage = cartpage.xButtonClick()
                            .getEmptyCartMessage();
                    softAssert.assertThat(actualMessage)
                            .as("Verify that product is removed from the cart")
                            .isEqualTo("Cart is empty! Click here to buy products.");
                });
            });
        });
        softAssert.assertAll();
    }
}