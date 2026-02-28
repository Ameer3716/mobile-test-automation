package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    private AndroidDriver driver;

    // Replace locators with your app's real element IDs

    @AndroidFindBy(id = "com.your.app:id/search_input")
    private WebElement searchInput;

    @AndroidFindBy(id = "com.your.app:id/search_submit")
    private WebElement searchSubmitButton;

    @AndroidFindBy(id = "com.your.app:id/search_results")
    private WebElement searchResults;

    @AndroidFindBy(id = "com.your.app:id/no_results_text")
    private WebElement noResultsText;

    public SearchPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void enterSearchQuery(String query) {
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public void clickSearch() {
        searchSubmitButton.click();
    }

    public void searchFor(String query) {
        enterSearchQuery(query);
        clickSearch();
    }

    public boolean areResultsDisplayed() {
        return searchResults.isDisplayed();
    }

    public boolean isNoResultsMessageDisplayed() {
        return noResultsText.isDisplayed();
    }
}
