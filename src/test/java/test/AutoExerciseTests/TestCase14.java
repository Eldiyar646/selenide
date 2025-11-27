package test.AutoExerciseTests;

import base.BaseTest;
import com.selenide.data.UserData;
import com.selenide.layers.web.page.home.HomePage;
import base.TestFlags;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.Tags;

import static com.selenide.data.UserData.faker;
import static io.qameta.allure.Allure.step;


@Tag(Tags.SMOKE)

public class TestCase14 extends BaseTest {
    UserData user = new UserData();

    @Test
    @Order(14)
    @Owner("Eldiyar")
    @DisplayName("Test Case 14: Place Order: Register while Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Add products to cart
            5. Click 'Cart' button
            6. Verify that cart page is displayed
            7. Click Proceed To Checkout
            8. Click 'Register / Login' button
            9. Fill all details in Signup and create account
            10. Verify 'ACCOUNT CREATED!' and click 'Continue' button
            11. Verify ' Logged in as username' at top
            12.Click 'Cart' button
            13. Click 'Proceed To Checkout' button
            14. Verify Address Details and Review Your Order
            15. Enter description in comment text area and click 'Place Order'
            16. Enter payment details: Name on Card, Card Number, CVC, Expiration date
            17. Click 'Pay and Confirm Order' button
            18. Verify success message 'Your order has been placed successfully!'
            19. Click 'Delete Account' button
            20. Verify 'ACCOUNT DELETED!' and click 'Continue' button""")
    void placeOrderRegisterWhileCheckout() {
        var softAssert = new SoftAssertions();

        String generatedName = faker
                .name().firstName();
        String generatedEmail = faker
                .internet().emailAddress();

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

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var cartpage = home
                    .clickAddToCart("Blue Top")
                    .clickContinue()
                    .clickCartTab();

            step("Verify that cart page is displayed", () -> {
                softAssert.assertThat(home.isPageTabActive("Cart"))
                        .as("Cart page is displayed")
                        .isTrue();

                var login = cartpage
                        .clickProceedToCheckout()
                        .clickRegisterLogin();

                var signup = login
                        .inputSignUpName(generatedName)
                        .inputSignUpEmail(generatedEmail)
                        .clickSignUpButton();

                step("Verify that 'Enter Account Information' is visible", () -> {
                    softAssert.assertThat(signup.titlesInAllPages("Enter Account Information"))
                            .as("Enter Account Information is visible")
                            .isEqualToIgnoringCase("Enter Account Information");

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

                        var homeAfterContinue = accountCreated.clickContinueButton().waitForPageLoaded();

                        String banner = homeAfterContinue.getLoggedInBannerText();
                        step("Verify that 'Logged in as username' is visible", () -> {
                            softAssert.assertThat(banner)
                                    .as("'Logged in as <username>' is visible")
                                    .containsIgnoringCase("Logged in as " + generatedName);

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

                                var payment = cartPageAfterLogin
                                        .enterDescriptionInComment("All good")
                                        .clickToPlaceOrder();

                                var deleteAccount = payment
                                        .waitForPageLoaded()
                                        .inputCardInformation()
                                        .clickPayAndConfirmButton();

                                step("Verify that success message is present", () -> {
                                    String successText = payment
                                            .waitForSuccessAlert()
                                            .getSuccessAlertText();

                                    softAssert.assertThat(successText)
                                            .as("Success message should be present")
                                            .contains("Your order has been placed successfully!");

                                    if (!TestFlags.shouldKeepAccount()) {
                                        deleteAccount.clickDeletedAccountTab();

                                        step("Verify that 'Account Deleted!' is visible", () -> {
                                            softAssert.assertThat(deleteAccount.titlesInAllPages("Account Deleted!"))
                                                    .as("'Account Deleted!' is visible")
                                                    .isEqualToIgnoringCase("Account Deleted!");

                                            deleteAccount.clickContinueButton();
                                        });
                                    }
                                });
                            });
                        });
                    });
                });
            });
        });
        softAssert.assertAll();
    }
}
