package com.reports.utils.slack;

import com.google.gson.Gson;
import com.reports.utils.Utility;
import com.reports.utils.logging.LogManager;
import com.reports.utils.slack.models.BlockBaseModel;
import com.reports.utils.slack.models.Elements;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.files.FilesUploadResponse;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlackService {

    private final LogManager logger = new LogManager(SlackService.class);

    // Slack token and channel ID variables
    private final String token = "1243243225";
    private final String slackChannel = "343w453dds";

    // Slack client instance
    private final Slack slack = Slack.getInstance();

    // Utility instance to retrieve XML result and current date
    private final Utility utility = new Utility();

    // Result of XML parsing
    final String result = utility.getXMLResult();

    public SlackService() throws IOException, ParserConfigurationException, SAXException {
    }

    // Method to send the Slack report
    public void sendSlackReport() {
        logger.debug("Sending the report to Slack");
        try {
            ChatPostMessageResponse postResultsResponse;
            postResultsResponse = postTestExecutions();
            postTestReports(postResultsResponse);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("Something went wrong sending the message to Slack\n" + exception.getMessage());
        }
    }

    // Method to post test executions to Slack
    private ChatPostMessageResponse postTestExecutions() throws SlackApiException, IOException {
        BlockBaseModel[] blockArray = new BlockBaseModel[1];
        BlockBaseModel blockContent = getBaseModel();
        blockArray[0] = blockContent;

        // Sending message to Slack channel
        ChatPostMessageResponse postResultsResponse = slack.methods(token)
                .chatPostMessage(req -> req.channel(slackChannel).blocksAsString((new Gson()).toJson(blockArray)));

        // Handling response
        if (!postResultsResponse.isOk()) {
            logger.error("Something went wrong sending the message to Slack");
            logger.error(postResultsResponse.toString());
        } else {
            logger.info("Successfully sent the message to Slack");
        }
        return postResultsResponse;
    }

    // Method to retrieve block element
    @NotNull
    private BlockBaseModel getBaseModel() {
        BlockBaseModel blockContent = new BlockBaseModel();
        blockContent.setType("context");
        List<Elements> elementsList = new ArrayList<>();
        Elements imageElement = getElements(); // Retrieve image element based on test result
        elementsList.add(imageElement);
        Elements timeStampElement = new Elements();
        timeStampElement.setType("mrkdwn");
        timeStampElement.setText("*When:* " + utility.getDate()); // Adding timestamp element
        elementsList.add(timeStampElement);
        blockContent.setElements(elementsList);
        return blockContent;
    }

    // Method to retrieve image element based on test result
    @NotNull
    private Elements getElements() {
        Elements imageElement = new Elements();
        imageElement.setType("image");
        if (result.equals("passed")) {
            imageElement.setImage_url("https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/Eo_circle_green_blank.svg/480px-Eo_circle_green_blank.svg.png");
            imageElement.setAlt_text("passed");
        } else {
            imageElement.setImage_url("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Eo_circle_red_blank.svg/480px-Eo_circle_red_blank.svg.png");
            imageElement.setAlt_text("failed");
        }
        return imageElement;
    }

    // Method to post test reports to Slack
    private void postTestReports(ChatPostMessageResponse postResultsResponse) {
        try {
            // Path to the test report HTML file
            String path = System.getProperty("user.dir") + "/target/spark-reports/spark-report.html";
            // Uploading file to Slack channel
            FilesUploadResponse replyWithFile = slack.methods(token)
                    .filesUpload(req -> req.channels(Collections.singletonList(slackChannel))
                            .threadTs(postResultsResponse.getTs())
                            .filename("Test Report: " + utility.getDate() + ".html")
                            .filetype("html")
                            .file(new File(path))
                            .filetype("html"));

            // Handling response
            if (!replyWithFile.isOk()) {
                logger.error("Something went wrong sending the Test Reports to Slack");
                logger.error(replyWithFile.toString());
            } else {
                logger.info("Successfully sent the Test Reports to Slack");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
