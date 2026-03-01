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
 * Page Object for the Login screen of SauceLabs My Demo App.
 * Encapsulates all login-related element locators and user actions.
 */
public class LoginPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    // ======= ELEMENT LOCATORS (SauceLabs My Demo App) =======

    @AndroidFindBy(accessibility = "Username input field")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "Password input field")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "Login button")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'provided credentials')]")
    private WebElement errorMessage;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Username is required')]")
    private WebElement usernameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Password is required')]")
    private WebElement passwordRequiredError;

    // ======= CONSTRUCTOR =======
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // ======= PAGE ACTIONS =======

    /** Enter username in the username field. */
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /** Enter password in the password field. */
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /** Click the login button. */
    public void clickLoginButton() {
        loginButton.click();
    }

    /** Perform full login with given credentials. */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /** Check if the generic error message is displayed. */
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Get the text of the error message. */
    public String getErrorMessageText() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    /** Check if username required error is shown. */
    public boolean isUsernameRequiredErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameRequiredError));
            return usernameRequiredError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Check if password required error is shown. */
    public boolean isPasswordRequiredErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordRequiredError));
            return passwordRequiredError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Check if the login button is visible on screen. */
    public boolean isLoginButtonDisplayed() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Check if the username field is visible. */
    public boolean isUsernameFieldDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            return usernameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
