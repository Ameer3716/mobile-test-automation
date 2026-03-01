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
 * Page Object for the Cart screen of SauceLabs My Demo App.
 * Handles cart items, checkout flow, and cart-related assertions.
 */
public class CartPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    // ======= ELEMENT LOCATORS (SauceLabs My Demo App) =======

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='My Cart']")
    private WebElement cartTitle;

    @AndroidFindBy(accessibility = "Proceed To Checkout button")
    private WebElement checkoutButton;

    @AndroidFindBy(accessibility = "remove item")
    private WebElement removeItemButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='No Items']")
    private WebElement noItemsText;

    @AndroidFindBy(accessibility = "total price")
    private WebElement totalPrice;

    @AndroidFindBy(accessibility = "Go back")
    private WebElement backButton;

    // ======= CONSTRUCTOR =======
    public CartPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ======= PAGE ACTIONS =======

    /** Check if the cart screen is displayed. */
    public boolean isCartPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartTitle));
            return cartTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Get the cart title text. */
    public String getCartTitle() {
        wait.until(ExpectedConditions.visibilityOf(cartTitle));
        return cartTitle.getText();
    }

    /** Click the Proceed To Checkout button. */
    public void clickCheckout() {
        checkoutButton.click();
    }

    /** Remove an item from the cart. */
    public void removeItem() {
        removeItemButton.click();
    }

    /** Check if the cart is empty (no items text shown). */
    public boolean isCartEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOf(noItemsText));
            return noItemsText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Get the total price displayed in cart. */
    public String getTotalPrice() {
        return totalPrice.getText();
    }

    /** Navigate back from the cart. */
    public void clickBack() {
        backButton.click();
    }

    /** Check if checkout button is displayed (means items exist). */
    public boolean isCheckoutButtonDisplayed() {
        try {
            return checkoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
