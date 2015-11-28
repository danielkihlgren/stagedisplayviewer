package se.pingstteknik.propresenter.stagedisplayviewer.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import se.pingstteknik.propresenter.stagedisplayviewer.model.Field;
import se.pingstteknik.propresenter.stagedisplayviewer.model.StageDisplay;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.0.0
 */
public class XmlParser {

    private static final Logger log = LoggerFactory.getLogger(XmlParser.class);
    private static final String FIELD_XML_ELEMENT = "Field";
    private static final String IDENTIFIER_XML_ATTRIBUTE = "identifier";

    private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();

    public StageDisplay parse(String xml) {
        try {
            NodeList list = getFieldElements(xml);
            StageDisplay.Builder builder = StageDisplay.newStageDisplayBuilder();

            for (int i=0; i< list.getLength(); i++) {
                Node item = list.item(i);
                builder.addField(new Field(getIdentifier(item), item.getTextContent()));
            }
            return builder.build();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.warn("Parse error {}", e);
        }

        return null;
    }

    private String getIdentifier(Node item) {
        return item.getAttributes().getNamedItem(IDENTIFIER_XML_ATTRIBUTE).getTextContent();
    }

    private NodeList getFieldElements(String xml) throws SAXException, IOException, ParserConfigurationException {
        return FACTORY.newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getElementsByTagName(FIELD_XML_ELEMENT);
    }
}
