package com.reports.utils.spark;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.apache.commons.lang3.SystemUtils;

/**
 * SparkReporter class for managing ExtentReports with Spark.
 */
public class SparkReporter {

  /**
   * The ExtentReports instance.
   */
  public static final ExtentReports extent = GetReporter.extent;

  private static class GetReporter {

    /**
     * The ExtentReports instance with Spark configuration.
     */
    public static final ExtentReports extent = new ExtentReports();

    /**
     * The ExtentSparkReporter instance.
     */
    private static final ExtentSparkReporter spark = new ExtentSparkReporter("target/spark-reports/spark-report.html");

    static {
      spark.config().setTheme(Theme.STANDARD);
      spark.config().setReportName("Example Report");
      spark.config().setDocumentTitle("Example Report");
      spark.config().setEncoding("UTF-8");
      spark.config().setTimelineEnabled(true);
      spark.config().enableOfflineMode(false);
      spark.config().thumbnailForBase64(true);
      spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
      spark.viewConfigurer().viewOrder().as(new ViewName[] {ViewName.DASHBOARD, ViewName.TEST}).apply();
      extent.attachReporter(spark);
      extent.setAnalysisStrategy(AnalysisStrategy.TEST);
      extent.setSystemInfo("Operating System", getOperatingSystem());
    }
  }

  /**
   * Get the operating system information.
   * @return The operating system name.
   */
  public static String getOperatingSystem() {
    if (SystemUtils.IS_OS_WINDOWS) {
      return "windows";
    } else if (SystemUtils.IS_OS_LINUX) {
      return "linux";
    } else {
      return "mac";
    }
  }
}