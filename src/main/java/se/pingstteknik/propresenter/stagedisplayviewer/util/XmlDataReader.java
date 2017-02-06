package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.0.0
 */
public class XmlDataReader {

    private static final String XML_START_TAG = "<StageDisplayData>";
    private static final String XML_END_TAG = "</StageDisplayData>";
    private static final String NEWLINE = "\n";

    public String readXmlData(BufferedReader in) throws IOException {
        StringBuilder readXmlData = new StringBuilder(256);
        boolean readCompleted = false;
        while (!readCompleted) {
            String inputLine = in.readLine();
            if (inputLine == null) {
                return null;
            }
            System.out.println(inputLine);
            if (inputLine.contains(XML_START_TAG)) {
                readXmlData.setLength(0);
            }

            readXmlData.append(inputLine);
            if (inputLine.contains(XML_END_TAG)) {
                readCompleted = true;
            } else {
                readXmlData.append(NEWLINE);
            }
        }
        return readXmlData.toString();
    }

}
