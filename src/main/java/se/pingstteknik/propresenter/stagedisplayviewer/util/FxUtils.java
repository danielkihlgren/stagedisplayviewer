package se.pingstteknik.propresenter.stagedisplayviewer.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

import static se.pingstteknik.propresenter.stagedisplayviewer.config.Property.*;

public class FxUtils {

    private static final String BLACK_BACKGROUND = "-fx-background-color: #000;";

    public Text createLowerKey() {
        Text lowerKey = new Text();
        lowerKey.setFont(Font.font(FONT_FAMILY.toString(), FontWeight.MEDIUM, 60));
        lowerKey.setFill(Color.WHITE);
        lowerKey.setWrappingWidth(getWrappingWidth());
        lowerKey.setTextAlignment(TextAlignment.CENTER);
        return lowerKey;
    }

    public Scene createScene(Text lowerKey) {
        Rectangle2D bounds = getScreenBounds();
        return new Scene(createRoot(lowerKey), bounds.getWidth(), bounds.getHeight());
    }

    private double getWrappingWidth() {
        return getScreenBounds().getWidth() * outputWidthPercentage();
    }

    private Rectangle2D getScreenBounds() {
        return OUTPUT_SCREEN.toInt() <= Screen.getScreens().size()
                ? Screen.getScreens().get(OUTPUT_SCREEN.toInt()-1).getBounds()
                : Screen.getPrimary().getBounds();
    }

    private double outputWidthPercentage() {
        return 0.01 * (double) OUTPUT_WIDTH_PERCENTAGE.toInt();
    }

    private GridPane createRoot(Text lowerKey) {
        GridPane root = new GridPane();
        root.setStyle(BLACK_BACKGROUND);
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.add(lowerKey, 0, 0, 2, 1);
        return root;
    }
}
