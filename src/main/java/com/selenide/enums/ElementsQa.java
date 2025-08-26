package com.selenide.enums;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public enum ElementsQa {

    NAME("name"),
    EMAIL("email"),
    SUBJECT("subject"),
    MESSAGE("message"),

    SIGNUP_NAME("signup-name"),
    SIGNUP_EMAIL("signup-email"),
    SIGNUP_BUTTON("signup-button"),

    LOGIN_EMAIL("login-email"),
    LOGIN_PASSWORD("login-password"),
    LOGIN_BUTTON("login-button"),

    CONTINUE_BUTTON("continue-button"),
    CREATE_ACCOUNT_BUTTON("create-account"),
    SUBMIT_BUTTON("submit-button"),
            ;

    private final String value;

    ElementsQa(String value) {
        this.value = value;
    }

    public SelenideElement getElement() {
        return $x("//*[@data-qa='" + value + "']").shouldBe(visible);
    }
    }