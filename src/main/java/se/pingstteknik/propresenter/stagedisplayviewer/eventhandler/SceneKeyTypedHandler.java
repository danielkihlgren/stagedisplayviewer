package se.pingstteknik.propresenter.stagedisplayviewer.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SceneKeyTypedHandler implements EventHandler<KeyEvent>{

    private final Stage stage;

    public SceneKeyTypedHandler(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCharacter()) {
            case "f" :
            case "F" :
                stage.setFullScreen(!stage.isFullScreen());
                break;
            default :
                break;
        }
    }
}
