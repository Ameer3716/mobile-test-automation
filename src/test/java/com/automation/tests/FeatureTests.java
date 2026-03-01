package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.ProductPage;
import com.automation.pages.CartPage;
import com.automation.pages.LoginPage;
import com.automation.utils.AppiumUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Cart and Product Features (TC08 - TC10).
 * Tests add-to-cart, cart display, and product details functionality.
 * Each test is independent with proper assertions.
 */
public class FeatureTests extends BaseTest {

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
     * TC08 - Verify adding a product to cart from product detail page.
     * Opens first product, adds to cart, then verifies cart page shows item.
     */
    @Test(priority = 1, description = "TC08: Verify adding a product to cart")
    public void tc08_addProductToCart() {
        // Click the first product to open detail
        homePage.clickProductAtIndex(0);
        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Product detail page should be displayed");

        // Add product to cart
        productPage.clickAddToCart();

        // Navigate to cart
        productPage.goToCart();

        // Verify cart has items (checkout button visible means items exist)
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page should be displayed");
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
                "Checkout button should be visible indicating items in cart");
    }

    /**
     * TC09 - Verify product detail page displays correct information.
     * Opens a product and checks that title and price are present.
     */
    @Test(priority = 2, description = "TC09: Verify product detail displays title and price")
    public void tc09_productDetailShowsInfo() {
        homePage.clickProductAtIndex(0);

        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Product detail page should load");

        String title = productPage.getProductTitle();
        Assert.assertNotNull(title, "Product title should not be null");
        Assert.assertFalse(title.isEmpty(), "Product title should not be empty");

        String price = productPage.getProductPrice();
        Assert.assertNotNull(price, "Product price should not be null");
        Assert.assertTrue(price.contains("$"), "Product price should contain '$' symbol");
    }

    /**
     * TC10 - Verify navigating back from product detail returns to catalog.
     * Tests the back navigation from product detail screen.
     */
    @Test(priority = 3, description = "TC10: Verify back navigation from product detail")
    public void tc10_backFromProductDetailReturnsToCatalog() {
        // Navigate to product detail
        homePage.clickProductAtIndex(0);
        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Product detail page should be displayed");

        // Navigate back
        productPage.clickBack();

        // Verify we are back on catalog
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Catalog page should be displayed after pressing back");
        Assert.assertTrue(homePage.areProductsDisplayed(),
                "Products should be visible on the catalog page");
    }
}
