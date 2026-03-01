package com.automation.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.automation.utils.AppiumUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    // This driver will be used by all test classes
    protected AndroidDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        // Read from config.properties
        String appiumUrl   = AppiumUtils.getProperty("appium.server.url");
        String platformName = AppiumUtils.getProperty("platform.name");
        String deviceName   = AppiumUtils.getProperty("device.name");
        String appPath      = AppiumUtils.getProperty("app.path");
        String automationName = AppiumUtils.getProperty("automation.name");

        // Docker support: override Appium URL from environment variables
        String appiumHost = System.getenv("APPIUM_HOST");
        String appiumPort = System.getenv("APPIUM_PORT");
        if (appiumHost != null && !appiumHost.trim().isEmpty()) {
            int appiumPortNumber = 4723;
            if (appiumPort != null && !appiumPort.trim().isEmpty()) {
                try {
                    appiumPortNumber = Integer.parseInt(appiumPort.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid APPIUM_PORT '" + appiumPort + "', defaulting to 4723");
                }
            }
            appiumUrl = "http://" + appiumHost.trim() + ":" + appiumPortNumber;
            System.out.println("Using Docker Appium URL: " + appiumUrl);
        }

        // Configure desired capabilities
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(platformName);
        options.setDeviceName(deviceName);
        options.setApp(System.getProperty("user.dir") + "/" + appPath);
        options.setAutomationName(automationName);
        options.setNoReset(false);

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
}
