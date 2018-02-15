package se.pingstteknik.propresenter.stagedisplayviewer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;
import se.pingstteknik.propresenter.stagedisplayviewer.eventhandler.SceneKeyTypedHandler;
import se.pingstteknik.propresenter.stagedisplayviewer.runner.LowerKeyHandler;
import se.pingstteknik.propresenter.stagedisplayviewer.util.FxUtils;
import se.pingstteknik.propresenter.stagedisplayviewer.util.Logger;
import se.pingstteknik.propresenter.stagedisplayviewer.util.LoggerFactory;
import se.pingstteknik.propresenter.stagedisplayviewer.util.MidiModule;

import java.io.IOException;

/**
 * @author Daniel Kihlgren
 * @version 1.6.0
 * @since 1.0.0
 */
public class Main extends Application {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String PROGRAM_TITLE = "Stage display Lower Key viewer";
    private static LowerKeyHandler lowerKeyHandler;
    private static Thread thread;
    private MidiModule midiModule;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        log.info("Starting program");
        final FxUtils fxUtils = new FxUtils();

        Property.loadProperties();

        Text lowerKey = fxUtils.createLowerKey();
        midiModule = new MidiModule();
        lowerKeyHandler = new LowerKeyHandler(lowerKey, midiModule);
        thread = new Thread(lowerKeyHandler);

        primaryStage.setTitle(PROGRAM_TITLE);
        Scene scene = fxUtils.createScene(lowerKey);
        scene.setOnKeyTyped(new SceneKeyTypedHandler(primaryStage));
        primaryStage.setScene(scene);
        fxUtils.startOnCorrectScreen(primaryStage);
        primaryStage.setOnCloseRequest(getEventHandler());
        primaryStage.setFullScreen(Property.START_IN_FULLSCREEN.isTrue());
        primaryStage.show();
        thread.start();
    }

    private EventHandler<WindowEvent> getEventHandler() {
        return new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                midiModule.terminate();
                lowerKeyHandler.terminate();
            }
        };
    }
}
