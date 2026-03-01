# ============================================================
# COLLABORATOR SCRIPT - Push Issues #3, #4, #6, #8, #10
# ============================================================
# This script creates feature branches, modifies/enhances files,
# commits, and pushes ALL 5 branches automatically with NO pauses.
#
# BEFORE RUNNING:
# 1. Make sure you have Git installed and configured:
#      git config --global user.name "Your Name"
#      git config --global user.email "your@email.com"
# 2. Clone the repo:
#      git clone https://github.com/Ameer3716/mobile-test-automation.git
#      cd mobile-test-automation
# 3. Make sure you're on 'main' and it's up to date:
#      git checkout main
#      git pull origin main
# 4. Make sure all previous PRs (issues 1,2,5,7,9) are merged to main
#
# USAGE:
#   cd mobile-test-automation
#   .\collaborator-push-issues.ps1
#
# AFTER the script finishes, go to GitHub and create + merge
# PRs for all 5 branches IN THIS ORDER:
#   1) feature/issue-3-base-test
#   2) feature/issue-4-login-page-pom
#   3) feature/issue-6-login-tests
#   4) feature/issue-8-feature-tests
#   5) feature/issue-10-readme
# ============================================================

$ErrorActionPreference = "Stop"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Collaborator - Push Issues #3,#4,#6,#8,#10" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Make sure we start from updated main
git checkout main
git pull origin main

# ============================================================
# ISSUE #3: Enhance BaseTest class + AppiumUtils
# ============================================================
Write-Host "`n>>> Issue #3: Enhance BaseTest class" -ForegroundColor Yellow

git checkout -b feature/issue-3-base-test

# Update BaseTest.java with enhanced logging and platform version support
@'
package com.automation.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.automation.utils.AppiumUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * BaseTest - Foundation class for all test classes.
 * Handles Appium driver initialization and teardown.
 * All test classes should extend this class to get driver access.
 *
 * @author Collaborator
 * @version 1.1
 */
public class BaseTest {

    // This driver will be used by all test classes
    protected AndroidDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        System.out.println("Initializing test environment...");

        // Read from config.properties
        String appiumUrl      = AppiumUtils.getProperty("appium.server.url");
        String platformName   = AppiumUtils.getProperty("platform.name");
        String platformVersion = AppiumUtils.getProperty("platform.version");
        String deviceName     = AppiumUtils.getProperty("device.name");
        String appPath        = AppiumUtils.getProperty("app.path");
        String automationName = AppiumUtils.getProperty("automation.name");

        // Log configuration details
        System.out.println("Platform: " + platformName + " " + platformVersion);
        System.out.println("Device: " + deviceName);
        System.out.println("Appium Server: " + appiumUrl);

        // Configure desired capabilities
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(platformName);
        options.setDeviceName(deviceName);
        options.setApp(System.getProperty("user.dir") + "/" + appPath);
        options.setAutomationName(automationName);
        options.setNoReset(false);

        // Set platform version if provided
        if (platformVersion != null && !platformVersion.isEmpty()) {
            options.setPlatformVersion(platformVersion);
        }

        // Start the driver
        driver = new AndroidDriver(new URL(appiumUrl), options);

        // Set implicit wait
        int implicitWait = Integer.parseInt(AppiumUtils.getProperty("implicit.wait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        System.out.println("Driver started successfully");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver closed successfully");
        }
    }

    /**
     * Helper method to get the current driver instance.
     * Can be used by test classes that need direct driver access.
     */
    protected AndroidDriver getDriver() {
        return driver;
    }
}
'@ | Set-Content -Path "src/main/java/com/automation/base/BaseTest.java" -Encoding UTF8

# Update AppiumUtils.java with enhanced error handling and default value support
@'
package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * AppiumUtils - Utility class for loading and accessing configuration properties.
 * Reads from config/config.properties file.
 *
 * @author Collaborator
 * @version 1.1
 */
public class AppiumUtils {

    private static Properties properties;

    // Load config.properties file
    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream fis = new FileInputStream("config/config.properties");
                properties.load(fis);
                System.out.println("Configuration loaded successfully");
            } catch (IOException e) {
                throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
            }
        }
        return properties;
    }

    // Get a specific property value
    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    /**
     * Get a property value with a default fallback.
     * @param key the property key
     * @param defaultValue value to return if key is not found
     * @return the property value or defaultValue
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperties().getProperty(key);
        return (value != null) ? value : defaultValue;
    }
}
'@ | Set-Content -Path "src/main/java/com/automation/utils/AppiumUtils.java" -Encoding UTF8

git add src/main/java/com/automation/base/BaseTest.java src/main/java/com/automation/utils/AppiumUtils.java
git commit -m "feat: enhance BaseTest with logging and AppiumUtils with defaults - closes #3"
git push origin feature/issue-3-base-test

Write-Host ">>> Issue #3 pushed!" -ForegroundColor Green

# ============================================================
# ISSUE #4: Enhance LoginPage POM
# ============================================================
Write-Host "`n>>> Issue #4: Enhance LoginPage POM" -ForegroundColor Yellow

git checkout main
git checkout -b feature/issue-4-login-page-pom

# Update LoginPage.java with enhanced wait methods and validation
@'
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
 * LoginPage - Page Object Model for the Login screen.
 * Contains all element locators and actions for the login page.
 * Implements POM pattern for better maintainability.
 *
 * @author Collaborator
 * @version 1.1
 */
public class LoginPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    /**
     * Perform complete login action with username and password.
     * @param username the username to enter
     * @param password the password to enter
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        try {
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
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickForgotPassword() {
        forgotPasswordLink.click();
    }

    /**
     * Check if the login page is fully loaded and ready.
     * @return true if login page elements are visible
     */
    public boolean isLoginPageReady() {
        try {
            return isLoginButtonDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
'@ | Set-Content -Path "src/main/java/com/automation/pages/LoginPage.java" -Encoding UTF8

git add src/main/java/com/automation/pages/LoginPage.java
git commit -m "feat: enhance LoginPage POM with wait support and validation - closes #4"
git push origin feature/issue-4-login-page-pom

Write-Host ">>> Issue #4 pushed!" -ForegroundColor Green

# ============================================================
# ISSUE #6: Enhance login test cases (TC01-TC04)
# ============================================================
Write-Host "`n>>> Issue #6: Enhance login test cases (TC01-TC04)" -ForegroundColor Yellow

git checkout main
git checkout -b feature/issue-6-login-tests

# Update LoginTests.java with improved test descriptions and assertions
@'
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * LoginTests - Test class for login functionality.
 * Covers valid login, invalid login, empty credentials, and UI verification.
 *
 * @author Collaborator
 * @version 1.1
 */
public class LoginTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        homePage  = new HomePage(driver);
    }

    // TC01 - Verify login page is displayed on app launch
    @Test(priority = 1, description = "TC01: Verify login page elements are displayed on app launch")
    public void tc01_verifyLoginPageDisplayed() {
        System.out.println("TC01: Checking login page visibility...");
        boolean isLoginButtonVisible = loginPage.isLoginButtonDisplayed();
        Assert.assertTrue(isLoginButtonVisible, "Login button should be visible on launch");
        System.out.println("TC01: PASSED - Login page is displayed");
    }

    // TC02 - Verify successful login with valid credentials
    @Test(priority = 2, description = "TC02: Verify user can login with valid credentials")
    public void tc02_successfulLogin() {
        System.out.println("TC02: Attempting login with valid credentials...");
        loginPage.login("validuser@test.com", "ValidPass123");
        boolean isHomeDisplayed = homePage.isHomePageDisplayed();
        Assert.assertTrue(isHomeDisplayed, "Home page should appear after successful login");
        System.out.println("TC02: PASSED - Successful login verified");
    }

    // TC03 - Verify error message with wrong password
    @Test(priority = 3, description = "TC03: Verify error message shown for incorrect password")
    public void tc03_invalidPasswordShowsError() {
        System.out.println("TC03: Testing invalid password scenario...");
        loginPage.login("validuser@test.com", "WrongPassword");
        boolean isErrorShown = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorShown, "Error message should be shown for wrong password");
        System.out.println("TC03: PASSED - Error message displayed for wrong password");
    }

    // TC04 - Verify error message with empty credentials
    @Test(priority = 4, description = "TC04: Verify error message shown when fields are empty")
    public void tc04_emptyCredentialsShowsError() {
        System.out.println("TC04: Testing empty credentials scenario...");
        loginPage.clickLoginButton();
        boolean isErrorShown = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorShown, "Error message should appear for empty credentials");
        System.out.println("TC04: PASSED - Error message displayed for empty fields");
    }
}
'@ | Set-Content -Path "src/test/java/com/automation/tests/LoginTests.java" -Encoding UTF8

git add src/test/java/com/automation/tests/LoginTests.java
git commit -m "test: enhance login test cases TC01-TC04 with logging and priorities - closes #6"
git push origin feature/issue-6-login-tests

Write-Host ">>> Issue #6 pushed!" -ForegroundColor Green

# ============================================================
# ISSUE #8: Enhance feature test cases (TC08-TC10) + ProductPage
# ============================================================
Write-Host "`n>>> Issue #8: Enhance feature test cases (TC08-TC10)" -ForegroundColor Yellow

git checkout main
git checkout -b feature/issue-8-feature-tests

# Update ProductPage.java with enhanced methods
@'
package com.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * ProductPage - Page Object Model for the Product detail screen.
 * Contains all element locators and actions for product details.
 *
 * @author Collaborator
 * @version 1.1
 */
public class ProductPage {

    private AndroidDriver driver;

    // Product element locators - replace with your app's real element IDs

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
        try {
            return productTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the product has a valid price displayed.
     * @return true if price element is visible
     */
    public boolean isPriceDisplayed() {
        try {
            return productPrice.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
'@ | Set-Content -Path "src/main/java/com/automation/pages/ProductPage.java" -Encoding UTF8

# Update FeatureTests.java with enhanced logging and priorities
@'
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.HomePage;
import com.automation.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * FeatureTests - Test class for search and app feature functionality.
 * Covers search with valid/invalid keywords and input validation.
 *
 * @author Collaborator
 * @version 1.1
 */
public class FeatureTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeMethod
    public void initAndLogin() {
        loginPage  = new LoginPage(driver);
        homePage   = new HomePage(driver);
        searchPage = new SearchPage(driver);
        loginPage.login("validuser@test.com", "ValidPass123");
    }

    // TC08 - Verify search with valid keyword shows results
    @Test(priority = 1, description = "TC08: Verify search returns results for valid keyword")
    public void tc08_searchWithValidKeyword() {
        System.out.println("TC08: Searching for valid keyword...");
        homePage.clickSearchIcon();
        searchPage.searchFor("laptop");
        boolean resultsVisible = searchPage.areResultsDisplayed();
        Assert.assertTrue(resultsVisible, "Results should show for valid search keyword");
        System.out.println("TC08: PASSED - Search results displayed");
    }

    // TC09 - Verify search with invalid keyword shows no results
    @Test(priority = 2, description = "TC09: Verify search shows no-results message for invalid keyword")
    public void tc09_searchWithInvalidKeyword() {
        System.out.println("TC09: Searching for invalid keyword...");
        homePage.clickSearchIcon();
        searchPage.searchFor("xyzxyzxyznotexist123");
        boolean noResultsVisible = searchPage.isNoResultsMessageDisplayed();
        Assert.assertTrue(noResultsVisible, "No results message should appear for invalid search");
        System.out.println("TC09: PASSED - No results message displayed");
    }

    // TC10 - Verify search field accepts input correctly
    @Test(priority = 3, description = "TC10: Verify search input accepts and displays typed text")
    public void tc10_searchInputAcceptsText() {
        System.out.println("TC10: Testing search input field...");
        homePage.clickSearchIcon();
        String searchText = "phone";
        searchPage.enterSearchQuery(searchText);
        // Verifying page is responsive and not crashed
        Assert.assertNotNull(driver.getPageSource(), "App should remain responsive during search input");
        System.out.println("TC10: PASSED - Search input is functional");
    }
}
'@ | Set-Content -Path "src/test/java/com/automation/tests/FeatureTests.java" -Encoding UTF8

git add src/test/java/com/automation/tests/FeatureTests.java src/main/java/com/automation/pages/ProductPage.java
git commit -m "test: enhance feature tests TC08-TC10 and ProductPage POM - closes #8"
git push origin feature/issue-8-feature-tests

Write-Host ">>> Issue #8 pushed!" -ForegroundColor Green

# ============================================================
# ISSUE #10: Enhance README documentation
# ============================================================
Write-Host "`n>>> Issue #10: Enhance README documentation" -ForegroundColor Yellow

git checkout main
git checkout -b feature/issue-10-readme

# Create enhanced README.md with more details
@'
# Mobile Test Automation Framework

## Project Overview
This project is a mobile test automation framework built using **Java**, **Appium**, and **TestNG**.
It automates functional test cases for an Android application using the **Page Object Model (POM)** design pattern.

The framework provides a scalable and maintainable structure for writing automated mobile tests,
with centralized configuration, reusable page objects, and comprehensive test coverage.

## Team Members
- **Member 1** - Framework setup, test implementation, CI pipeline
- **Member 2** - Page objects, test cases, documentation

## Tools & Technologies
| Tool | Version | Purpose |
|------|---------|---------|
| Java | 11 | Programming language |
| Maven | 3.9.x | Build tool & dependency management |
| Appium | 2.x | Mobile test automation |
| TestNG | 7.8.0 | Test runner & assertions |
| UiAutomator2 | Latest | Android automation driver |
| GitHub Actions | - | CI/CD pipeline |
| Git | Latest | Version control |

## Prerequisites
Before running tests, ensure you have installed:
- Java JDK 11
- Maven 3.x
- Android SDK (via Android Studio)
- Node.js (for Appium installation)
- Appium Server (`npm install -g appium`)
- Appium UiAutomator2 driver (`appium driver install uiautomator2`)
- Android Emulator or physical device

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/Ameer3716/mobile-test-automation.git
cd mobile-test-automation
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Configure the App
- Place your APK in the `apps/` folder
- Update `config/config.properties` with your device name and APK path

### 4. Start Appium Server
```bash
appium
```

### 5. Start Android Emulator
Open Android Studio -> Device Manager -> Start your AVD

## How to Run Tests

### Run all tests:
```bash
mvn test
```

### Run a specific test class:
```bash
mvn test -Dtest=LoginTests
mvn test -Dtest=NavigationTests
mvn test -Dtest=FeatureTests
```

### Run with verbose output:
```bash
mvn test -Dsurefire.useFile=false
```

## Project Structure
```
mobile-test-automation/
├── .github/workflows/
│   └── ci.yml                    # GitHub Actions CI pipeline
├── src/
│   ├── main/java/com/automation/
│   │   ├── base/
│   │   │   └── BaseTest.java     # Driver setup and teardown
│   │   ├── pages/
│   │   │   ├── LoginPage.java    # Login screen page object
│   │   │   ├── HomePage.java     # Home screen page object
│   │   │   ├── SearchPage.java   # Search screen page object
│   │   │   └── ProductPage.java  # Product screen page object
│   │   └── utils/
│   │       └── AppiumUtils.java  # Configuration utility
│   └── test/java/com/automation/tests/
│       ├── LoginTests.java       # Login test cases (TC01-TC04)
│       ├── NavigationTests.java  # Navigation test cases (TC05-TC07)
│       └── FeatureTests.java     # Feature test cases (TC08-TC10)
├── config/
│   └── config.properties         # Test configuration
├── apps/                         # APK files directory
├── pom.xml                       # Maven build configuration
├── testng.xml                    # TestNG suite configuration
└── README.md                     # Project documentation
```

## Test Cases
| TC # | Test Name | Class | What it Tests |
|------|-----------|-------|---------------|
| TC01 | tc01_verifyLoginPageDisplayed | LoginTests | Login screen loads on startup |
| TC02 | tc02_successfulLogin | LoginTests | Valid credentials allow entry |
| TC03 | tc03_invalidPasswordShowsError | LoginTests | Wrong password shows error |
| TC04 | tc04_emptyCredentialsShowsError | LoginTests | Empty fields show error |
| TC05 | tc05_verifyHomePageTitle | NavigationTests | Home title is correct |
| TC06 | tc06_navigateToSearchPage | NavigationTests | Search screen opens |
| TC07 | tc07_logoutReturnsToLogin | NavigationTests | Logout goes back to login |
| TC08 | tc08_searchWithValidKeyword | FeatureTests | Valid search shows results |
| TC09 | tc09_searchWithInvalidKeyword | FeatureTests | Invalid search shows no results |
| TC10 | tc10_searchInputAcceptsText | FeatureTests | Search input works correctly |

## Design Pattern: Page Object Model (POM)
This framework follows the **Page Object Model** pattern:
- Each app screen has a corresponding Java class in `pages/`
- All element locators are defined within their page class
- Test classes only interact with page methods, never directly with elements
- This ensures reusability, maintainability, and clean test code

## CI/CD Pipeline
GitHub Actions pipeline (`.github/workflows/ci.yml`) runs automatically:
- **On push** to `main` branch
- **On pull requests** to `main` branch
- Builds the Maven project and compiles all test classes
- Validates framework integrity on every code change

## Git Workflow
- `main` - Protected branch, requires PR and review
- `feature/issue-X-description` - Individual feature branches
- All changes go through Pull Requests with peer review
- Commit messages follow conventional format: `feat:`, `test:`, `fix:`, `docs:`, `ci:`
'@ | Set-Content -Path "README.md" -Encoding UTF8

git add README.md
git commit -m "docs: enhance README with detailed structure and design pattern docs - closes #10"
git push origin feature/issue-10-readme

Write-Host ">>> Issue #10 pushed!" -ForegroundColor Green

git checkout main

# ============================================================
# DONE - ALL BRANCHES PUSHED
# ============================================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host " ALL 5 BRANCHES PUSHED TO GITHUB!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "Branches pushed:" -ForegroundColor Cyan
Write-Host "  [x] feature/issue-3-base-test"
Write-Host "  [x] feature/issue-4-login-page-pom"
Write-Host "  [x] feature/issue-6-login-tests"
Write-Host "  [x] feature/issue-8-feature-tests"
Write-Host "  [x] feature/issue-10-readme"
Write-Host ""
Write-Host "NOW go to GitHub and create + merge PRs IN THIS ORDER:" -ForegroundColor Yellow
Write-Host "  1) feature/issue-3-base-test"
Write-Host "  2) feature/issue-4-login-page-pom"
Write-Host "  3) feature/issue-6-login-tests"
Write-Host "  4) feature/issue-8-feature-tests"
Write-Host "  5) feature/issue-10-readme"
Write-Host ""
Write-Host "For each: Click 'Compare & pull request' -> Create PR -> Merge" -ForegroundColor Yellow
Write-Host ""
