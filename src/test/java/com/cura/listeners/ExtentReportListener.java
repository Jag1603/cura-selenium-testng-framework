package com.cura.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        createReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    @Override
    public void onStart(ITestContext context) {
        createReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (extentReports == null) {
            createReport();
        }

        ExtentTest test = extentReports.createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
        test.assignCategory(result.getInstanceName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (testThread.get() != null) {
            testThread.get().log(Status.PASS, "Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (testThread.get() != null) {
            ExtentTest test = testThread.get();
            test.log(Status.FAIL, "Test failed");

            if (result.getThrowable() != null) {
                test.log(Status.FAIL, result.getThrowable().getMessage());
            }

            String screenshotBase64 = captureScreenshot(result);
            if (screenshotBase64 != null) {
                test.addScreenCaptureFromBase64String(screenshotBase64, "Failure Screenshot");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (testThread.get() != null) {
            testThread.get().log(Status.SKIP, "Test skipped");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        if (testThread.get() != null) {
            testThread.get().log(Status.WARNING, "Test failed but within success percentage");
        }
    }

    private static void createReport() {
        if (extentReports != null) {
            return;
        }

        Path reportDirectory = Paths.get("reports");
        try {
            Files.createDirectories(reportDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create report directory", e);
        }

        Path reportPath = reportDirectory.resolve("extent-report.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath.toFile());
        sparkReporter.config().setDocumentTitle("CURA Test Automation Report");
        sparkReporter.config().setReportName("CURA Regression Suite");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    private String captureScreenshot(ITestResult result) {
        Object instance = result.getInstance();

        if (instance instanceof com.cura.base.BaseTest base) {
            WebDriver driver = base.getDriver();
            if (driver instanceof TakesScreenshot takesScreenshot) {
                return takesScreenshot.getScreenshotAs(OutputType.BASE64);
            }
        }

        return null;
    }
}
