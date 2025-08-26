package com.selenide.layers.web.page.accountCreate;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.selenide.enums.ElementsQa;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class AccountCreatedPage extends BasePage<AccountCreatedPage> {

    public AccountCreatedPage waitForPageLoaded() {
        $x("//*[text()='Account Created!']").shouldBe(Condition.visible);
        return this;
    }

    @Override
    protected ElementsCollection getTitles() {
        return titlesInAllPages;
    }

    @Step("Click continue button{0}")
    public HomePage clickContinueButton() {
        elementManager.click(ElementsQa.CONTINUE_BUTTON.getElement());
        return Selenide.page(HomePage.class);
    }
}
