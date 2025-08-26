package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)

public class TestCase7 extends BaseTest {

    @Test
    @Order(7)
    @Owner("Eldiyar")
    @DisplayName("Verify Test Cases Page")
    @Severity(SeverityLevel.BLOCKER)
    void verifyTestCasesTest() {

        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        var testCase = home
                .clickTestCasesTab()
                .waitForPageLoaded();

        step("Verify user is navigated to test cases page successfully", () -> {
            softAssert.assertThat(testCase.isPageTabActive("Test Cases"))
                    .as("User is navigated to test cases page successfully")
                    .isTrue();
        });

        softAssert.assertAll();
    }
}


//        var linkVal = testCase
//                .getLinkValue("Test Case 5: Register User with existing email");
//
//        softAssert.assertThat(linkVal)
//                .as("Verify link value")
//                .isEqualTo("https://www.automationexercise.com/test_cases#collapse5");
//
//        softAssert.assertAll();
//
//        testCase.clickToLink("Test Case 5: Register User with existing email")
//                .verifyCaseIsOpened("Test Case 5: Register User with existing email");


//        softAssert.assertThat(testCase).as("Case is opened").
//

