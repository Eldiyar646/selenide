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

public class TestCase9 extends BaseTest {

    @Test
    @Order(9)
    @Owner("Eldiyar")
    @DisplayName("Search Product")
    @Severity(SeverityLevel.BLOCKER)
    void searchProductTest() {
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

        var search = product
                .inputSearchProduct("Blue Top")
                        .clickSearchProduct();

        step("Verify 'SEARCHED PRODUCTS' is visible", () -> {
            softAssert.assertThat(search.titlesInAllPages("Searched Products"))
                    .as("'SEARCHED PRODUCTS' title should be visible")
                    .isEqualToIgnoringCase("Searched Products");
        });

        step("Verify searched products are visible", () -> {
            softAssert.assertThat(search.isAllRelatedProductVisible())
                    .as("All related products should be visible")
                    .isTrue();
        });

        softAssert.assertAll();
    }
}
