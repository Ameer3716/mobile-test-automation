# Mobile Test Automation Framework

## Project Overview
This project is a mobile test automation framework built using Java, Appium, and TestNG.
It automates functional test cases for an Android application using the Page Object Model (POM) design pattern.

## Team Members
- [Your Name] - Framework setup, test implementation, CI pipeline
- [Partner Name] - Page objects, test cases, documentation

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
- Appium Server (`npm install -g appium`)
- Appium UiAutomator2 driver (`appium driver install uiautomator2`)
- Android Emulator or physical device

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/Ameer3716/mobile-test-automation.git
cd mobile-test-automation
```

### 2. Configure the App
- Place your APK in the `apps/` folder
- Update `config/config.properties` with your device name and APK path

### 3. Start Appium Server
```bash
appium
```

### 4. Start Android Emulator
Open Android Studio -> Device Manager -> Start your AVD

## How to Run Tests Locally
```bash
mvn test
```
Or run a specific test class:
```bash
mvn test -Dtest=LoginTests
```

## Project Structure
```
src/
  main/java/com/automation/
    base/       - BaseTest (driver setup/teardown)
    pages/      - Page Object Model classes
    utils/      - Helper utilities
  test/java/com/automation/
    tests/      - Test classes
config/         - Configuration properties
apps/           - APK files
.github/workflows/ - CI pipeline
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

## CI Workflow
GitHub Actions pipeline (`.github/workflows/ci.yml`) runs on every push and PR to `main`.
It builds the project and compiles all test classes to validate the framework.

## Git Workflow
- `main` - Protected branch, no direct commits
- `develop` - Integration branch
- `feature/xxx` - Individual feature branches
- All changes go through Pull Requests with peer review
