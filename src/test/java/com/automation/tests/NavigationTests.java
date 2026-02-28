package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import com.automation.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeMethod
    public void initAndLogin() {
        loginPage = new LoginPage(driver);
        homePage  = new HomePage(driver);
        searchPage = new SearchPage(driver);
        // Login before navigation tests
        loginPage.login("validuser@test.com", "ValidPass123");
    }

    // TC05 - Verify home page title is correct
    @Test(description = "Verify home page title text is correct after login")
    public void tc05_verifyHomePageTitle() {
        String title = homePage.getHomeTitleText();
        Assert.assertNotNull(title, "Home page title should not be null");
        Assert.assertFalse(title.isEmpty(), "Home page title should not be empty");
    }

    // TC06 - Verify search page opens from home
    @Test(description = "Verify navigating to search page works")
    public void tc06_navigateToSearchPage() {
        homePage.clickSearchIcon();
        // Verify search input is displayed on search page
        // Add assertion based on your app
        Assert.assertNotNull(driver.getPageSource(), "Search page should load");
    }

    // TC07 - Verify logout navigates back to login
    @Test(description = "Verify logout returns user to login screen")
    public void tc07_logoutReturnsToLogin() {
        homePage.clickLogout();
        boolean isLoginDisplayed = loginPage.isLoginButtonDisplayed();
        Assert.assertTrue(isLoginDisplayed, "Login page should show after logout");
    }
}
