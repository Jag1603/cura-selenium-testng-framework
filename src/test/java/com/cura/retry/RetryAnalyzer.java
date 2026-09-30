package com.cura.retry;

import com.cura.utils.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int attempts = 0;
    private final int maxAttempts = ConfigReader.getInt("retryCount");

    @Override
    public boolean retry(ITestResult result) {
        if (attempts < maxAttempts) {
            attempts++;
            return true;
        }
        return false;
    }
}
