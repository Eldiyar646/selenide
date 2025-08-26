package com.selenide.layers.web.page.productsPage;

import com.codeborne.selenide.Condition;
import com.selenide.enums.ElementsOnPage;
import com.selenide.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$x;

public class ProductDetailPage extends BasePage<ProductDetailPage> {


    @Override
    @Step("Wait for page is loaded{0}")
    public ProductDetailPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Products"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("Clear and input quantity")
    public ProductDetailPage clearAndInputDigit(String value) {
        var input = ElementsOnPage.QUANTITY.getElementById();
        input.clear();
        input.setValue(value);
        return this;
    }

    @Step("Click add to cart button")
    public ProductDetailPage clickAddToCart() {
        var addToCart = $x("//button[contains(normalize-space(.), 'Add to cart')]");
        elementManager.click(addToCart);
        return this;
    }


}

//div[@class='product-information'])))

