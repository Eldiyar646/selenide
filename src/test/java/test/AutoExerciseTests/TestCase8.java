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
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        var product = home.clickProductsTab()
                .waitForPageLoaded();

        step("Verify user is navigated to ALL PRODUCTS page successfully", () -> {
            softAssert.assertThat(product.titlesInAllPages("All Products"))
                    .as("'All Products' title should be visible")
                    .isEqualToIgnoringCase("All Products");

        });

        step("The products list is visible", () -> {
            softAssert.assertThat(product.isProductsListVisible())
                    .as("Product list should be visible")
                    .isTrue();
        });

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

        softAssert.assertAll();
    }
}
