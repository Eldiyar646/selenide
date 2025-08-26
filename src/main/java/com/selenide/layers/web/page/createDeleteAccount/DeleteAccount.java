package com.selenide.layers.web.page.createDeleteAccount;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.layers.web.page.BasePage;

import static com.codeborne.selenide.Selenide.$x;


public class DeleteAccount extends BasePage<DeleteAccount> {

    public DeleteAccount waitForPageLoaded() {
        $x("//*[text()='Account Deleted!']")
                .shouldBe(Condition.visible);
        return this;
    }

}
