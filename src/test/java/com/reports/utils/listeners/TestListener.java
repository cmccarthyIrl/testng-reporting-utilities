package com.reports.utils.listeners;

import com.aventstack.extentreports.Status;
import com.reports.utils.logging.LogManager;
import com.reports.utils.spark.SparkReporter;
import com.reports.utils.spark.SparkTests;
import com.reports.utils.xray.XrayService;
import org.testng.*;

/**
 * TestListener class for handling TestNG test events.
 */
public class TestListener implements ITestListener, ISuiteListener {
    private static final LogManager logger = new LogManager(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("============= Starting test: " + result.getName() + " =============");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        updateExtentTest(result, Status.PASS);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        updateExtentTest(result, Status.FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        updateExtentTest(result, Status.SKIP);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onFinish(ISuite suite) {
        SparkReporter.extent.flush();
        XrayService xrayService = new XrayService();
        xrayService.sendReportToJiraXray();
    }

    /**
     * Updates the extent test based on the test result.
     * @param iTestResult The TestNG result.
     * @param status The status of the test.
     */
    private void updateExtentTest(ITestResult iTestResult, Status status) {
        if (SparkTests.getTest() != null) {

            // Condition to handle special cases of test status
            if (iTestResult.getThrowable() != null) {
                if (status == Status.SKIP &&
                        (iTestResult.getThrowable().getClass() == SkipException.class || iTestResult.getThrowable().getClass() == AssertionError.class)
                        && iTestResult.getTestContext().getFailedTests().getResults(iTestResult.getMethod()).isEmpty()
                        && iTestResult.getTestContext().getSkippedTests().getResults(iTestResult.getMethod()).isEmpty()) {
//                    logger.warn(iTestResult);
                    logger.warn("============= Skipped test: " + iTestResult.getName() + " ==============");
                    SparkTests.getTest().log(Status.INFO, "============= Skipped test: " + iTestResult.getName() + " ==============");
                } else {
//                    logger.error(iTestResult);
                    logger.error("============= Failed test: " + iTestResult.getName() + " ==============");
                    SparkTests.getTest().fail("============= Failed test: " + iTestResult.getName() + " ==============");
                }
            } else {
                logger.info("============= Passed test: " + iTestResult.getName() + " ==============");
                SparkTests.getTest().pass("============= Passed test: " + iTestResult.getName() + " ==============");
            }
        }
    }
}