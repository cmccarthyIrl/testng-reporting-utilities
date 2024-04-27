package com.reports.utils.logging;

import com.aventstack.extentreports.Status;
import com.reports.utils.spark.SparkTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

/**
 * LogManager class for managing logging operations.
 */
public class LogManager {

  private final Logger logger;

  /**
   * Constructor to initialize LogManager with a specified class.
   * @param aClass The class for which LogManager is initialized.
   */
  public LogManager(Class<?> aClass) {
    try {
      logger = LoggerFactory.getLogger(aClass);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Logs an info message.
   * @param logEntry The object to be logged.
   */
  public void info(Object logEntry) {
    if (SparkTests.getTest() != null) {
      if (logEntry instanceof String) {
        SparkTests.getTest().log(Status.INFO, (String) logEntry);
        logger.info((String) logEntry);
      } else if (logEntry instanceof ITestResult) {
        SparkTests.getTest().log(Status.INFO, ((ITestResult) logEntry).getThrowable());
        logger.info(((ITestResult) logEntry).getThrowable().getMessage());
      }
    } else {
      logger.info((String) logEntry);
    }
  }

  /**
   * Logs a debug message.
   * @param logEntry The object to be logged.
   */
  public void debug(Object logEntry) {
    if (SparkTests.getTest() != null) {
      if (logEntry instanceof String) {
        SparkTests.getTest().log(Status.INFO, (String) logEntry);
        logger.debug((String) logEntry);
      } else if (logEntry instanceof ITestResult) {
        SparkTests.getTest().log(Status.INFO, ((ITestResult) logEntry).getThrowable());
        logger.debug(((ITestResult) logEntry).getThrowable().getMessage());
      }
    } else {
      logger.debug((String) logEntry);
    }
  }

  /**
   * Logs a warning message.
   * @param logEntry The object to be logged.
   */
  public void warn(Object logEntry) {
    if (SparkTests.getTest() != null) {
      if (logEntry instanceof String) {
        SparkTests.getTest().log(Status.INFO, (String) logEntry);
        logger.warn((String) logEntry);
      } else if (logEntry instanceof ITestResult) {
        SparkTests.getTest().log(Status.INFO, ((ITestResult) logEntry).getThrowable());
        logger.warn(((ITestResult) logEntry).getThrowable().getMessage());
      }
    } else {
      logger.warn((String) logEntry);
    }
  }

  /**
   * Logs an error message.
   * @param logEntry The object to be logged.
   */
  public void error(Object logEntry) {
    if (SparkTests.getTest() != null) {
      if (logEntry instanceof String) {
        SparkTests.getTest().log(Status.INFO, (String) logEntry);
        logger.error((String) logEntry);
      } else if (logEntry instanceof ITestResult) {
        SparkTests.getTest().log(Status.INFO, ((ITestResult) logEntry).getThrowable());
        logger.error(((ITestResult) logEntry).getThrowable().getMessage());
      }
    } else {
      logger.error((String) logEntry);
    }
  }
}
