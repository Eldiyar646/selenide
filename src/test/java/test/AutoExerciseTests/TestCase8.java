package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
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
@Tag(Tags.WEB)

/*
1. Launch browser
2. Navigate to url 'http://automationexercise.com'
3. Verify that home page is visible successfully
4. Click on 'Products' button
5. Verify user is navigated to ALL PRODUCTS page successfully
6. The products list is visible
7. Click on 'View Product' of first product
8. User is landed to product detail page
9. Verify that detail detail is visible: product name, category, price, availability, condition, brand
*/
public class TestCase8 extends BaseTest {

    @Test
    @Order(8)
    @Owner("Eldiyar")
    @DisplayName("Verify All Products and product detail page")
    @Severity(SeverityLevel.BLOCKER)
    void verifyAllProductsTest() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home tab should be active")
                    .isTrue();
        });

        var product = home.clickProductsTab()
                .waitForPageLoaded();

        step("Verify user is navigated to ALL PRODUCTS page successfully", () -> {
            softAssert.assertThat(product.isPageTabActive("Products"))
                    .as("Products tab should be active")
                    .isTrue();
        });

        step("The products list is visible", () -> {
            softAssert.assertThat(product.isProductsListVisible().isTitleVisible("All products"))
                    .as("All products title and list should be visible")
                    .isTrue();
        });

        product.clickViewProduct("Blue top").waitForPageLoaded();

        step("Verify that detail is visible: product name, category, price, availability, condition, brand", () -> {
            softAssert.assertThat(product.isProductInformationVisible("Product name, Category, Price, Availability, Condition, Brand"))
                    .as("All details are visible")
                    .isTrue();
        });

        softAssert.assertAll();







    }
}
