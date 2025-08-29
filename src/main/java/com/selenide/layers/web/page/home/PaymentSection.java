package com.selenide.layers.web.page.home;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.selenide.enums.ElementsOnPage;
import com.selenide.layers.web.page.BasePage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class PaymentSection extends BasePage<PaymentSection> {

    @Override
    public PaymentSection waitForPageLoaded() {
        $x("//form[@id='payment-form']").shouldBe(Condition.visible);
        return this;
    }

    public PaymentSection inputCardInformation() {
        elementManager.inputOnlyElement(ElementsOnPage.NAME_ON_CARD.getElementFromAccount().setValue("Eld"));
        elementManager.inputOnlyElement(ElementsOnPage.CARD_NUMBER.getElementFromAccount().setValue("1253659874"));
        elementManager.inputOnlyElement(ElementsOnPage.CVC.getElementFromAccount().setValue("125"));
        elementManager.inputOnlyElement(ElementsOnPage.EXPIRE_MONTH.getElementFromAccount().setValue("12"));
        elementManager.inputOnlyElement(ElementsOnPage.EXPIRE_YEAR.getElementFromAccount().setValue("2026"));
        return this;
    }

    @Step("Click pay and confirm button")
    public HomePage clickPayAndConfirmButton() {
        elementManager.click(ElementsOnPage.SUBMIT.getElementById());
        return page(HomePage.class);

    }

    //    public boolean isSubscriptionAlertVisible() {
//        try {
//            $x("//div[@class='alert-success alert' and contains(text(),'Your order has been placed successfully!')]")
//                    .shouldBe(Condition.appear, Duration.ofSeconds(5));
//            return true;
//        } catch (UIAssertionError e) {
//            return false;
//        }
    public String getSubscriptionAlertText() {
            return $x("//div[@class='alert-success alert']")
                    .should(Condition.appear, Duration.ofSeconds(2))
                    .getText()
                    .trim();
        }
    }



