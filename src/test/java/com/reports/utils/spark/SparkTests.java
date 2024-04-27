package com.reports.utils.spark;

import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Optional;

public class SparkTests {

    // ThreadLocal variables to manage method-level and data provider tests
    private static final ThreadLocal<ExtentTest> testThread = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<ExtentTest> dataProviderThread = ThreadLocal.withInitial(() -> null);

    // Get the current test being executed
    public static synchronized ExtentTest getTest() {
        return Optional.ofNullable(dataProviderThread.get()).orElse(testThread.get());
    }

    // Create a test
    public static synchronized void createTest(ITestResult result) {
        ExtentTest extentTest = getOrCreateTest(result);
        createTestNode(result);
    }

    // Get or create a test node
    private static synchronized ExtentTest getOrCreateTest(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if (testThread.get() != null && testThread.get().getModel().getName().equals(methodName)) {
            return testThread.get();
        } else {
            ExtentTest extentTest = SparkReporter.extent.createTest(methodName, result.getMethod().getDescription());
            testThread.set(extentTest);
            assignGroups(extentTest, result.getMethod().getGroups());
            return extentTest;
        }
    }

    // Create a parameter node
    private static synchronized void createTestNode(ITestResult result) {
        if (result.getParameters().length > 0) {
            String paramName = Arrays.asList(result.getParameters()).toString();
            ExtentTest paramTest = testThread.get().createNode(paramName);
            dataProviderThread.set(paramTest);
        } else {
            dataProviderThread.set(null);
        }
    }

    // Assign groups to the test
    private static void assignGroups(ExtentTest test, String[] groups) {
        // Assign categories based on group names
        Arrays.stream(groups).map(SparkTests::getCategory).forEach(test::assignCategory);
    }

    // Extract category from group name
    private static String getCategory(String group) {
        return group.startsWith("d:") || group.startsWith("device:") ? group.replace("d:", "").replace("device:", "") :
                group.startsWith("a:") || group.startsWith("author:") ? group.replace("a:", "").replace("author:", "") :
                        group.startsWith("t:") || group.startsWith("tag:") ? group.replace("t:", "").replace("tag:", "") :
                                group;
    }
}