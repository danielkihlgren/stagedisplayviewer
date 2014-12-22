package se.pingstteknik.propresenter.stagedisplayviewer.util;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static se.pingstteknik.propresenter.stagedisplayviewer.config.Property.*;

public class FxTextUtils {

    private final Text helperText = new Text();

    public Font getOptimizedFont(String text, double maxWidth) {
        return Font.font(FONT_FAMILY.toString(), FontWeight.MEDIUM, calculateTextSize(text, maxWidth));
    }

    private int calculateTextSize(String text, double maxWidth) {
        int fontSize = MAX_FONT_SIZE.toInt() + 1;
        double width = computeTextWidth(text, MAX_FONT_SIZE.toInt());
        while (width > maxWidth && fontSize > MIN_FONT_SIZE.toInt()) {
            fontSize--;
            width = computeTextWidth(text, fontSize);
        }
        return fontSize;
    }

    private double computeTextWidth(String text, double fontSize) {
        helperText.setText(text);
        helperText.setFont(Font.font(FONT_FAMILY.toString(), FontWeight.MEDIUM, fontSize));

        helperText.setWrappingWidth(0.0D);
        double d = Math.min(helperText.prefWidth(-1.0D), 0.0D);
        helperText.setWrappingWidth((int) Math.ceil(d));
        d = Math.ceil(helperText.getLayoutBounds().getWidth());

        return d;
    }

}