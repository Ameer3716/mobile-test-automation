package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import com.automation.pages.ProductPage;
import com.automation.pages.CartPage;
import com.automation.utils.AppiumUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Navigation functionality (TC05 - TC07).
 * Tests home page display, product navigation, and logout flow.
 * Each test is independent with fresh page object initialization.
 */
public class NavigationTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeMethod
    public void initPages() {
        loginPage   = new LoginPage(driver);
        homePage    = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage    = new CartPage(driver);
    }

    /**
     * TC05 - Verify home/catalog page loads with products.
     * App opens to the product catalog; verifies title and product display.
     */
    @Test(priority = 1, description = "TC05: Verify home page displays products after launch")
    public void tc05_verifyHomePageDisplaysProducts() {
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Product catalog page should be displayed on app launch");
        String title = homePage.getHomeTitleText();
        Assert.assertEquals(title, "Products",
                "Home page title should be 'Products'");
        Assert.assertTrue(homePage.areProductsDisplayed(),
                "Products should be visible on the catalog page");
    }

    /**
     * TC06 - Verify user can navigate to product detail page.
     * Taps the first product and verifies detail page loads.
     */
    @Test(priority = 2, description = "TC06: Verify navigation to product detail page")
    public void tc06_navigateToProductDetail() {
        homePage.clickProductAtIndex(0);

        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Product detail page should be displayed after clicking a product");
        String productTitle = productPage.getProductTitle();
        Assert.assertNotNull(productTitle, "Product title should not be null");
        Assert.assertFalse(productTitle.isEmpty(), "Product title should not be empty");
    }

    /**
     * TC07 - Verify logout returns user to catalog and clears session.
     * Logs in first, then logs out and verifies return to catalog.
     */
    @Test(priority = 3, description = "TC07: Verify logout navigates back to catalog")
    public void tc07_logoutReturnsToHome() {
        // Login first
        homePage.navigateToLogin();
        String validUser = AppiumUtils.getProperty("valid.username");
        String validPass = AppiumUtils.getProperty("valid.password");
        loginPage.login(validUser, validPass);

        // Verify we are on home page after login
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Home page should be visible after login");

        // Perform logout
        homePage.logout();

        // Verify we're back to catalog
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Catalog page should be displayed after logout");
    }
}
