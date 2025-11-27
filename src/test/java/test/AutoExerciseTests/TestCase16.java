package test.AutoExerciseTests;

import base.BaseTest;
import base.TestFlags;
import com.selenide.data.UserData;
import com.selenide.layers.web.page.home.HomePage;
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

import static io.qameta.allure.Allure.step;

@Tag(Tags.SMOKE)

public class TestCase16 extends BaseTest {

    @Test
    @Order(16)
    @Owner("Eldiyar")
    @DisplayName("Test Case 16: Place Order: Login before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            1. Launch browser
            2. Navigate to url 'http://automationexercise.com'
            3. Verify that home page is visible successfully
            4. Click 'Signup / Login' button
            5. Fill email, password and click 'Login' button
            6. Verify 'Logged in as username' at top
            7. Add products to cart
            8. Click 'Cart' button
            9. Verify that cart page is displayed
            10. Click Proceed To Checkout
            11. Verify Address Details and Review Your Order
            12. Enter description in comment text area and click 'Place Order'
            13. Enter payment details: Name on Card, Card Number, CVC, Expiration date
            14. Click 'Pay and Confirm Order' button
            15. Verify success message 'Your order has been placed successfully!'
            16. Click 'Delete Account' button
            17. Verify 'ACCOUNT DELETED!' and click 'Continue' button""")
    void placeOrderLoginBeforeCheckout() {
        var softAssert = new SoftAssertions();

        var home = open(HomePage.class)
                .waitForPageLoaded();

        step("Verify that home page is visible successfully", () -> {
            softAssert.assertThat(home.isPageTabActive("Home"))
                    .as("Home page is loaded and visible successfully")
                    .isTrue();

            var login = home
                    .clickSignUpLoginTab()
                    .waitForPageLoaded();

            step("Verify 'Login to your account' is visible", () -> {
                softAssert.assertThat(login.titlesInAllPages("Login to your account"))
                        .as("'Login to your account' is visible")
                        .isEqualToIgnoringCase("Login to your account");

                var afterLogin = login
                        .inputLoginEmail(System.getProperty("LOGIN_EMAIL", "qa+tc16@ex.com"))
                        .inputLoginPassword(System.getProperty("LOGIN_PASSWORD", "1"))
                        .clickLoginButton();

                var homeAfterContinue = afterLogin;

                String banner = homeAfterContinue.getLoggedInBannerText();
                step("Verify that 'Logged in as username' is visible", () -> {
                    // Check that banner contains "Logged in as" followed by a username
                    softAssert.assertThat(banner)
                            .as("Should contain 'Logged in as' text. Found: '" + banner + "'")
                            .containsIgnoringCase("Logged in as");
                    
                    // Check that there's text after "Logged in as" (the username)
                    String[] parts = banner.split("(?i)logged in as");
                    softAssert.assertThat(parts.length)
                            .as("Should have text after 'Logged in as'. Banner: '" + banner + "'")
                            .isGreaterThan(1);
                    
                    String username = parts[1].trim();
                    softAssert.assertThat(username)
                            .as("Username should not be empty. Found: '" + username + "'")
                            .isNotEmpty();
                });

                var cartActions = homeAfterContinue
                        .clickAddToCart("Blue Top")
                        .clickContinue()
                        .clickCartTab();

                step("Verify that cart page is displayed", () -> {
                    softAssert.assertThat(cartActions.isPageTabActive("Cart"))
                            .as("Cart page is displayed")
                            .isTrue();

                    var cartPage = cartActions
                            .clickProceedToCheckout();

                    UserData actual = cartPage.getDeliveryAddress();

                    step("Verify Address Details and Review Your Order", () -> {
                        softAssert.assertThat(actual.getFirstName()).isNotEmpty();
                        softAssert.assertThat(actual.getLastName()).isNotEmpty();
                        softAssert.assertThat(actual.getCompanyName()).isNotEmpty();
                        softAssert.assertThat(actual.getAddress1()).isNotEmpty();
                        softAssert.assertThat(actual.getAddress2()).isNotEmpty();
                        softAssert.assertThat(actual.getCountry()).isNotEmpty();
                        softAssert.assertThat(actual.getCityState()).isNotEmpty();
                        softAssert.assertThat(actual.getZipCode()).isNotEmpty();
                        softAssert.assertThat(actual.getPhone()).isNotEmpty();

                        var cartItems = cartPage.getCartItems();
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

                        var payment = cartPage
                                .enterDescriptionInComment("All good")
                                .clickToPlaceOrder();

                        var deleteAccount = payment
                                .waitForPageLoaded()
                                .inputCardInformation()
                                .clickPayAndConfirmButton();

                        String successMessage = payment.getSuccessAlertText();
                        softAssert.assertThat(successMessage)
                                .as("Success message should be visible")
                                .isEqualTo("Your order has been placed successfully!");

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
        softAssert.assertAll();
    }
}
