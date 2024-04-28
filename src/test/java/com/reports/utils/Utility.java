package com.reports.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class Utility {

    public String getDate() {
        DateFormat date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        cals.add(10, 11);
        return date.format(cals.getTime());
    }

    public String getXMLResult() throws IOException, SAXException, ParserConfigurationException {
        String path = System.getProperty("user.dir") + "/target/surefire-reports/testng-results.xml";
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        int failedResult = Integer.parseInt(doc.getDocumentElement().getAttribute("failed"));
        if (failedResult > 0) {
            return "failed";
        }
        return "passed";
    }
}
