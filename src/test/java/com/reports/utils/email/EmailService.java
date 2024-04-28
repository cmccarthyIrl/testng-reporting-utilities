/*
 * This class handles sending emails with test execution results.
 */

package com.reports.utils.email;

import com.reports.utils.logging.LogManager;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

public class EmailService {
    private static final LogManager logger = new LogManager(EmailService.class);

    // Method to send email
    public void sendEmail() {
        logger.debug("Sending the report by Email");
        try {
            // Email addresses
            String to = "email1@gmail.com";
            String from = "email2@gmail.com";
            String cc1 = "email3@gmail.com";
            String host = "YOUR HOST";

            // Email properties
            Properties prop = new Properties();
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.port", "25");

            // HTML template for the email body
            String template2 = "<!DOCTYPE html> <html lang=\"en\"> <head> <style> body { font-family: \"lato\", sans-serif; } .container { max-width: 600px; margin-left: auto; margin-right: auto; padding-right: 10px; } h2 { font-size: 26px; margin: 20px 0; text-align: center; color: #E01921; }  </style> <title>Email title</title> </head> <body> <div class=\"container\"><h2>Test Results</h2> <div><p> Hi, <br><br> I hope you're having a good day!, please find attached the test execution results</p> </div> </div> </body> </html>";

            // Email session
            Session session = Session.getDefaultInstance(prop);
            // Creating MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setHeader("X-Priority", "1");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc1));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(from));
            message.setSubject("Subject Title");

            // Create a multipart message
            MimeMultipart multipart = new MimeMultipart();

            // Create the HTML content part
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(template2, "text/html");
            multipart.addBodyPart(htmlPart);

            // Add attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(System.getProperty("user.dir") + "/target/spark-reports/spark-report.html");
            multipart.addBodyPart(attachmentPart);

            // Set the multipart as the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            logger.info("Email message successfully sent....");
        } catch (Exception exception) {
            logger.error("Something went wrong sending the email\n" + exception.getMessage());
        }
    }
}