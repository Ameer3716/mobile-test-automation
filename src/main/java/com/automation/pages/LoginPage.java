package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private AndroidDriver driver;

    // ======= ELEMENT LOCATORS =======
    // IMPORTANT: Replace these locators with real ones from your app
    // Use Appium Inspector to find the correct IDs/XPaths

    @AndroidFindBy(id = "com.your.app:id/username_field")
    private WebElement usernameField;

    @AndroidFindBy(id = "com.your.app:id/password_field")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.your.app:id/login_button")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.your.app:id/error_message")
    private WebElement errorMessage;

    @AndroidFindBy(id = "com.your.app:id/forgot_password_link")
    private WebElement forgotPasswordLink;

    // ======= CONSTRUCTOR =======
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ======= PAGE ACTIONS =======

    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }

    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }

    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }
}
