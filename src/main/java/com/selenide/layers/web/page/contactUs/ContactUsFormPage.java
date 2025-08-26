package com.selenide.layers.web.page.contactUs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.selenide.enums.ElementsQa;
import com.selenide.layers.web.page.BasePage;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Step;
import net.datafaker.Faker;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ContactUsFormPage extends BasePage<ContactUsFormPage> {
    Faker faker = new Faker();

    @Override
    public ContactUsFormPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Contact us"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Override
    protected ElementsCollection getTitles() {
        return null;
    }

    @Step("Check Contact Us tab is active{0}")
    public boolean isContactUsTabActive() {
        var homeTab = navBarElements.find(Condition.partialText("Contact us"))
                .find(By.tagName("a"));
        return homeTab.has(Condition.attribute("style", "color: orange;"));
    }

    @Step("Check New user signup visible{0}")
    public boolean isGetInTouchTitleVisible() {
        return $x("//h2[text()='Get In Touch']")
                .shouldBe(Condition.visible).exists();
    }

    @Step("Enter Name: {name}")
    public ContactUsFormPage inputName() {
        var fakeName = faker.name().name();
        elementManager.inputOnlyElement(ElementsQa.NAME.getElement().setValue(fakeName));
        return this;
    }

    @Step("Enter Email: {email}")
    public ContactUsFormPage inputEmail() {
        var fakeEmail = faker.internet().emailAddress();
        elementManager.inputOnlyElement(ElementsQa.EMAIL.getElement().setValue(fakeEmail));
        return this;
    }

    @Step("Enter Email: {email}")
    public ContactUsFormPage inputSubject() {
        var fakeSubject = faker.lorem().sentence();
        elementManager.inputOnlyElement(ElementsQa.SUBJECT.getElement().setValue(fakeSubject));
        return this;
    }

    @Step("Enter message: {message}")
    public ContactUsFormPage inputMessage() {
        var fakeMessage = faker.lorem().sentence();
        elementManager.inputOnlyElement(ElementsQa.MESSAGE.getElement().setValue(fakeMessage));
        return this;
    }

    @Step("Upload file from PC{0}")
    public ContactUsFormPage uploadFile() {
        $x("//input[@name='upload_file']")
                .uploadFile(new File("C://Users//PC-User//Pictures//i.webp"));
        return this;
    }

    @Step("Click submit button{0}")
    public ContactUsFormPage clickSubmitButton() {
        elementManager.click(ElementsQa.SUBMIT_BUTTON.getElement());
        return this;
    }

    @Step("Click to accept alert OK{0}")
    public ContactUsFormPage acceptAlert() {
        Selenide.switchTo().alert().accept();
        return this;
    }

    @Step("Check title: Success! Your details have been submitted successfully.")
    public boolean isSuccessMessageVisible() {
        return $(byText("Success! Your details have been submitted successfully."))
                .shouldBe(Condition.visible)
                .exists();
    }

    public boolean isHomeButtonVisible() {
        return $x("//a[@class='btn btn-success']//span[contains(text(),'Home')]")
                .shouldBe(Condition.visible).exists();
    }


    public HomePage clickHomeButton() {
        elementManager.click($x("//a[@class='btn btn-success']//span[contains(text(),'Home')]"));
        return Selenide.page(HomePage.class);
    }

}
