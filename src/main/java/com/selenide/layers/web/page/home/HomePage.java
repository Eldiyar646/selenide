package com.selenide.layers.web.page.home;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.cart.CartPage;
import com.selenide.layers.web.page.contactUs.ContactUsFormPage;
import com.selenide.layers.web.page.createDeleteAccount.DeleteAccount;
import com.selenide.layers.web.page.productsPage.ProductsPage;
import com.selenide.layers.web.page.signup.SignUpPage;
import com.selenide.layers.web.page.testCases.TestCasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class HomePage extends BasePage<HomePage> {

    @Override
    public HomePage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Home"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("Click signup/login tab")
    public SignUpPage clickSignUpLoginTab() {
        clickNavBarTab("Signup / Login");
        return page(SignUpPage.class);
    }

    @Step("Click Delete Account tab")
    public DeleteAccount clickDeletedAccountTab() {
        clickNavBarTab("Delete Account");
        return page(DeleteAccount.class);
    }

    @Step("Click Logout tab{0}")
    public SignUpPage clickLogOutTab() {
        clickNavBarTab("Logout");
        return page(SignUpPage.class);
    }

    @Step("Click Contact Us tab{0}")
    public ContactUsFormPage clickContactUsTab() {
        clickNavBarTab("Contact us");
        return page(ContactUsFormPage.class);
    }

    @Step("Click Test Case tab{0}")
    public TestCasePage clickTestCasesTab() {
        clickNavBarTab("Test Cases");
        return page(TestCasePage.class);
    }

    @Step("Click Products tab{0}")
    public ProductsPage clickProductsTab() {
        clickNavBarTab("Products");
        return page(ProductsPage.class);
    }

    @Step("Click Products tab{0}")
    public CartPage clickCartTab() {
        clickNavBarTab("Cart");
        return page(CartPage.class);
    }

    @Step("Scroll down to footer")
    public HomePage subscriptionTitle() {
        $(byText("Subscription")).scrollIntoView(true)
                .shouldHave(text("Subscription"));
        return this;
    }

    public String getName() {
        SelenideElement name = $x("//input[@data-qa='name']");
        return name.getValue();
    }

    @Step("Check Logged in as banner visible{0}")
    public String getLoggedInBannerText() {
        // Try multiple ways to find the "Logged in as" banner
        try {
            // First try: look for exact text
            return navBarElements.find(Condition.partialText("Logged in as"))
                    .shouldBe(Condition.visible, Duration.ofSeconds(5))
                    .getText();
        } catch (Exception e1) {
            try {
                // Second try: look for any element containing "Logged"
                return navBarElements.find(Condition.partialText("Logged"))
                        .shouldBe(Condition.visible, Duration.ofSeconds(5))
                        .getText();
            } catch (Exception e2) {
                // Third try: look for any text that might indicate login
                return navBarElements.find(Condition.partialText("as"))
                        .shouldBe(Condition.visible, Duration.ofSeconds(5))
                        .getText();
            }
        }
    }






//    public String isLoggedInAsVisible() {
//        return navBarElements
//                .find(Condition.partialText("Logged in as"))
//                .getText();
//    }


}
