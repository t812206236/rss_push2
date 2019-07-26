package onem.cjq.rss.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Rfc822Time {
	public static String dateToStr() {
		SimpleDateFormat fm = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		// System.out.println(date);
		return fm.format(date) + " GMT";
	}

	public static Date strToDate(String s) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
		return fm.parse(s);
	}

	public static int dateCompare(String s1, String s2) throws ParseException {
		Date d1 = strToDate(s1);
		Date d2 = strToDate(s2);
		if (d1.getTime() < d2.getTime()) {
			return -1;
		} else if (d1.getTime() > d2.getTime()) {
			return 1;
		} else {
			return 0;
		}
	}

}
