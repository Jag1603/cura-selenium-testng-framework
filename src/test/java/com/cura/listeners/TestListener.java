package com.cura.listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {
    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("START: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("PASS: {}", result.getMethod().getMethodName());
    }

    @Override
public void onTestFailure(ITestResult result) {

    log.error(
        "FAIL: {}",
        result.getMethod().getMethodName(),
        result.getThrowable()
    );

    Object instance = result.getInstance();

    if (instance instanceof com.cura.base.BaseTest base) {

        WebDriver driver = base.getDriver();

        if (driver != null) {

            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Failure Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );

            Allure.addAttachment(
                    "Page Source",
                    "text/html",
                    driver.getPageSource(),
                    ".html"
            );
        }
    }
}
}
