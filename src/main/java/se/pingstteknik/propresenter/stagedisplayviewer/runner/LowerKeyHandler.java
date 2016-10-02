package se.pingstteknik.propresenter.stagedisplayviewer.runner;

import javafx.application.Platform;
import javafx.scene.text.Text;
import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;
import se.pingstteknik.propresenter.stagedisplayviewer.model.StageDisplay;
import se.pingstteknik.propresenter.stagedisplayviewer.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static se.pingstteknik.propresenter.stagedisplayviewer.config.Property.*;
import static se.pingstteknik.propresenter.stagedisplayviewer.util.ThreadUtil.sleep;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.0.0
 */
public class LowerKeyHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LowerKeyHandler.class);
    private static final ConcatenateRowsTranslator concatenateRowsTranslator = new ConcatenateRowsTranslator(PRESERVE_TWO_LINES.isTrue());
    private static final RemoveLinesAfterEmptyLineTranslator removeLinesAfterEmptyLineTranslator = new RemoveLinesAfterEmptyLineTranslator();
    private static final FxTextUtils  fxTextUtils = new FxTextUtils();
    private static final XmlDataReader xmlDataReader = new XmlDataReader();
    private static final XmlParser xmlParser = new XmlParser();
    private static final String SUCCESSFUL_LOGIN = "<StageDisplayLoginSuccess />";
    private static final String SUCCESSFUL_LOGIN_WINDOWS = "<StageDisplayLoginSuccess>";

    private volatile boolean running = true;
    private final Text lowerKey;
    private final MidiModule midiModule;

    public LowerKeyHandler(Text lowerKey, MidiModule midiModule) throws IOException {
        this.lowerKey = lowerKey;
        this.midiModule = midiModule;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(HOST.toString(), PORT.toInt())) {
            try (
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))
            ) {

                log.info("Connection to propresenter established at " + HOST.toString() + ":" + PORT.toString());
                out.println(getLoginString());

                String loginResponse = in.readLine();
                if (SUCCESSFUL_LOGIN.equals(loginResponse) || SUCCESSFUL_LOGIN_WINDOWS.equals(loginResponse)) {
                    log.info("Login succeeded");
                } else {
                    log.error("Login failed with incorrect password: " + PASSWORD.toString() + ", with response: " + loginResponse);
                    running = false;
                }

                while (running && socket.isConnected()) {
                    if (in.ready()) {
                        update(in);
                    }
                    sleep();
                }
            }
        } catch (IOException e) {
            log.error("Connection to propresenter failed at " + HOST.toString() + ":" + PORT.toString(), e);
        }
        log.info("Closing program");
        Platform.exit();
    }

    public void terminate() {
        running = false;
    }

    private void update(BufferedReader in) throws IOException {
        String xmlRawData = xmlDataReader.readXmlData(in);
        StageDisplay stageDisplay = xmlParser.parse(xmlRawData);
        String slide = stageDisplay.getData("CurrentSlide");
        String slideNotes = stageDisplay.getData("CurrentSlideNotes");

        log.info("RAW XML: {}", xmlRawData);
        log.debug("Slide notes: {}", slideNotes);
        log.debug("Slide text unparsed: {}", slide);

        if (!slide.isEmpty()) {
            String slideText = REMOVE_LINES_AFTER_EMPTY_LINE.isTrue()
                    ? removeLinesAfterEmptyLineTranslator.transform(slide) : slide;
            slideText = TEXT_TRANSLATOR_ACTIVE.isTrue()
                    ? concatenateRowsTranslator.transformSceneText(slideText) : slideText;
            lowerKey.setFont(fxTextUtils.getOptimizedFont(slideText, lowerKey.getWrappingWidth()));
            lowerKey.setText(slideText);
            log.debug("Slide text parsed: {}", slideText);
        } else {
            lowerKey.setText(" ");
        }
        midiModule.handleMessage(slideNotes);
    }

    private String getLoginString() {
        return "<StageDisplayLogin>" + Property.PASSWORD + "</StageDisplayLogin>\n\r";
    }
}
