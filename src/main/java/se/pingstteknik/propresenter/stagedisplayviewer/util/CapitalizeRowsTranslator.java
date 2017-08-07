package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CapitalizeRowsTranslator {
    private static final Pattern FIRST_CHAR_REGEX = Pattern.compile("(?<=\n|^)([a-z])");
    
    /**
     * Capitalizes the first word of every line.
     * @param text
     * @return
     */
    public static String transform(String text) {
	Matcher m = FIRST_CHAR_REGEX.matcher(text);
	StringBuffer result = new StringBuffer();
	while(m.find()) // for each match, replace with capitalized text.
	    m.appendReplacement(result, m.group().toUpperCase());
	m.appendTail(result);
	return result.toString();
    }
}
