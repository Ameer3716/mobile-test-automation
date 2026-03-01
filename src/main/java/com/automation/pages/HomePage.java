package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Product Catalog (Home) screen of SauceLabs My Demo App.
 * This is the main landing page that displays the product listing.
 */
public class HomePage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    // ======= ELEMENT LOCATORS (SauceLabs My Demo App) =======

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Products']")
    private WebElement productsTitle;

    @AndroidFindBy(accessibility = "open menu")
    private WebElement menuButton;

    @AndroidFindBy(accessibility = "cart badge")
    private WebElement cartBadge;

    @AndroidFindBy(accessibility = "sort button")
    private WebElement sortButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='store item']")
    private List<WebElement> productItems;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Log In']")
    private WebElement menuLoginOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Log Out']")
    private WebElement menuLogoutOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Catalog']")
    private WebElement menuCatalogOption;

    // ======= CONSTRUCTOR =======
    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ======= PAGE ACTIONS =======

    /** Check if the Products title is displayed (confirms we're on home page). */
    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productsTitle));
            return productsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Get the title text shown on the home page. */
    public String getHomeTitleText() {
        wait.until(ExpectedConditions.visibilityOf(productsTitle));
        return productsTitle.getText();
    }

    /** Open the side navigation menu. */
    public void openMenu() {
        menuButton.click();
    }

    /** Navigate to login screen via the menu. */
    public void navigateToLogin() {
        openMenu();
        wait.until(ExpectedConditions.visibilityOf(menuLoginOption));
        menuLoginOption.click();
    }

    /** Perform logout via the menu. */
    public void logout() {
        openMenu();
        wait.until(ExpectedConditions.visibilityOf(menuLogoutOption));
        menuLogoutOption.click();
    }

    /** Navigate to catalog via the menu. */
    public void navigateToCatalog() {
        openMenu();
        wait.until(ExpectedConditions.visibilityOf(menuCatalogOption));
        menuCatalogOption.click();
    }

    /** Click the cart icon/badge. */
    public void clickCart() {
        cartBadge.click();
    }

    /** Click the sort button to open sorting options. */
    public void clickSortButton() {
        sortButton.click();
    }

    /** Get the count of product items visible on screen. */
    public int getProductCount() {
        return productItems.size();
    }

    /** Click on a product by its index in the list. */
    public void clickProductAtIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            productItems.get(index).click();
        }
    }

    /** Check if products are loaded on the page. */
    public boolean areProductsDisplayed() {
        try {
            return productItems.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
