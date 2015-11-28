package se.pingstteknik.propresenter.stagedisplayviewer.runner;

import javafx.scene.text.Text;
import se.pingstteknik.propresenter.stagedisplayviewer.Main;
import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;
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

    private static final TextTranslator textTranslator = new TextTranslator();
    private static final RemoveLinesAfterEmptyLineTranslator removeLinesAfterEmptyLineTranslator = new RemoveLinesAfterEmptyLineTranslator();
    private static final FxTextUtils  fxTextUtils = new FxTextUtils();
    private static final XmlDataReader xmlDataReader = new XmlDataReader();
    private static final XmlParser xmlParser = new XmlParser();

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
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {

                out.println(getLoginString());

                while (running && socket.isConnected()) {
                    if (in.ready()) {
                        update(in);
                    }
                    sleep();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.closeApp();
    }

    public void terminate(Thread thread) {
        running = false;
        thread.run();
    }

    private void update(BufferedReader in) throws IOException {
        String sceneRawData = xmlDataReader.readXmlData(in);
        String parsedText = xmlParser.parse(sceneRawData).getData("CurrentSlide");
        String slideNotes = xmlParser.parse(sceneRawData).getData("CurrentSlideNotes");

        System.out.println("RAW XML:     " + sceneRawData);
        System.out.println("Parsed text: " + parsedText);
        System.out.println("Slide notes: " + slideNotes);
        lowerKey.setText(" ");

        if (!parsedText.isEmpty()) {
            String currentSlideText = REMOVE_LINES_AFTER_EMPTY_LINE.isTrue()
                    ? removeLinesAfterEmptyLineTranslator.transform(parsedText) : parsedText;
            currentSlideText = TEXT_TRANSLATOR_ACTIVE.isTrue()
                    ? textTranslator.transformSceneText(currentSlideText) : currentSlideText;
            lowerKey.setFont(fxTextUtils.getOptimizedFont(currentSlideText, lowerKey.getWrappingWidth()));
            lowerKey.setText(currentSlideText);
            System.out.println("Processed text:\n" + currentSlideText);
        }
        midiModule.handleMessage(slideNotes);
    }

    private String getLoginString() {
        return "<StageDisplayLogin>" + Property.PASSWORD + "</StageDisplayLogin>\n\r";
    }
}
