# Mobile Test Automation Framework

## Project Overview
A comprehensive mobile test automation framework for the **SauceLabs My Demo App** (Android) built with **Java 11**, **Appium 2.x**, and **TestNG 7.8.0**. The framework follows the **Page Object Model (POM)** design pattern and includes 10 automated functional test cases covering login, navigation, product details, and cart functionality.

### Key Features
- **Page Object Model** architecture for maintainability and code reuse
- **10 independent test cases** with proper TestNG assertions
- **Parallel test execution** via TestNG (`parallel="tests"`, `thread-count="3"`)
- **HTML test reports** using ExtentReports + Maven Surefire Reports
- **CI/CD pipeline** with GitHub Actions (build on every push and PR)
- **Configuration-driven** setup via `config.properties`

---

## Tools and Technologies

| Tool / Technology | Version | Purpose |
|-------------------|---------|---------|
| Java JDK | 11 | Programming language |
| Maven | 3.9.x | Build tool & dependency management |
| Appium | 2.x | Mobile automation server |
| Appium Java Client | 8.6.0 | Java bindings for Appium |
| TestNG | 7.8.0 | Test runner, assertions & parallel execution |
| Selenium | 4.15.0 | WebDriver API (Appium dependency) |
| UiAutomator2 | Latest | Android automation driver |
| ExtentReports | 5.1.1 | HTML test report generation |
| GitHub Actions | - | Continuous Integration pipeline |
| Git | Latest | Version control |

---

## Project Structure
```
mobile-test-automation/
├── .github/workflows/
│   └── ci.yml                    # GitHub Actions CI pipeline
├── apps/
│   └── SauceLabs-My-Demo-App.apk # Android application under test
├── config/
│   └── config.properties          # Device, app & server configuration
├── src/
│   ├── main/java/com/automation/
│   │   ├── base/
│   │   │   └── BaseTest.java      # Driver setup & teardown
│   │   ├── pages/
│   │   │   ├── LoginPage.java     # Login screen page object
│   │   │   ├── HomePage.java      # Product catalog page object
│   │   │   ├── ProductPage.java   # Product detail page object
│   │   │   └── CartPage.java      # Shopping cart page object
│   │   └── utils/
│   │       ├── AppiumUtils.java        # Config properties loader
│   │       └── ExtentReportManager.java # HTML report listener
│   └── test/java/com/automation/tests/
│       ├── LoginTests.java        # TC01-TC04: Login scenarios
│       ├── NavigationTests.java   # TC05-TC07: Navigation scenarios
│       └── FeatureTests.java      # TC08-TC10: Cart & product features
├── testng.xml                     # TestNG suite (parallel config)
├── pom.xml                        # Maven dependencies & plugins
└── README.md                      # This file
```

---

## Prerequisites

Before running tests, ensure you have the following installed:

1. **Java JDK 11** — [Download](https://adoptium.net/)
2. **Apache Maven 3.x** — [Download](https://maven.apache.org/download.cgi)
3. **Android SDK** — via [Android Studio](https://developer.android.com/studio)
4. **Appium Server 2.x** — Install via npm:
   ```bash
   npm install -g appium
   ```
5. **UiAutomator2 Driver** — Install via Appium:
   ```bash
   appium driver install uiautomator2
   ```
6. **Android Emulator** (or a physical device connected via USB)

---

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

### 3. Configure Your Device
Edit `config/config.properties`:
```properties
device.name=emulator-5554       # Your emulator or device name
platform.version=12.0            # Your Android version
app.path=apps/SauceLabs-My-Demo-App.apk
appium.server.url=http://127.0.0.1:4723
```

### 4. Start Appium Server
```bash
appium
```

### 5. Start Android Emulator
Open Android Studio → Device Manager → Start your AVD  
Or via command line:
```bash
emulator -avd <your_avd_name>
```

---

## How to Run Tests Locally

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

### Run and generate Surefire HTML report:
```bash
mvn test surefire-report:report
```
Report location: `target/site/surefire-report.html`

### ExtentReport:
After running tests, the ExtentReport is automatically generated at:
```
test-output/ExtentReport.html
```

---

## Test Cases

| TC # | Test Name | Class | Description | Assertions |
|------|-----------|-------|-------------|------------|
| TC01 | `tc01_verifyLoginPageDisplayed` | LoginTests | Verify login UI elements are visible | `assertTrue` (username field, login button) |
| TC02 | `tc02_successfulLogin` | LoginTests | Login with valid credentials | `assertTrue` (home displayed), `assertEquals` (title) |
| TC03 | `tc03_invalidPasswordShowsError` | LoginTests | Wrong password shows error | `assertTrue` (error visible), `assertNotNull` (error text) |
| TC04 | `tc04_emptyCredentialsShowsError` | LoginTests | Empty fields show validation error | `assertTrue` (required error) |
| TC05 | `tc05_verifyHomePageDisplaysProducts` | NavigationTests | Catalog page loads with products | `assertEquals` (title), `assertTrue` (products visible) |
| TC06 | `tc06_navigateToProductDetail` | NavigationTests | Product detail page opens | `assertTrue` (detail displayed), `assertNotNull` (title) |
| TC07 | `tc07_logoutReturnsToHome` | NavigationTests | Logout returns to catalog | `assertTrue` (catalog displayed) |
| TC08 | `tc08_addProductToCart` | FeatureTests | Add product to cart | `assertTrue` (cart page, checkout button) |
| TC09 | `tc09_productDetailShowsInfo` | FeatureTests | Product shows title and price | `assertNotNull`, `assertTrue` (price contains $) |
| TC10 | `tc10_backFromProductDetailReturnsToCatalog` | FeatureTests | Back button returns to catalog | `assertTrue` (catalog, products visible) |

---

## CI Workflow

The project uses **GitHub Actions** for Continuous Integration. The pipeline is defined in `.github/workflows/ci.yml`.

### Pipeline Triggers:
- **Push** to `main` branch
- **Pull Request** targeting `main` branch

### Pipeline Steps:
1. **Checkout** — Clones the repository
2. **Setup Java 11** — Installs Temurin JDK 11
3. **Cache Maven** — Caches `~/.m2` for faster builds
4. **Build (compile)** — Runs `mvn compile` to verify main sources
5. **Test Compile** — Runs `mvn test-compile` to verify test sources

> **Note:** Full test execution requires a connected Android device/emulator which is not available in standard GitHub-hosted runners. The CI pipeline validates that all code compiles successfully.

---

## Git Workflow

This project follows a **Feature Branch Workflow** with Pull Requests:

1. **`main`** — Protected default branch, only merged via PRs
2. **`feature/issue-X-*`** — Feature branches for each issue/task
3. **Pull Requests** — All changes reviewed and merged through PRs
4. **Two contributors** — Collaborative development with task distribution

### Branch naming convention:
```
feature/issue-1-project-setup
feature/issue-2-base-framework
feature/issue-3-login-page
...
feature/issue-10-readme
```

---

## Bonus Features

### 1. Parallel Test Execution
Configured in `testng.xml`:
```xml
<suite parallel="tests" thread-count="3">
```
Each test group (Login, Navigation, Feature) runs in its own thread for faster execution.

### 2. Test Report Generation
- **ExtentReports** — Rich HTML report at `test-output/ExtentReport.html`
- **Maven Surefire Report** — Standard report via `mvn surefire-report:report`

---

## Team Members
- **Ameer** — Framework setup, CI pipeline, test implementation
- **Collaborator** — Page objects, test cases, documentation
