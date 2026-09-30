package com.cura.driver;
import java.util.Map;
import com.cura.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
    private DriverFactory() {}

    public static WebDriver createDriver(String browser, boolean headless) {

    if (browser == null || browser.isBlank()) {
        browser = "chrome";
    }

    browser = browser.trim().toLowerCase();

    switch (browser) {

        case "chrome":

    WebDriverManager.chromedriver().setup();

    ChromeOptions chromeOptions = new ChromeOptions();

    if (headless) {
        chromeOptions.addArguments("--headless=new");
    }

    chromeOptions.addArguments("--start-maximized");

    // Test automation settings
    chromeOptions.addArguments("--disable-notifications");

    // Prevent Chrome password manager prompts
    chromeOptions.setExperimentalOption(
            "prefs",
            Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_leak_detection", false,
                    "profile.password_manager_enabled", false
            )
    );

    return new ChromeDriver(chromeOptions);

        case "firefox":
            WebDriverManager.firefoxdriver().setup();

            FirefoxOptions firefoxOptions = new FirefoxOptions();

            if (headless) {
                firefoxOptions.addArguments("-headless");
            }

            return new FirefoxDriver(firefoxOptions);

        default:
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser +
                    ". Supported browsers: chrome, firefox"
            );
    }
}
}
