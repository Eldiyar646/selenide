package com.selenide.layers.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.selenide.data.UserData;
import com.selenide.enums.ElementsOnPage;
import com.selenide.enums.ElementsQa;
import com.selenide.layers.web.manager.ElementManager;
import com.selenide.layers.web.page.cart.CartPage;
import com.selenide.layers.web.page.home.HomePage;
import com.selenide.layers.web.page.productsPage.ProductDetailPage;
import com.selenide.layers.web.page.productsPage.ProductsPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.partialText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.ENTER;

public abstract class BasePage<T extends BasePage> {

    public abstract T waitForPageLoaded();

    protected final ElementManager elementManager = new ElementManager();

    protected final ElementsCollection navBarElements =
            $x("//ul[contains(@class, 'navbar-nav')]").findAll(By.xpath("li"));

    protected final ElementsCollection cartPageElements =
            $x("//div[@class='table-responsive cart_info']").findAll(By.xpath("tr"));

    protected final ElementsCollection productCard =
            $$x("//div[@class='col-sm-4']//div[contains(@class,'product-image-wrapper')]");

    protected final ElementsCollection productInformation =
            $$x("//div[@class='product-information']");

    protected final ElementsCollection listOfTestCases =
            $x("//section[@id='form']").findAll(By.className("panel-group"));

    protected final SelenideElement productName =
            $x("//div[@class='product-information']/h2");

    public boolean isPageTabActive(String tabName) {
        var activeTab = navBarElements.find(Condition.partialText(tabName))
                .find(By.tagName("a"));
        activeTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return true;
    }

    @Step("Check title in page")
    public String titlesInAllPages (String value) {
        return $x("//h2[contains(.,'" + value + "')]")
                .shouldBe(Condition.visible).getText();
    }

    @Step("Check all details of product ")
    public boolean isProductInformationVisible(String titleText) {
        var title = productInformation.find(Condition.partialText(titleText));
        return title.exists() && title.is(Condition.visible);
    }

    @Step("Check if product name is visible")
    public boolean isProductNameVisible(String expectedName) {
        return productName.shouldBe(Condition.visible).getText().equals(expectedName);
    }

    public boolean isSubscriptionAlertVisible() {
        try {
            var alert = $x("//div[@class='alert-success alert']");
            alert.shouldBe(Condition.visible, Duration.ofSeconds(10));
            return alert.has(partialText("You have been successfully subscribed!"));
        } catch (Throwable e) {
            return false;
        }
    }

    protected void clickNavBarTab(String tabName) {
        var tab = navBarElements.find(Condition.partialText(tabName)).find(By.tagName("a"));
        elementManager.click(tab);
    }

    @Step("Click continue button{0}")
    public HomePage clickContinueButton() {
        elementManager.click(ElementsQa.CONTINUE_BUTTON.getElement());
        return Selenide.page(HomePage.class);
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
        elementManager.inputElementWithText(ElementsOnPage.SUBSCRIPTION.getElementById(), value);
        elementManager.click($("#subscribe"));
    }

    public CartPage clickViewCart() {
        elementManager.click($(byText("View Cart")));
        return page(CartPage.class);
    }

    @SuppressWarnings("unchecked")
    protected T self(){
        return (T) this;
    }

    protected <P extends BasePage<P>> P goTo(Class<P> pageClass) {
        return page(pageClass);
    }

    public T clickAddToCart(String title) {
        elementManager.click($(byText("Add to cart")));
        return self();
    }

    @Step("Click continue shopping link")
    public T clickContinue() {
        elementManager.click($(byText("Continue Shopping")));
        return self();
    }

    @Step("Wait products list will visible")
    public boolean isProductsListVisible() {
        return ($x("//div[@class='features_items']").has(visible));
    }

    @Step("Check if searched products are visible")
    public boolean isAllRelatedProductVisible() {
        return !productCard.filter(Condition.visible).isEmpty();
    }

    @Step("Get alert text from all pages")
    public String getMessageText(String expectedMessage) {
        return $(byText(expectedMessage))
                .shouldBe(Condition.visible)
                .getText();
    }

    public UserData getDeliveryAddress() {
        UserData data = new UserData();

        String nameText = $x("//ul[@id='address_delivery']//li[contains(@class,'address_firstname') and contains(@class,'address_lastname')]")
                .getText().trim();
        String[] nameParts = nameText.split("\\s+");
        int startIndex = (nameParts[0].endsWith(".") ? 1 : 0);
        if (nameParts.length > startIndex) {
            data.setFirstName(nameParts[startIndex]);
        }
        if (nameParts.length > startIndex + 1) {
            data.setLastName(String.join(" ", Arrays.copyOfRange(nameParts, startIndex + 1, nameParts.length)));
        }

        ElementsCollection addressLines = $$x("//ul[@id='address_delivery']//li[contains(@class,'address_address1') and contains(@class,'address_address2')]");
        if (addressLines.size() >= 1) data.setCompanyName(addressLines.get(0).getText().trim());
        if (addressLines.size() >= 2) data.setAddress1(addressLines.get(1).getText().trim());
        if (addressLines.size() >= 3) data.setAddress2(addressLines.get(2).getText().trim());

        String cityStateZip = $x("//ul[@id='address_delivery']//li[contains(@class,'address_city') and contains(@class,'address_state_name') and contains(@class,'address_postcode')]")
                .getText().trim();
        String[] parts = cityStateZip.split("\\s+");
        if (parts.length >= 1) {
            data.setZipCode(parts[parts.length - 1]);
            data.setCityState(String.join(" ", Arrays.copyOf(parts, parts.length - 1)));
        } else {
            data.setCityState(cityStateZip);
        }

        data.setCountry($x("//ul[@id='address_delivery']//li[contains(@class,'address_country_name')]")
                .getText().trim());

        data.setPhone($x("//ul[@id='address_delivery']//li[contains(@class,'address_phone')]")
                .getText().trim());

        return data;
    }



}


//    var homeElement = navBarElements
//            .find(Condition.partialText("Home"));
//        homeElement
//                .find(By.xpath("a"))
//                .shouldHave(Condition.attribute("style", "color: orange;"));
//        return this;