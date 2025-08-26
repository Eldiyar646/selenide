package com.selenide.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.enums.ElementsOnPage;
import com.selenide.layers.web.manager.ElementManager;
import com.selenide.layers.web.page.cart.CartPage;
import com.selenide.layers.web.page.productsPage.ProductDetailPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.partialText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.ENTER;

public abstract class BasePage<T extends BasePage> {

    public abstract T waitForPageLoaded();

    protected abstract ElementsCollection getTitles();

    protected final ElementManager elementManager = new ElementManager();

    protected final ElementsCollection navBarElements =
            $x("//ul[contains(@class, 'navbar-nav')]").findAll(By.xpath("li"));

    protected final ElementsCollection cartPageElements =
            $x("//div[@class='table-responsive cart_info']").findAll(By.xpath("tr"));

    protected final ElementsCollection productCard =
            $$x("//div[@class='col-sm-4']//div[contains(@class,'product-image-wrapper')]");

    protected final ElementsCollection productInformation =
            $$x("//div[@class='product-information']");

    protected final ElementsCollection titlesInAllPages =
            $$x("//div[contains(@class,'row')]//h2");

    protected final ElementsCollection listOfTestCases =
            $x("//section[@id='form']").findAll(By.className("panel-group"));

    public boolean isPageTabActive(String tabName) {
        var activeTab = navBarElements.find(Condition.partialText(tabName))
                .find(By.tagName("a"));
        activeTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return true;
    }

    public boolean isTitleVisible(String titleText) {
        var title = titlesInAllPages.find(Condition.partialText(titleText));
        return title.exists() && title.is(Condition.visible);
    }

    public boolean isProductInformationVisible(String titleText) {
        var title = productInformation.find(Condition.partialText(titleText));
        return title.exists() && title.is(Condition.visible);
    }

    public boolean isSubscriptionAlertVisible() {
        return $x("//div[@class='alert-success alert']")
                .has(partialText("You have been successfully subscribed!"));
    }

    protected void clickNavBarTab(String tabName) {
        navBarElements.find(Condition.partialText(tabName)).click();
    }

    @Step("Click to view product {0}")
    public ProductDetailPage clickViewProduct(String productName) {
        var product = productCard.find(Condition.partialText(productName));
        var viewButton = product.$("a[href*='product_details']");
        elementManager.click(viewButton);
        return page(ProductDetailPage.class);
    }

    @Step("Enter email to Subscription field")
    public void enterEmailForSubscribe(String value) {
        elementManager.inputElementWithText(ElementsOnPage.SUBSCRIPTION.getElementById(), value + ENTER);
    }

    public CartPage clickViewCart() {
        elementManager.click($(byText("View Cart")));
        return page(CartPage.class);
    }

}


//    var homeElement = navBarElements
//            .find(Condition.partialText("Home"));
//        homeElement
//                .find(By.xpath("a"))
//                .shouldHave(Condition.attribute("style", "color: orange;"));
//        return this;