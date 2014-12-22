package se.pingstteknik.propresenter.stagedisplayviewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;
import se.pingstteknik.propresenter.stagedisplayviewer.eventhandler.SceneKeyTypedHandler;
import se.pingstteknik.propresenter.stagedisplayviewer.runner.LowerKeyHandler;
import se.pingstteknik.propresenter.stagedisplayviewer.util.FxUtils;

import java.io.IOException;

public class Main extends Application {

    private static final String PROGRAM_TITLE = "Stage display Lower Key viewer";
    private static LowerKeyHandler lowerKeyHandler;
    private static Thread thread;
    
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        final FxUtils fxUtils = new FxUtils();

        Property.loadProperties();

        Text lowerKey = fxUtils.createLowerKey();
        lowerKeyHandler = new LowerKeyHandler(lowerKey);
        thread = new Thread(lowerKeyHandler);

        primaryStage.setTitle(PROGRAM_TITLE);
        Scene scene = fxUtils.createScene(lowerKey);
        scene.setOnKeyTyped(new SceneKeyTypedHandler(primaryStage));
        primaryStage.setScene(scene);
        primaryStage.setX(400);
        primaryStage.setOnCloseRequest(getEventHandler());
        primaryStage.setFullScreen(true);
        primaryStage.show();
        thread.start();
    }

    private EventHandler<WindowEvent> getEventHandler() {
        return new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Closing program");
                lowerKeyHandler.terminate(thread);
                closeApp();
            }
        };
    }
    
    public static void closeApp() {
        Platform.exit();
    }
}
