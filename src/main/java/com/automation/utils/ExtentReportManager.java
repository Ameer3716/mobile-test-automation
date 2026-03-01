package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * ExtentReports Test Listener for generating HTML test reports.
 * Implements TestNG ITestListener to capture test execution events.
 * 
 * Reports are generated at: test-output/ExtentReport.html
 * 
 * Bonus Feature: Automated HTML test report generation.
 */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Mobile Automation Test Report");
        spark.config().setReportName("SauceLabs My Demo App - Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("App", "SauceLabs My Demo App");
        extent.setSystemInfo("Platform", "Android");
        extent.setSystemInfo("Framework", "Appium + TestNG");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Test FAILED: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
