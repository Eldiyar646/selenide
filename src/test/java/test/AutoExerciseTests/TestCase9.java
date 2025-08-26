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
    @Order(8)
    @Owner("Eldiyar")
    @DisplayName("Search Producte")
    @Severity(SeverityLevel.BLOCKER)
    void searchProductTest() {
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
            softAssert.assertThat(product.isTitleVisible("All products"))
                    .as("All products title should be visible")
                    .isTrue();
        });

        var search = product
                .searchProduct("Tie");

        step("Verify 'SEARCHED PRODUCTS' is visible", () -> {
            softAssert.assertThat(product.isTitleVisible("Searched Products"))
                    .as("Searched Products are visible")
                    .isTrue();
        });

        step("Verify Searched Products is visible", () -> {
            softAssert.assertThat(product.isAllRelatedProductVisible())
                    .as("Products should be visible after search")
                    .isTrue();
        });

        softAssert.assertAll();

    }
}
