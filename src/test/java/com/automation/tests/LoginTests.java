package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        homePage  = new HomePage(driver);
    }

    // TC01 - Verify login page is displayed on app launch
    @Test(description = "Verify login page elements are displayed on app launch")
    public void tc01_verifyLoginPageDisplayed() {
        boolean isLoginButtonVisible = loginPage.isLoginButtonDisplayed();
        Assert.assertTrue(isLoginButtonVisible, "Login button should be visible on launch");
    }

    // TC02 - Verify successful login with valid credentials
    @Test(description = "Verify user can login with valid credentials")
    public void tc02_successfulLogin() {
        loginPage.login("validuser@test.com", "ValidPass123");
        boolean isHomeDisplayed = homePage.isHomePageDisplayed();
        Assert.assertTrue(isHomeDisplayed, "Home page should appear after successful login");
    }

    // TC03 - Verify error message with wrong password
    @Test(description = "Verify error message shown for incorrect password")
    public void tc03_invalidPasswordShowsError() {
        loginPage.login("validuser@test.com", "WrongPassword");
        boolean isErrorShown = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorShown, "Error message should be shown for wrong password");
    }

    // TC04 - Verify error message with empty credentials
    @Test(description = "Verify error message shown when fields are empty")
    public void tc04_emptyCredentialsShowsError() {
        loginPage.clickLoginButton();
        boolean isErrorShown = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorShown, "Error message should appear for empty credentials");
    }
}
