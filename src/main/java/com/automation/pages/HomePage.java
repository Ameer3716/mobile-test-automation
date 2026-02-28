package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private AndroidDriver driver;

    // Replace locators with your app's real element IDs

    @AndroidFindBy(id = "com.your.app:id/home_title")
    private WebElement homeTitle;

    @AndroidFindBy(id = "com.your.app:id/search_icon")
    private WebElement searchIcon;

    @AndroidFindBy(id = "com.your.app:id/profile_icon")
    private WebElement profileIcon;

    @AndroidFindBy(id = "com.your.app:id/logout_button")
    private WebElement logoutButton;

    @AndroidFindBy(id = "com.your.app:id/menu_button")
    private WebElement menuButton;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isHomePageDisplayed() {
        return homeTitle.isDisplayed();
    }

    public String getHomeTitleText() {
        return homeTitle.getText();
    }

    public void clickSearchIcon() {
        searchIcon.click();
    }

    public void clickProfileIcon() {
        profileIcon.click();
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public void clickMenuButton() {
        menuButton.click();
    }
}
