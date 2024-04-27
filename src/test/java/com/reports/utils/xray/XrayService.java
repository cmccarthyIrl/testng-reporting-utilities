package com.reports.utils.xray;

import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

/**
 * XrayService class for interacting with the Xray API.
 */
public class XrayService {

    /**
     * Sends the test report to Jira Xray.
     */
    public void sendReportToJiraXray() {
        // Set the base URI for Xray API
        RestAssured.baseURI = "https://xray.cloud.getxray.app";

        // Define Jira credentials and project information
        String id = "{JIRA_USERNAME}";
        String secret = "{JIRA_SECRET}";
        String projectKey = "CALC";
        String testPlanKey = "CALC-1";

        // Authenticate and get the access token
        final String token = RestAssured.given()
                .headers("Content-Type", "application/json")
                .body("{\"client_id\":\"" + id + "\",\"client_secret\",\"" + secret + "\"}")
                .post("/api/v1/authenticate").then().extract().asPrettyString();

        // If token is not blank, import the test report to Xray
        if(token.isBlank()){
            final Response response = RestAssured.given().auth().oauth2(JsonParser.parseString(token).getAsString())
                    .headers("Content-Type", "application/json")
                    .body(new File(System.getProperty("user.dir") + "target/surfire-reports/testng-results.xml"))
                    .queryParam(projectKey, projectKey)
                    .queryParam("testPlanKey", testPlanKey)
                    .post("/api/v1/import/testng");

            response.prettyPrint();

        } else {
            // Handle the case when token is blank
            // Do something
        }
    }

}