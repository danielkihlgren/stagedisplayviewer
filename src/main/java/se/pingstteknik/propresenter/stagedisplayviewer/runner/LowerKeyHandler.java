package se.pingstteknik.propresenter.stagedisplayviewer.runner;

import javafx.scene.text.Text;
import se.pingstteknik.propresenter.stagedisplayviewer.Main;
import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;
import se.pingstteknik.propresenter.stagedisplayviewer.util.FxTextUtils;
import se.pingstteknik.propresenter.stagedisplayviewer.util.XmlDataReader;
import se.pingstteknik.propresenter.stagedisplayviewer.util.TextTranslator;
import se.pingstteknik.propresenter.stagedisplayviewer.util.XmlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static se.pingstteknik.propresenter.stagedisplayviewer.config.Property.HOST;
import static se.pingstteknik.propresenter.stagedisplayviewer.config.Property.PORT;
import static se.pingstteknik.propresenter.stagedisplayviewer.util.ThreadUtil.sleep;

public class LowerKeyHandler implements Runnable {

    private static final TextTranslator textTranslator = new TextTranslator();
    private static final FxTextUtils  fxTextUtils = new FxTextUtils();
    private static final XmlDataReader xmlDataReader = new XmlDataReader();
    private static final XmlParser xmlParser = new XmlParser();

    private volatile boolean running = true;
    private final Text lowerKey;

    public LowerKeyHandler(Text lowerKey) throws IOException {
        this.lowerKey = lowerKey;
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
                        updateLowerKey(in);
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

    private void updateLowerKey(BufferedReader in) throws IOException {
        String sceneRawData = xmlDataReader.readXmlData(in);
        String parsedText = xmlParser.parse(sceneRawData).getData("CurrentSlide");

        System.out.println("RAW XML:     " + sceneRawData);
        System.out.println("Parsed text: " + parsedText);
        lowerKey.setText(" ");

        if (!parsedText.isEmpty()) {
            String currentSlideText = textTranslator.transformSceneText(parsedText);

            lowerKey.setFont(fxTextUtils.getOptimizedFont(currentSlideText, lowerKey.getWrappingWidth()));
            lowerKey.setText(currentSlideText);
            System.out.println("Processed text:\n" + currentSlideText);
        }
    }

    private String getLoginString() {
        return "<StageDisplayLogin>" + Property.PASSWORD + "</StageDisplayLogin>\n\r";
    }
}
