package com.selenide.layers.web.page.testCases;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.selenide.enums.CommonAttributes;
import com.selenide.layers.web.page.BasePage;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class TestCasePage extends BasePage<TestCasePage> {


    @Override
    public TestCasePage waitForPageLoaded() {
        var pageTab = navBarElements.find(Condition.partialText("Test Cases"))
                .find(By.tagName("a"));
        pageTab.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Override
    protected ElementsCollection getTitles() {
        return titlesInAllPages;
    }

    public String getLinkValue(String title) {
        var testCases = listOfTestCases
                .find(Condition.partialText(title));
        var aElement = testCases
                .find(By.tagName("a"));
        return aElement.getAttribute("href");
    }

    public TestCasePage clickToLink(String title) {
        var testCases = listOfTestCases
                .find(Condition.partialText(title));
        var aElement = testCases
                .find(By.tagName("a"));
        elementManager.click(aElement);
        return this;
    }

    public void verifyCaseIsOpened(String title) {
        var testCases = listOfTestCases
                .find(Condition.partialText(title));
        var aElement = testCases
                .find(By.tagName("a"));
        elementManager.shouldNotHaveAttribute(aElement, CommonAttributes.CLASS, "collapsed");
    }
}



