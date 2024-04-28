package com.reports.test;

import com.reports.utils.RetryAnalyser;
import com.reports.utils.annotations.Action;
import com.reports.utils.annotations.Step;
import com.reports.utils.annotations.Xray;
import com.reports.utils.dataprovider.SparkDP;
import com.reports.utils.logging.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Tests extends BaseTest {

    LogManager logger = new LogManager(Tests.class);
    private final String nestedElement = "Hello from the nested method";

    @Test(groups = {"Pass", "Spark"}, description = "Pass test description")
    @Xray(requirement = "", test = "CALC-1231", labels = "JIRA_LABEL")
    public void pass() {
        logger.info("Pass this test");
        actionExample();
        stepExample();
        annotatedStep("Annotated from the pass test");
        Assert.assertTrue(true);
    }

    private static int count = 0;

    @Test(retryAnalyzer = RetryAnalyser.class, groups = {"Retry", "Spark"}, description = "Retry test description")
    @Xray(requirement = "", test = "CALC-1232", labels = "JIRA_LABEL")
    public void retry() {
        logger.warn("Retry this test");
        logger.debug("The retry count is: " + count);
        actionExample();
        stepExample();
        annotatedStep("Annotated from the retry test");
        if (count == 0) {
            count = 1;
            Assert.fail();
        } else {
            Assert.assertTrue(true);
        }
    }

    @Test(dataProvider = "numbers", dataProviderClass = SparkDP.class, groups = {"DataProvider",
            "Spark"}, description = "Data provider test description")
    @Xray(requirement = "", test = "CALC-1233", labels = "JIRA_LABEL")
    public void dpTest(int number) {
        logger.info("Pass this test");
        actionExample();
        stepExample();
        annotatedStep("Annotated from the dp test");
        Assert.assertTrue(true);
    }

    @Test(groups = {"Fail", "Spark"}, description = "Fail test description")
    @Xray(requirement = "", test = "CALC-1234", labels = "JIRA_LABEL")
    public void fail() {
        logger.error("Fail this test");
        actionExample();
        stepExample();
        annotatedStep("Annotated from the fail test");
        Assert.fail();
    }

    @Action("This is an example of an aspect log")
    private void actionExample() {
        //do something
    }

    @Step("This is an example of an aspect log")
    private void stepExample() {
        //do something
    }

    @io.qameta.allure.Step("Parent annotated step with parameter [{parameter}]")
    public void annotatedStep(final String parameter) {
        nestedAnnotatedStep();
    }

    @io.qameta.allure.Step("Nested annotated step with global parameter [{this.nestedElement}]")
    public void nestedAnnotatedStep() {
    }
}