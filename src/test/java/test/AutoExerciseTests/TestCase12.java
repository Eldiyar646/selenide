package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.cart.ProductsList;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import test.Tags;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase12 extends BaseTest {

    @Test
    @Order(12)
    @Owner("Eldiyar")
    @DisplayName("Add Products in Cart")
    @Severity(SeverityLevel.BLOCKER)
    void addProductTest() {

        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

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

        softAssert.assertAll();
    }

}
