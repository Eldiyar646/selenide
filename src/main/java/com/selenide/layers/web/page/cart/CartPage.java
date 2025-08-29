package com.selenide.layers.web.page.cart;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.data.UserData;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.home.PaymentSection;
import com.selenide.layers.web.page.signup.SignUpPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

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

    public CartPage clickProceedToCheckout() {
        elementManager.click($(byText("Proceed To Checkout")));
        return this;
    }

    public SignUpPage clickRegisterLogin() {
        elementManager.click($(byText("Register / Login")));
        return page(SignUpPage.class);
    }

    public CartPage enterDescriptionInComment(String title) {
        elementManager.inputOnlyElement($x("    //textarea[@name='message']"));
        return this;
    }

    public PaymentSection clickToPlaceOrder() {
        elementManager.click($(byText("Place Order")));
        return page(PaymentSection.class);
    }


}

