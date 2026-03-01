package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    private AndroidDriver driver;

    // Replace locators with your app's real element IDs

    @AndroidFindBy(id = "com.your.app:id/product_title")
    private WebElement productTitle;

    @AndroidFindBy(id = "com.your.app:id/product_price")
    private WebElement productPrice;

    @AndroidFindBy(id = "com.your.app:id/add_to_cart_button")
    private WebElement addToCartButton;

    @AndroidFindBy(id = "com.your.app:id/back_button")
    private WebElement backButton;

    public ProductPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getProductTitle() {
        return productTitle.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void clickBack() {
        backButton.click();
    }

    public boolean isProductPageDisplayed() {
        return productTitle.isDisplayed();
    }
}
