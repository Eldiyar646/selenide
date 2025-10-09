package com.selenide.layers.web.manager;

import com.codeborne.selenide.SelenideElement;
import com.selenide.enums.CommonAttributes;
import com.selenide.layers.web.page.signup.SignUpPage;
import org.openqa.selenium.ElementClickInterceptedException;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import com.codeborne.selenide.ex.InvalidStateError;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

public class ElementManager {
    private final int DELAY = 30;

    public ElementManager click(SelenideElement element) {
        try {
            element
                    .shouldBe(visible, Duration.ofSeconds(DELAY))
                    .shouldHave(enabled, Duration.ofSeconds(DELAY))
                    .shouldNotHave(attribute("disabled"))
                    .shouldBe(clickable, Duration.ofSeconds(DELAY))
                    .scrollIntoView(true)
                    .click();
        } catch (ElementClickInterceptedException | InvalidStateError e) {
            // Fallback to JS click if something intercepts the element
            element.scrollIntoView(true);
            executeJavaScript("arguments[0].click();", element);
        }
        return this;
    }

    public ElementManager inputElementWithText(SelenideElement element, String text) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .scrollTo().setValue(text);
        return this;
    }

    public ElementManager inputOnlyElement(SelenideElement element) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY)).scrollIntoView(true);
        return this;
    }

    public ElementManager select(SelenideElement element, String option) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldHave(enabled, Duration.ofSeconds(DELAY))
                .scrollIntoView(true)
                .selectOption(option);
        return this;
    }

    public String getText(SelenideElement element) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldNotBe(empty, Duration.ofSeconds(DELAY));
        return element.getText();
    }

    public ElementManager shouldNotHaveAttribute(SelenideElement element, CommonAttributes attribute, String attributeValue) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldNotHave(attribute(attribute.value, attributeValue));
        return this;
    }

    public ElementManager shouldHaveAttribute(SelenideElement element, CommonAttributes attribute, String attributeValue) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldHave(attribute(attribute.value, attributeValue));
        return this;
    }

    public ElementManager shouldHaveText(SelenideElement element, String title) {
        element
                .shouldBe(visible, Duration.ofSeconds(DELAY))
                .shouldHave(text(title));
                return this;
    }
}