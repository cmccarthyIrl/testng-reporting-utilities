package com.reports.utils.dataprovider;

import org.testng.annotations.DataProvider;

public class SparkDP {

    @DataProvider(name = "numbers")
    public static Object[][] getNumbers() {
        return new Object[][]{{1}, {2}, {3}};
    }
}