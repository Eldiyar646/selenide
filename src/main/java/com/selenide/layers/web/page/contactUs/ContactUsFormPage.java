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

import static com.codeborne.selenide.Selenide.*;

public class ContactUsFormPage extends BasePage<ContactUsFormPage> {
    Faker faker = new Faker();
    File file = new File("src/test/resources/i.webp");

    @Override
    public ContactUsFormPage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Contact us"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("Enter name")
    public ContactUsFormPage inputName() {
        var fakeName = faker.name().name();
        elementManager.inputOnlyElement(ElementsQa.NAME.getElement().setValue(fakeName));
        return this;
    }

    @Step("Enter email")
    public ContactUsFormPage inputEmail() {
        var fakeEmail = faker.internet().emailAddress();
        elementManager.inputOnlyElement(ElementsQa.EMAIL.getElement().setValue(fakeEmail));
        return this;
    }

    @Step("Enter subject")
    public ContactUsFormPage inputSubject() {
        var fakeSubject = faker.lorem().sentence();
        elementManager.inputOnlyElement(ElementsQa.SUBJECT.getElement().setValue(fakeSubject));
        return this;
    }

    @Step("Enter message")
    public ContactUsFormPage inputMessage() {
        var fakeMessage = faker.lorem().sentence();
        elementManager.inputOnlyElement(ElementsQa.MESSAGE.getElement().setValue(fakeMessage));
        return this;
    }

    @Step("Upload file from PC")
    public ContactUsFormPage uploadFile() {
        $x("//input[@name='upload_file']")
                .uploadFile(file);
        return this;
    }

    @Step("Click submit button")
    public ContactUsFormPage clickSubmitButton() {
        elementManager.click(ElementsQa.SUBMIT_BUTTON.getElement());
        return this;
    }

    @Step("Click to accept alert OK")
    public void acceptAlert() {
        Selenide.switchTo().alert().accept();
    }

    @Step("Click to continue home button")
    public HomePage clickHomeButton() {
        elementManager.click($x("//a[@class='btn btn-success']//span[contains(text(),'Home')]"));
        return page(HomePage.class);
    }



}
