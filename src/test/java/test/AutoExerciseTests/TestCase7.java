package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.*;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import test.utils.TestRailCaseId;
import test.utils.TestRailExtension;

import static io.qameta.allure.Allure.step;

@ExtendWith(TestRailExtension.class)
public class TestCase7 extends BaseTest {

    @Test
    @TestRailCaseId("C1")
    @Order(7)
    @Owner("Eldiyar")
    @DisplayName("Test Case 7: Verify Test Cases Page")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click on 'Test Cases' button
            5. Verify user is navigated to test cases page successfully""")
    void verifyTestCasesPage() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var testCase = home
                    .clickTestCasesTab()
                    .waitForPageLoaded();

            step("Verify user is navigated to test cases page successfully", () -> {
                softAssert.assertThat(testCase.isPageTabActive("Test Cases"))
                        .as("User is navigated to test cases page successfully")
                        .isTrue();
            });
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

