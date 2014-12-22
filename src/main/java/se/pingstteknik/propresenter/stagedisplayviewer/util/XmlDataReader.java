package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.io.BufferedReader;
import java.io.IOException;


public class XmlDataReader {

    private static final String XML_START_TAG = "<StageDisplayData>";
    private static final String XML_END_TAG = "</StageDisplayData>";
    private static final String NEWLINE = "\n";

    public String readXmlData(BufferedReader in) throws IOException {
        StringBuilder readXmlData = new StringBuilder(256);
        boolean readCompleted = false;
        while (!readCompleted) {
            String x = in.readLine();
            System.out.println(x);
            if (x.contains(XML_START_TAG)) {
                readXmlData.setLength(0);
            }

            readXmlData.append(x);
            if (x.contains(XML_END_TAG)) {
                readCompleted = true;
            } else {
                readXmlData.append(NEWLINE);
            }
        }
        return readXmlData.toString();
    }

}
