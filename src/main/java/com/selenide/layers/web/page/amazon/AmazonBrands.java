package com.selenide.layers.web.page.amazon;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.selenide.layers.web.page.BasePage;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.ENTER;

public class AmazonBrands extends BasePage<AmazonBrands> {

    public AmazonBrands waitForPageLoaded() {
        $$x("//div[contains(@class, 'a-cardui-header')]").shouldBe(sizeGreaterThan(1));
        return this;
    }

    @Override
    protected ElementsCollection getTitles() {
        return null;
    }


    public AmazonBrands search(String value) {
        elementManager.inputElementWithText($("#twotabsearchtextbox"), value + ENTER);
        return this;
    }

    private final ElementsCollection brandsList = $$x("//div[@id='brandsRefinements']//li//a//span[contains(@class, 'a-size-base')]");
    private final ElementsCollection conditionList = $$x("//ul[@id='filter-p_n_condition-type']//li//a//span[contains(@class,'a-size-base')]");


    public List<String> getAllBrands() {
        $x("//span[text()='Brands']").shouldBe(visible);
        var result = brandsList.stream().map(SelenideElement::getText).toList();
        return result;
    }

    public List<String> getAllConditions() {
        $x("//span[text()='Condition']").shouldBe(visible);
        var resultCondition = conditionList.stream().map(SelenideElement::getText).toList();
        return resultCondition;
    }


}
