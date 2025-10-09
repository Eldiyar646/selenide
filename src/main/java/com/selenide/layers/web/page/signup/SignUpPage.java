package com.selenide.layers.web.page.signup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.selenide.data.UserData;
import com.selenide.enums.ElementsOnPage;
import com.selenide.enums.ElementsQa;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.createDeleteAccount.AccountCreatedPage;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Step;
import net.datafaker.Faker;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.YearMonth;
import java.util.concurrent.ThreadLocalRandom;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class SignUpPage extends BasePage<SignUpPage> {

    @Override
    public SignUpPage waitForPageLoaded() {
            var pageTab = navBarElements.find(Condition.partialText("Signup / Login"))
                    .find(By.tagName("a"));
            pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
            return this;
    }

    @Step("Enter SignUp name")
    public SignUpPage inputSignUpName(String name) {
        elementManager.inputOnlyElement(ElementsQa.SIGNUP_NAME.getElement().setValue(name));
        return this;
    }

    @Step("Enter SignUp email")
    public SignUpPage inputSignUpEmail(String email) {
        elementManager.inputOnlyElement(ElementsQa.SIGNUP_EMAIL.getElement().setValue(email));
        return this;
    }

    @Step("Click signup button")
    public SignUpPage clickSignUpButton() {
        elementManager.click(ElementsQa.SIGNUP_BUTTON.getElement());
        // Wait for the account information section to appear after clicking signup
        $x("//h2[contains(.,'Enter Account Information')]")
                .shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    @Step("Enter login email")
    public SignUpPage inputLoginEmail(String email) {
        elementManager.inputOnlyElement(ElementsQa.LOGIN_EMAIL.getElement().setValue(email));
        return this;
    }

    @Step("Enter login password")
    public SignUpPage inputLoginPassword(String password) {
        elementManager.inputOnlyElement(ElementsQa.LOGIN_PASSWORD.getElement().setValue(password));
        return this;
    }

    @Step("Click login button")
    public HomePage clickLoginButton() {
        elementManager.click(ElementsQa.LOGIN_BUTTON.getElement());
        return Selenide.page(HomePage.class);
    }

    @Step("Choose title")
    public SignUpPage chooseTitle(String title) {
        switch (title.toLowerCase()) {
            case "mr":
                elementManager.click(ElementsOnPage.TITLE_MR.getElementById());
                break;
            case "mrs":
                elementManager.click(ElementsOnPage.TITLE_MRS.getElementById());
                break;
            default:
                throw new IllegalArgumentException("Unknown title: " + title);
        }
        return this;
    }

    @Step("Enter password")
    public SignUpPage inputPassword(String password) {
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .PASSWORD.getElementFromAccount().setValue(password));
        return this;
    }

    @Step("Enter date of birth")
    public SignUpPage selectRandomDateMonthYear() {
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        int year = ThreadLocalRandom.current().nextInt(1970, 2021);
        int maxDaysInMonth = YearMonth.of(year, month).lengthOfMonth();
        int day = ThreadLocalRandom.current().nextInt(1, maxDaysInMonth + 1);

        $("#days").selectOptionByValue(String.valueOf(day));
        $("#months").selectOptionByValue(String.valueOf(month));
        $("#years").selectOptionByValue(String.valueOf(year));
        return this;
    }

    @Step("Choose checkBoxes")
    public SignUpPage markCheckBoxes(boolean newsLetter, boolean specialOffers) {
        if (newsLetter) {
            SelenideElement newsLetterCheckbox = $("#newsletter");
            if (!newsLetterCheckbox.isSelected()) {
                elementManager.click(newsLetterCheckbox);
            }
        }
        if (specialOffers) {
            SelenideElement specialOffersCheckbox = $("#optin");
            if (!specialOffersCheckbox.isSelected()) {
                elementManager.click(specialOffersCheckbox);
            }
        }
        return this;
    }

    @Step("Enter user first name")
    public SignUpPage inputUserFirstName(UserData user) {
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .FIRST_NAME.getElementFromAccount()
                        .setValue(user.getFirstName()));
        return this;
    }

    @Step("Enter user last name")
    public SignUpPage inputUserLastName(UserData user) {
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .LAST_NAME.getElementFromAccount()
                        .setValue(user.getLastName()));
        return this;
    }

    @Step("Enter company name")
    public SignUpPage inputCompanyName(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .COMPANY.getElementFromAccount()
                        .setValue(user.getCompanyName()));
        return this;
    }

    @Step("Enter first address")
    public SignUpPage inputUserAddress1(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .FIRST_ADDRESS.getElementFromAccount()
                        .setValue(user.getAddress1()));
        return this;
    }

    @Step("Enter second address")
    public SignUpPage inputUserAddress2(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .SECOND_ADDRESS.getElementFromAccount()
                        .setValue(user.getAddress2()));
        return this;
    }

    @Step("Enter user country")
    public SignUpPage inputUserCountry() {
        elementManager.select(ElementsOnPage
                        .COUNTRY.getElementFromAccount(), "United States");
        return this;
    }

    @Step("Enter user state")
    public SignUpPage inputUserState(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .STATE.getElementFromAccount()
                        .setValue(user.getState()));
        return this;
    }

    @Step("Enter user city")
    public SignUpPage inputUserCity(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .CITY.getElementFromAccount()
                        .setValue(user.getCity()));
        return this;
    }

    @Step("Enter user zipcode")
    public SignUpPage inputUserZipCode(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .ZIP_CODE.getElementFromAccount().setValue(user.getZipCode()));
        return this;
    }

    @Step("Enter user phone number")
    public SignUpPage inputUserMobilePhone(UserData user) {
        elementManager.inputOnlyElement(ElementsOnPage
                        .PHONE.getElementFromAccount()
                        .setValue(user.getPhone()));
        return this;
    }

    @Step("Click create account button")
    public AccountCreatedPage clickCreateAccountButton() {
        elementManager.click(ElementsQa.CREATE_ACCOUNT_BUTTON.getElement());
        return Selenide.page(AccountCreatedPage.class);
    }




}
