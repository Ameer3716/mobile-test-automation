package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import com.automation.utils.AppiumUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Login functionality (TC01 - TC04).
 * Tests login screen display, valid login, invalid login, and empty credentials.
 * Each test is independent and uses proper TestNG assertions.
 */
public class LoginTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        homePage  = new HomePage(driver);
        // Navigate to login via menu (app opens on catalog by default)
        homePage.navigateToLogin();
    }

    /**
     * TC01 - Verify login page elements are displayed.
     * Validates that username field and login button are visible.
     */
    @Test(priority = 1, description = "TC01: Verify login page UI elements are displayed")
    public void tc01_verifyLoginPageDisplayed() {
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),
                "Username field should be visible on login page");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button should be visible on login page");
    }

    /**
     * TC02 - Verify successful login with valid credentials.
     * Uses standard SauceLabs demo credentials: bob@example.com / 10203040
     */
    @Test(priority = 2, description = "TC02: Verify successful login with valid credentials")
    public void tc02_successfulLogin() {
        String validUser = AppiumUtils.getProperty("valid.username");
        String validPass = AppiumUtils.getProperty("valid.password");

        loginPage.login(validUser, validPass);

        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Home/Products page should appear after successful login");
        Assert.assertEquals(homePage.getHomeTitleText(), "Products",
                "Home page title should be 'Products'");
    }

    /**
     * TC03 - Verify error message with invalid credentials.
     * Tests that incorrect password shows an error message.
     */
    @Test(priority = 3, description = "TC03: Verify error message for invalid password")
    public void tc03_invalidPasswordShowsError() {
        String validUser = AppiumUtils.getProperty("valid.username");
        String invalidPass = AppiumUtils.getProperty("invalid.password");

        loginPage.login(validUser, invalidPass);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for incorrect password");
        String errorText = loginPage.getErrorMessageText();
        Assert.assertNotNull(errorText, "Error message text should not be null");
        Assert.assertFalse(errorText.isEmpty(), "Error message text should not be empty");
    }

    /**
     * TC04 - Verify validation error when credentials are empty.
     * Tests that tapping login with no input shows required field errors.
     */
    @Test(priority = 4, description = "TC04: Verify error when submitting empty credentials")
    public void tc04_emptyCredentialsShowsError() {
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isUsernameRequiredErrorDisplayed(),
                "Username required error should appear for empty username");
    }
}
