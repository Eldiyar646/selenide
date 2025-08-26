package com.selenide.layers.web.page.productsPage;

import com.codeborne.selenide.ElementsCollection;
import com.selenide.enums.ElementsOnPage;
import com.selenide.layers.web.page.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class ProductDetailPage extends BasePage<ProductDetailPage> {


    @Override
    @Step("Wait for page is loaded{0}")
    public ProductDetailPage waitForPageLoaded() {
        $x("//div[@class='product-information']").shouldHave(text("Availability:"));
        return this;
    }

    public ProductDetailPage clearAndInputDigit(String value){
        var input = ElementsOnPage.QUANTITY.getElementById();
        input.clear();
        input.setValue(value);
        return this;
    }

    public ProductDetailPage clickAddToCart(){
        var addToCart = $x("//button[contains(normalize-space(.), 'Add to cart')]");
        elementManager.click(addToCart);
        return this;
    }




//div[@class='product-information'])))

    @Override
    protected ElementsCollection getTitles() {
        return titlesInAllPages;
    }
}

