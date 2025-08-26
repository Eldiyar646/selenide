package com.selenide.layers.web.page.createDeleteAccount;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.layers.web.page.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class AccountCreatedPage extends BasePage<AccountCreatedPage> {

    public AccountCreatedPage waitForPageLoaded() {
        $x("//*[text()='Account Created!']")
                .shouldBe(Condition.visible);
        return this;
    }


}

