package com.selenide.layers.web.page.deleteAccount;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.selenide.enums.ElementsQa;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class DeleteAccount extends BasePage<DeleteAccount> {

    public DeleteAccount waitForPageLoaded() {
        $x("//*[text()='Account Deleted!']").shouldBe(Condition.visible);
        return this;
    }

    @Override
    protected ElementsCollection getTitles() {
        return titlesInAllPages;
    }

    @Step("Check account deleted visible{0}")
    public boolean isAccountDeletedMessageVisible() {
        return $(byText("Account Deleted!")).is(visible);
    }

    public HomePage clickContinueButton() {
        elementManager.click(ElementsQa.CONTINUE_BUTTON.getElement());
        return Selenide.page(HomePage.class);
    }

}
