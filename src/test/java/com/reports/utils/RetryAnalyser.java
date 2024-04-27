package com.reports.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyser implements IRetryAnalyzer {

  private int retryCount = 0;

  @Override
  public boolean retry(ITestResult result) {
    if (!result.isSuccess()) {
      if (retryCount < 1) {
        retryCount++;
        return true;
      } else {
        retryCount = 0;
        return false;
      }
    }
    return false;
  }
}
