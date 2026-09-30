package com.cura.base;

import com.cura.driver.DriverFactory;
import com.cura.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(
            @Optional("chrome") String browser,
            @Optional("false") String headless) {

        // Maven system property takes priority
        String selectedBrowser = System.getProperty(
                "browser",
                browser
        );

        String selectedHeadless = System.getProperty(
                "headless",
                headless
        );

        // Final fallback
        if (selectedBrowser == null || selectedBrowser.isBlank()) {
            selectedBrowser = ConfigReader.get("browser");

            if (selectedBrowser == null || selectedBrowser.isBlank()) {
                selectedBrowser = "chrome";
            }
        }

        if (selectedHeadless == null || selectedHeadless.isBlank()) {
            selectedHeadless = ConfigReader.get("headless");

            if (selectedHeadless == null || selectedHeadless.isBlank()) {
                selectedHeadless = "false";
            }
        }

        System.out.println("========================================");
        System.out.println("Browser   : " + selectedBrowser);
        System.out.println("Headless  : " + selectedHeadless);
        System.out.println("========================================");

        driver = DriverFactory.createDriver(
                selectedBrowser,
                Boolean.parseBoolean(selectedHeadless)
        );

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(
                java.time.Duration.ofSeconds(30)
        );

        driver.get(ConfigReader.get("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}