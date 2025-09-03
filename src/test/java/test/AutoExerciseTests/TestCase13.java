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

import java.util.List;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)

public class TestCase13 extends BaseTest {

    @Test
    @Order(13)
    @Owner("Eldiyar")
    @DisplayName("Test Case 13: Verify Product quantity in Cart")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click 'View Product' for any product on home page
            5. Verify product detail is opened
            6. Increase quantity to 4
            7. Click 'Add to cart' button
            8. Click 'View Cart' button
            9. Verify that product is displayed in cart page with exact quantity""")
    void verifyProductQuantityInCart() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var productsPage = home.clickViewProduct("Blue top")
                    .waitForPageLoaded();

            step("Verify product detail is opened", () -> {
                softAssert.assertThat(productsPage.isProductInformationVisible("Category"))
                        .as("Category should be visible").isTrue();
                softAssert.assertThat(productsPage.isProductInformationVisible("Rs."))
                        .as("Rs. should be visible").isTrue();
                softAssert.assertThat(productsPage.isProductInformationVisible("Availability"))
                        .as("Availability should be visible").isTrue();
                softAssert.assertThat(productsPage.isProductInformationVisible("Condition"))
                        .as("Condition should be visible").isTrue();
                softAssert.assertThat(productsPage.isProductInformationVisible("Brand"))
                        .as("Brand should be visible").isTrue();

                var detailPage = productsPage
                        .clearAndInputDigit("4")
                        .clickAddToCart()
                        .clickViewCart();

                List<ProductsList> viewCart = detailPage.waitForPageLoaded().getCartItems();

                ProductsList product = viewCart
                        .stream()
                        .filter(p -> p.getName()
                                .equals("Blue Top") && p.getQuantity() == 4)
                        .findFirst()
                        .orElseThrow(() -> new AssertionError("Product Blue Top with quantity 4 not found"));

                step("Verify that product is displayed in cart page with exact quantity", () -> {
                    softAssert.assertThat(product.getQuantity())
                            .as("Quantity should match")
                            .isEqualTo(4);
                });
            });
        });
        softAssert.assertAll();
    }
}