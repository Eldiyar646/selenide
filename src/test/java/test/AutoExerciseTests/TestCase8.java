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

public class TestCase8 extends BaseTest {

    @Test
    @Order(8)
    @Owner("Eldiyar")
    @DisplayName("Test Case 8: Verify All Products and product detail page")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Products' button
            5. Verify user is navigated to ALL PRODUCTS page successfully
            6. The products list is visible
            7. Click on 'View Product' of first product
            8. User is landed to product detail page
            9. Verify that detail detail is visible: product name, category, price, availability, condition, brand""")
    void verifyAllProductsAndProductDetailPage() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var product = home.clickProductsTab()
                    .waitForPageLoaded();

            step("Verify user is navigated to ALL PRODUCTS page successfully", () -> {
                softAssert.assertThat(product.titlesInAllPages("All Products"))
                        .as("'All Products' title should be visible")
                        .isEqualToIgnoringCase("All Products");

                step("The products list is visible", () -> {
                    softAssert.assertThat(product.isProductsListVisible())
                            .as("Product list should be visible")
                            .isTrue();

                    product
                            .clickViewProduct("Blue top")
                            .waitForPageLoaded();

                    step("Verify that detail is visible: product name, category, price, availability, condition, brand", () ->

                    {
                        softAssert.assertThat(product.isProductNameVisible("Blue Top"))
                                .as("Category should be visible").isTrue();
                        softAssert.assertThat(product.isProductInformationVisible("Category"))
                                .as("Category should be visible").isTrue();
                        softAssert.assertThat(product.isProductInformationVisible("Rs."))
                                .as("Rs. should be visible").isTrue();
                        softAssert.assertThat(product.isProductInformationVisible("Availability"))
                                .as("Availability should be visible").isTrue();
                        softAssert.assertThat(product.isProductInformationVisible("Condition"))
                                .as("Condition should be visible").isTrue();
                        softAssert.assertThat(product.isProductInformationVisible("Brand"))
                                .as("Brand should be visible").isTrue();
                    });
                });
            });
        });
        softAssert.assertAll();
    }
}
