package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object for the Product Detail screen of SauceLabs My Demo App.
 * Encapsulates product details, pricing, and add-to-cart actions.
 */
public class ProductPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    // ======= ELEMENT LOCATORS (SauceLabs My Demo App) =======

    @AndroidFindBy(accessibility = "product title")
    private WebElement productTitle;

    @AndroidFindBy(accessibility = "product price")
    private WebElement productPrice;

    @AndroidFindBy(accessibility = "Add To Cart button")
    private WebElement addToCartButton;

    @AndroidFindBy(accessibility = "cart badge")
    private WebElement cartBadge;

    @AndroidFindBy(accessibility = "Go back")
    private WebElement backButton;

    @AndroidFindBy(accessibility = "counter plus button")
    private WebElement increaseQuantityButton;

    @AndroidFindBy(accessibility = "counter minus button")
    private WebElement decreaseQuantityButton;

    @AndroidFindBy(accessibility = "counter amount")
    private WebElement quantityAmount;

    // ======= CONSTRUCTOR =======
    public ProductPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ======= PAGE ACTIONS =======

    /** Get the title text of the current product. */
    public String getProductTitle() {
        wait.until(ExpectedConditions.visibilityOf(productTitle));
        return productTitle.getText();
    }

    /** Get the price text of the current product. */
    public String getProductPrice() {
        return productPrice.getText();
    }

    /** Click the Add To Cart button. */
    public void clickAddToCart() {
        addToCartButton.click();
    }

    /** Navigate back to the product listing. */
    public void clickBack() {
        backButton.click();
    }

    /** Check if the product detail page is displayed. */
    public boolean isProductPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productTitle));
            return productTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Increase the product quantity. */
    public void increaseQuantity() {
        increaseQuantityButton.click();
    }

    /** Decrease the product quantity. */
    public void decreaseQuantity() {
        decreaseQuantityButton.click();
    }

    /** Get the current quantity value. */
    public String getQuantity() {
        return quantityAmount.getText();
    }

    /** Click on cart badge to go to cart. */
    public void goToCart() {
        cartBadge.click();
    }
}
