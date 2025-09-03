package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.cart.ProductsList;
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

public class TestCase12 extends BaseTest {

    @Test
    @Order(12)
    @Owner("Eldiyar")
    @DisplayName("Test Case 12: Add Products in Cart")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click 'Products' button
            5. Hover over first product and click 'Add to cart'
            6. Click 'Continue Shopping' button
            7. Hover over second product and click 'Add to cart'
            8. Click 'View Cart' button
            9. Verify both products are added to Cart
            10. Verify their prices, quantity and total price""")
    void addProductsInCart() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var cartPage = home
                    .clickProductsTab()
                    .clickAddProduct("Blue Top")
                    .clickContinue()
                    .clickAddProduct("Men Tshirt")
                    .clickViewCart();

            var cartItems = cartPage.getCartItems();

            step("Verify both products are added to Cart" +
                    "Verify their prices, quantity and total price", () -> {
                softAssert.assertThat(cartItems)
                        .extracting(ProductsList::getName)
                        .contains("Blue Top", "Men Tshirt");

                softAssert.assertThat(cartItems)
                        .extracting(ProductsList::getQuantity)
                        .allMatch(q -> q == 1);

                softAssert.assertThat(cartItems)
                        .extracting(ProductsList::getPrice)
                        .contains("Rs. 500", "Rs. 400");

                softAssert.assertThat(cartItems)
                        .extracting(ProductsList::getTotal)
                        .contains("Rs. 500", "Rs. 400");
            });
        });
        softAssert.assertAll();
    }

}
