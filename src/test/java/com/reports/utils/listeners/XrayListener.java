package com.reports.utils.listeners;

import com.reports.utils.annotations.Xray;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * XrayListener class for handling TestNG method invocation events with Xray annotations.
 */
public class XrayListener implements IInvokedMethodListener {

    final boolean testSuccess = true;

    /**
     * Executed before a test method invocation.
     *
     * @param method     The method being invoked.
     * @param testResult The result of the test method invocation.
     */
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && annotationPresent(method)) {
            testResult.setAttribute("requirement", method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Xray.class).requirement());
            testResult.setAttribute("test", method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Xray.class).test());
            testResult.setAttribute("labels", method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Xray.class).labels());
        }
    }

    /**
     * Checks if Xray annotation is present on the method.
     *
     * @param method The method to be checked.
     * @return True if Xray annotation is present, otherwise false.
     */
    private boolean annotationPresent(IInvokedMethod method) {
        return method.getTestMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Xray.class);
    }

    /**
     * Executed after a test method invocation.
     *
     * @param method     The method being invoked.
     * @param testResult The result of the test method invocation.
     */
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (!testSuccess) {
                testResult.setStatus(ITestResult.FAILURE);
            }
        }
    }
}