package com.selenide.enums;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public enum ElementsOnPage {

    TITLE_MR("id_gender1"),
    TITLE_MRS("id_gender2"),
    PASSWORD("password"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    COMPANY("company"),
    FIRST_ADDRESS("address"),
    SECOND_ADDRESS("address2"),
    COUNTRY("country"),
    STATE("state"),
    CITY("city"),
    ZIP_CODE("zipcode"),
    PHONE("mobile_number"),

    SEARCH("search_product"),
    SUBSCRIPTION("susbscribe_email"),
    QUANTITY("quantity"),

    NAME_ON_CARD("name-on-card"),
    CARD_NUMBER("card-number"),
    CVC("cvc"),
    EXPIRE_MONTH("expiry-month"),
    EXPIRE_YEAR("expiry-year"),
    SUBMIT("submit"),

    ;

    private final String value;

    ElementsOnPage(String value) {
        this.value = value;
    }

    public SelenideElement getElementFromAccount() {
        return $x("//*[@data-qa='" + value + "']");
    }

    public SelenideElement getElementById() {
        return $x("//*[@id='" + value + "']").shouldBe(Condition.visible);
    }




}
