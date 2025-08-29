package test.AutoExerciseTests;

import base.BaseTest;
import com.codeborne.selenide.Condition;
import com.selenide.data.UserData;
import com.selenide.layers.web.page.home.HomePage;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)
@Tag(Tags.REGRESSION)
@Tag(Tags.WEB)

public class TestCase14 extends BaseTest {

    Faker faker = new Faker();
    UserData user = new UserData();

    @Test
    @Order(14)
    @Owner("Eldiyar")
    @DisplayName("Place Order: Register while Checkout")
    @Severity(SeverityLevel.BLOCKER)
    void placeOrderRegisterWhileCheckoutTest() {

        String city = faker.address().city();
        String state = faker.address().state();

        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setCompanyName(faker.company().name());
        user.setAddress1(faker.address().streetAddress());
        user.setAddress2(faker.address().secondaryAddress());
        user.setCountry("United States");
        user.setState(state);
        user.setCity(city);
        user.setCityState(city + " " + state);
        user.setZipCode(faker.address().zipCode());
        user.setPhone(faker.phoneNumber().cellPhone());

        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();
        });

        var cartpage = home
                .clickAddToCart("Blue Top")
                .clickContinue()
                .clickCartTab();

        step("Verify that cart page is displayed", () -> {
            softAssert.assertThat(home.isPageTabActive("Cart"))
                    .as("Cart page is displayed")
                    .isTrue();
        });

        var login = cartpage
                .clickProceedToCheckout()
                .clickRegisterLogin();

        var signup = login
                .inputSignUpName("1")
                .inputSignUpEmail("22@22.15п")
                .clickSignUpButton();

        step("Verify that 'Enter Account Information' is visible", () -> {
            softAssert.assertThat(signup.titlesInAllPages("Enter Account Information"))
                    .as("Enter Account Information is visible")
                    .isEqualToIgnoringCase("Enter Account Information");
        });

        var accountCreated = signup.waitForPageLoaded()
                .chooseTitle("Mr").inputPassword("123")
                .markCheckBoxes(true, true)
                .selectRandomDateMonthYear()
                .inputUserFirstName(user).inputUserLastName(user)
                .inputCompanyName(user).inputUserAddress1(user)
                .inputUserAddress2(user).inputUserCountry()
                .inputUserState(user).inputUserCity(user)
                .inputUserZipCode(user).inputUserMobilePhone(user)
                .clickCreateAccountButton().waitForPageLoaded();

        step("Verify that 'Account Created!' is visible", () -> {
            softAssert.assertThat(accountCreated.titlesInAllPages("Account Created!"))
                    .as("Account Created! is visible")
                    .isEqualToIgnoringCase("Account Created!");
        });

        var homeAfterContinue = accountCreated.clickContinueButton().waitForPageLoaded();

        String banner = homeAfterContinue.getLoggedInBannerText();
        step("Verify that 'Logged in as username' is visible", () -> {
            softAssert.assertThat(banner)
                    .isEqualToIgnoringCase("Logged in as 1");
        });

        var cartPageAfterLogin = homeAfterContinue
                .clickCartTab()
                .clickProceedToCheckout();

        UserData actual = cartPageAfterLogin.getDeliveryAddress();

        step("Verify Address Details and Review Your Order", () -> {
            softAssert.assertThat(actual.getFirstName()).isEqualTo(user.getFirstName());
            softAssert.assertThat(actual.getLastName()).isEqualTo(user.getLastName());
            softAssert.assertThat(actual.getCompanyName()).isEqualTo(user.getCompanyName());
            softAssert.assertThat(actual.getAddress1()).isEqualTo(user.getAddress1());
            softAssert.assertThat(actual.getAddress2()).isEqualTo(user.getAddress2());
            softAssert.assertThat(actual.getCountry()).isEqualTo(user.getCountry());
            softAssert.assertThat(actual.getCityState()).isEqualTo(user.getCityState());
            softAssert.assertThat(actual.getZipCode()).isEqualTo(user.getZipCode());
            softAssert.assertThat(actual.getPhone()).isEqualTo(user.getPhone());

            var cartItems = cartPageAfterLogin.getCartItems();
            softAssert.assertThat(cartItems)
                    .as("Cart has at least 1 item")
                    .isNotEmpty();

            var blueTop = cartItems.stream()
                    .filter(p -> p.getName().equalsIgnoreCase("Blue Top"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Blue Top not found in Cart"));

            softAssert.assertThat(blueTop.getQuantity())
                    .as("Quantity of Blue Top")
                    .isEqualTo(1);

            softAssert.assertThat(blueTop.getPrice())
                    .as("Unit price of Blue Top")
                    .isEqualTo("Rs. 500");

            softAssert.assertThat(blueTop.getTotal())
                    .as("Total price of Blue Top")
                    .isEqualTo("Rs. 500");

        });

        var payment = cartPageAfterLogin
                .enterDescriptionInComment("All good")
                .clickToPlaceOrder();

        var deleteAccount = payment
                .waitForPageLoaded()
                .inputCardInformation()
                .clickPayAndConfirmButton();

        softAssert.assertThat("Your order has been placed successfully!")
                .as("Success message should be visible")
                .isEqualTo("Your order has been placed successfully!");

        deleteAccount.clickDeletedAccountTab();

        step("Verify that 'Account Deleted!' is visible", () -> {
            softAssert.assertThat(deleteAccount.titlesInAllPages("Account Deleted!"))
                    .as("'Account Deleted!' is visible")
                    .isEqualToIgnoringCase("Account Deleted!");
        });
        deleteAccount.clickContinueButton();

        softAssert.assertAll();
    }
}
