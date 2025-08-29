package com.selenide.layers.web.page.productsPage;

import com.codeborne.selenide.Condition;
import com.selenide.enums.ElementsOnPage;
import com.selenide.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;


public class ProductsPage extends BasePage<ProductsPage> {

    @Override
    @Step("Wait for page is loaded{0}")
    public ProductsPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Products"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("Input name on search line")
    public ProductsPage inputSearchProduct(String value) {
        elementManager.inputElementWithText(ElementsOnPage.SEARCH.getElementById(), value);
        return this;
    }

    @Step("Click on search tab")
    public ProductsPage clickSearchProduct() {
        elementManager.click($("#submit_search"));
        return this;
    }

    @Step("Click add product button")
    public ProductsPage clickAddProduct(String productName) {
        var product = productCard.find(Condition.partialText(productName));
        var addButton = product.$("a.add-to-cart");
        elementManager.click(addButton);
        return this;
    }
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

