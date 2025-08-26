package com.selenide.layers.web.page.signup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.selenide.enums.ElementsOnPage;
import com.selenide.enums.ElementsQa;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.accountCreate.AccountCreatedPage;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Step;
import net.datafaker.Faker;
import org.openqa.selenium.By;

import java.time.YearMonth;
import java.util.concurrent.ThreadLocalRandom;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class SignUpPage extends BasePage<SignUpPage> {
    Faker faker = new Faker();


    @Override
    public SignUpPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Signup / Login"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Override
    protected ElementsCollection getTitles() {
        return titlesInAllPages;
    }

    @Step("Enter SignUp Name: {name}")
    public SignUpPage inputSignUpName(String name) {
        elementManager.inputOnlyElement(ElementsQa.SIGNUP_NAME.getElement().setValue(name));
        return this;
    }

    @Step("Enter SignUp Email: {email}")
    public SignUpPage inputSignUpEmail(String email) {
        elementManager.inputOnlyElement(ElementsQa.SIGNUP_EMAIL.getElement().setValue(email));
        return this;
    }

    @Step("Enter login email{email}")
    public SignUpPage inputLoginEmail(String email) {
        elementManager.inputOnlyElement(ElementsQa.LOGIN_EMAIL.getElement().setValue(email));
        return this;
    }

    @Step("Enter login password {password}")
    public SignUpPage inputLoginPassword(String password) {
        elementManager.inputOnlyElement(ElementsQa.LOGIN_PASSWORD.getElement().setValue(password));
        return this;
    }

    @Step("Click signup button{0}")
    public SignUpPage clickSignUpButton() {
        elementManager.click(ElementsQa.SIGNUP_BUTTON.getElement());
        return this;
    }

    @Step("Click login button{0}")
    public HomePage clickLoginButton() {
        elementManager.click(ElementsQa.LOGIN_BUTTON.getElement());
        return Selenide.page(HomePage.class);
    }

    @Step("Check Enter Account Information visible{0}")
    public boolean isEnterAccountInformationVisible() {
        return $(byText("Enter Account Information"))
                .is(visible);
    }

    @Step("Choose title{0}")
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

    @Step("Enter password{0}")
    public SignUpPage inputPassword() {
        var fakePassword = faker.internet().password();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .PASSWORD.getElementFromAccount()
                        .setValue(fakePassword));
        return this;
    }

    @Step("Enter date of birth{0}")
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

    @Step("Choose checkBoxes{0}")
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

    @Step("Enter user first name{0}")
    public SignUpPage inputUserFirstName() {
        var fakeFirstName = faker.name().firstName();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .FIRST_NAME.getElementFromAccount()
                        .setValue(fakeFirstName));
        return this;
    }

    @Step("Enter user last name{0}")
    public SignUpPage inputUserLastName() {
        var fakeLastName = faker.name().lastName();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .LAST_NAME.getElementFromAccount()
                        .setValue(fakeLastName));
        return this;
    }

    @Step("Enter company name{0}")
    public SignUpPage inputCompanyName() {
        var fakeCompany = faker.company().name();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .COMPANY.getElementFromAccount()
                        .setValue(fakeCompany));
        return this;
    }

    @Step("Enter first address{0}")
    public SignUpPage inputUserAddress1() {
        var fakeFirstAddress = faker.address().fullAddress();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .FIRST_ADDRESS.getElementFromAccount()
                        .setValue(fakeFirstAddress));
        return this;
    }

    @Step("Enter second address{0}")
    public SignUpPage inputUserAddress2() {
        var fakeSecondAddress = faker.address().secondaryAddress();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .SECOND_ADDRESS.getElementFromAccount()
                        .setValue(fakeSecondAddress));
        return this;
    }

    @Step("Enter user country{0}")
    public SignUpPage inputUserCountry() {
        elementManager
                .select(ElementsOnPage
                        .COUNTRY.getElementFromAccount(), "United States");
        return this;
    }

    @Step("Enter user state{0}")
    public SignUpPage inputUserState() {
        var fakeState = faker.address().state();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .STATE.getElementFromAccount()
                        .setValue(fakeState));
        return this;
    }

    @Step("Enter user city{0}")
    public SignUpPage inputUserCity() {
        var fakeCity = faker.address().city();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .CITY.getElementFromAccount()
                        .setValue(fakeCity));
        return this;
    }

    @Step("Enter user zipcode{0}")
    public SignUpPage inputUserZipCode() {
        var fakeZipCode = faker.address().zipCode();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .ZIP_CODE.getElementFromAccount().setValue(fakeZipCode));
        return this;
    }

    @Step("Enter user phone number{0}")
    public SignUpPage inputUserMobilePhone() {
        var fakePhone = faker.phoneNumber().phoneNumber();
        elementManager
                .inputOnlyElement(ElementsOnPage
                        .PHONE.getElementFromAccount()
                        .setValue(fakePhone));
        return this;
    }

    public boolean isIncorrectMessageVisible() {
        return $(byText("Your email or password is incorrect!")).shouldBe(visible).exists();
    }

    @Step("error '{0}' is visible")
    public boolean isEmailExistingMessageVisible() {
        return $(byText("Email Address already exist!")).shouldBe(visible).exists();
    }

    @Step("Click create account button{0}")
    public AccountCreatedPage clickCreateAccountButton() {
        elementManager.click(ElementsQa.CREATE_ACCOUNT_BUTTON.getElement());
        return Selenide.page(AccountCreatedPage.class);
    }
}
