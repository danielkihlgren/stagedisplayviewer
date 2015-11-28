package se.pingstteknik.propresenter.stagedisplayviewer.util;

/**
 * Removes lines after an empty line
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.2.0
 */
public class RemoveLinesAfterEmptyLineTranslator {

    public String transform(String message) {
        return reconstructTextFromRows(splitStringOnLineBreaks(message));
    }

    private String reconstructTextFromRows(String[] rows) {
        StringBuilder aggregatedText = new StringBuilder();
        for (String row : rows) {
            if (row.equals("")) {
                break;
            }
            aggregatedText.append(row);
            aggregatedText.append("\n");
        }
        if (aggregatedText.length() > 0) {
            aggregatedText.deleteCharAt(aggregatedText.length() - 1);
        }
        return aggregatedText.toString();
    }

    private String[] splitStringOnLineBreaks(String message) {
        return message.split("\n");
    }

}
