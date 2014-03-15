package kakeibon4j.internal.scraping.xml.parser;

import java.util.Calendar;
import java.util.Date;

public abstract class Parser {
	
	public static Long parseLong(final String text) {
		try {
			return Long.valueOf(text);
		} catch (final NumberFormatException e) {
			return null;
		}
	}
	
	protected static long parseLongValue(final String text) {
		return Long.parseLong(text);
	}
	
	protected static int parseIntValue(final String text) {
		return Integer.parseInt(text);
	}
	
	protected static boolean parseBooleanValue(final String text) {
		if ("1".equals(text)) {
			return true;
		} else if ("0".equals(text)) {
			return false;
		} else {
			return Boolean.parseBoolean(text);
		}
	}
	
	protected static Date parseDate(final String text) { // yyyyMMdd形式
		final int year = Integer.parseInt(text.substring(0, 4));
		final int month = Integer.parseInt(text.substring(4, 6));
		final int day = Integer.parseInt(text.substring(6, 8));
		
		final Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day, 0, 0, 0);
		date.set(Calendar.MILLISECOND, 0); // きっちりミリ秒も0にする
		return date.getTime();
	}
	
	protected static Date parseDateTime(final String text) { // yyyy/MM/dd hh:mm:ss形式
		final String[] splited = text.split("[/ :]");
		if (splited.length != 6) {
			throw new IllegalArgumentException("Argument text:" + text + "must be formatted yyyy/MM/dd hh:mm:ss.");
		}
		
		final int year = Integer.parseInt(splited[0]);
		final int month = Integer.parseInt(splited[1]);
		final int day = Integer.parseInt(splited[2]);
		final int hour = Integer.parseInt(splited[3]);
		final int minute = Integer.parseInt(splited[4]);
		final int second = Integer.parseInt(splited[5]);
		
		final Calendar date = Calendar.getInstance();
		date.set(year, month - 1, day, hour, minute, second);
		date.set(Calendar.MILLISECOND, 0); // きっちりミリ秒も0にする
		return date.getTime();
	}
	
	protected static String parseString(final String text) {
		final String trimmed;
		if (text != null) {
			trimmed = text.trim();
		} else {
			trimmed = null;
		}
		return emptyStringToNull(trimmed);
	}
	
	private static String emptyStringToNull(final String text) {
		if (text == null || "".equals(text)) {
			return null;
		}
		return text;
	}
}
