package com.reports.test;

import com.reports.utils.listeners.TestListener;
import com.reports.utils.listeners.XrayListener;
import com.reports.utils.logging.LogManager;
import com.reports.utils.spark.SparkTests;
import org.slf4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({TestListener.class, XrayListener.class})
public class BaseTest {

    LogManager logger = new LogManager(BaseTest.class);

    @BeforeSuite
    public void beforeSuite(ITestResult iTestResult) {
        logger.debug("Started: beforeSuite");
        MDC.put("logFileName", iTestResult.getName());
        logger.debug("Finished: beforeSuite");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestResult iTestResult, Method method) {
        logger.debug("Started: beforeMethod");
        SparkTests.createTest(iTestResult);
        MDC.put("logFileName", method.getName());
        logger.debug("Finished: beforeMethod");
    }

    @AfterTest(alwaysRun = true)
    public void afterTest(ITestContext context) {
        logger.debug("Started: afterTest");
        MDC.remove(context.getName());
        logger.debug("Finished: afterTest");
    }
}