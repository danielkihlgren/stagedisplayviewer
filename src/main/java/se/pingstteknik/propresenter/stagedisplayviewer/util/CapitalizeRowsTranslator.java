package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CapitalizeRowsTranslator {
	/**
	 * Capitalizes the first word of every line.
	 * @param text
	 * @return
	 */
	public static String transform(String text) {
		Matcher m = Pattern.compile("(?<=\n|^)([a-z])").matcher(text);
		StringBuffer result = new StringBuffer();
		while(m.find()) // for each match, replace with capitalized text.
			m.appendReplacement(result, m.group().toUpperCase());
		m.appendTail(result);
		return result.toString();
	}
}
