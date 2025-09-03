package com.selenide.layers.web.page.cart;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.home.PaymentSection;
import com.selenide.layers.web.page.signup.SignUpPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CartPage extends BasePage<CartPage> {

    private SelenideElement xButton1 = $("a[data-product-id='1']");
    private SelenideElement emptyCartSpan = $("#empty_cart");
    private ElementsCollection xButtons = $$("a.cart_quantity_delete");

    @Override
    public CartPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Cart"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("Get all items from cart")
    public List<ProductsList> getCartItems() {
        ElementsCollection rows = $$x("//tr[contains(@id,'product')]");

        return rows.stream().map(row -> {
            String name = row.$("td.cart_description h4").getText();
            int quantity = Integer.parseInt(row.$("td.cart_quantity").getText().trim());
            String price = row.$("td.cart_price").getText();
            String total = row.$("td.cart_total").getText();
            return new ProductsList(name, quantity, price, total);
        }).toList();
    }

    @Step("Click 'X' button for first product")
    public CartPage xButtonClick() {
        xButton1.click();
        return this;
    }

    @Step("Delete all added products from the cart")
    public CartPage deleteAllAddedProducts() {
        while (!xButtons.isEmpty()) {
            xButtons.first().click();
            xButtons.first().should(Condition.disappear, Duration.ofSeconds(10));
        }
        return this;
    }

    @Step("Get empty cart message")
    public String getEmptyCartMessage() {
        return emptyCartSpan.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .getText();
    }

    public CartPage clickProceedToCheckout() {
        elementManager.click($(byText("Proceed To Checkout")));
        return this;
    }

    public SignUpPage clickRegisterLogin() {
        elementManager.click($(byText("Register / Login")));
        return page(SignUpPage.class);
    }

    public CartPage enterDescriptionInComment(String title) {
        elementManager.inputOnlyElement($x("//textarea[@name='message']"));
        return this;
    }

    public PaymentSection clickToPlaceOrder() {
        elementManager.click($(byText("Place Order")));
        return page(PaymentSection.class);
    }


}

