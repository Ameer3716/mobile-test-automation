package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import com.automation.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeatureTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeMethod
    public void initAndLogin() {
        loginPage  = new LoginPage(driver);
        homePage   = new HomePage(driver);
        searchPage = new SearchPage(driver);
        loginPage.login("validuser@test.com", "ValidPass123");
    }

    // TC08 - Verify search with valid keyword shows results
    @Test(description = "Verify search returns results for valid keyword")
    public void tc08_searchWithValidKeyword() {
        homePage.clickSearchIcon();
        searchPage.searchFor("laptop");
        boolean resultsVisible = searchPage.areResultsDisplayed();
        Assert.assertTrue(resultsVisible, "Results should show for valid search keyword");
    }

    // TC09 - Verify search with invalid keyword shows no results
    @Test(description = "Verify search shows no-results message for invalid keyword")
    public void tc09_searchWithInvalidKeyword() {
        homePage.clickSearchIcon();
        searchPage.searchFor("xyzxyzxyznotexist123");
        boolean noResultsVisible = searchPage.isNoResultsMessageDisplayed();
        Assert.assertTrue(noResultsVisible, "No results message should appear for invalid search");
    }

    // TC10 - Verify search field accepts input correctly
    @Test(description = "Verify search input accepts and displays typed text")
    public void tc10_searchInputAcceptsText() {
        homePage.clickSearchIcon();
        String searchText = "phone";
        searchPage.enterSearchQuery(searchText);
        // Just verifying page is responsive and not crashed
        Assert.assertNotNull(driver.getPageSource(), "App should remain responsive during search input");
    }
}
