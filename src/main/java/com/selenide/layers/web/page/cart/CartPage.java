package com.selenide.layers.web.page.cart;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;

public class CartPage extends BasePage<CartPage> {

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

}

