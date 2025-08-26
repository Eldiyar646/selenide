package com.selenide.layers.web.page.productsPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.enums.ElementsOnPage;
import com.selenide.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.ENTER;

public class ProductsPage extends BasePage<ProductsPage> {

    @Override
    @Step("Wait for page is loaded{0}")
    public ProductsPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Products"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("Wait products list will visible {0}")
    public ProductsPage isProductsListVisible() {
        $x("//div[@class='features_items']").scrollIntoView(true).shouldBe(visible);
        return this;
    }

    public ProductsPage searchProduct(String value) {
        elementManager.inputElementWithText(ElementsOnPage.SEARCH.getElementById(), value + ENTER);
        return this;
    }

    @Step("Check if searched products are visible")
    public boolean isAllRelatedProductVisible() {
        return !productCard.filter(Condition.visible).isEmpty();
    }

    @Step("Add product {0}")
    public ProductsPage clickAddProduct(String productName) {
        var product = productCard.find(Condition.partialText(productName));
        var addButton = product.$("a.add-to-cart");
        elementManager.click(addButton);
        return this;
    }

    public ProductsPage clickContinueButton(){
        elementManager.click($(byText("Continue Shopping")));
        return page(ProductsPage.class);
    }






//    public String findProductByName (String name) {
//        var productName = productCard
//                .find(Condition.partialText(name));
//        return productName.$("p").getText();
//    }
//
//    public ProductsPage clickElementsOnPage(String productName) {
//        var pElement = productCard
//                .find(Condition.partialText(productName));
//        var viewLink = pElement.$("a:contains('View Product')");
//        elementManager.click(viewLink);
//        return page(ProductsPage.class);
//    }

    @Override
    protected ElementsCollection getTitles() {
        return titlesInAllPages;
    }


}
