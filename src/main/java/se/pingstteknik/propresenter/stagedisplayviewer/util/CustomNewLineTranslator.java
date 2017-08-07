package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomNewLineTranslator {
	private static Pattern NOTES_REGEX = Pattern.compile("(i?)NEWLINE(?-i) (\\d+ ?)+");
	
	/**
	 * inserts a new line in the message as specified in the notes.
	 * disregards all whitespace that is in the message already.
	 * @param message
	 * @param notes
	 * @return
	 */
	public static String translate(String message, String notes) {
		Matcher m = NOTES_REGEX.matcher(notes);
		if(m.find()) {
			String[] args = m.group().split(" ");
			Set<Integer> indexes = new HashSet<>(); // collect word indexes.
			for(int i=1;i<args.length;i++)
				indexes.add(Integer.parseInt(args[i]));
			
			// insert specified indexes
			StringBuilder sb = new StringBuilder();
			String[] words = message.split("\\s+");
			// iterate word by word.
			for(int i=0;i<words.length;i++) {
				sb.append(words[i]);
				sb.append(' ');
				if(indexes.contains(i+1))
					sb.append('\n');
			}
			return sb.toString();
		}
		return message;
	}
}
