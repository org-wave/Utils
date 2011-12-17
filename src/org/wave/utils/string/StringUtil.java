package org.wave.utils.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private StringUtil() {

	}

	public static String toHumanCase(String value) {
		value = value.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");

		Pattern pattern = Pattern.compile("(?<=(^|[^A-Z]))[A-Z](?=([a-z]|\\s))");
		Matcher matcher = pattern.matcher(value);

		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buffer, matcher.group().toLowerCase());
		}
		matcher.appendTail(buffer);

		return buffer.toString();
	}

}
