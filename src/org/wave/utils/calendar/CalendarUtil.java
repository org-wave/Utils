package org.wave.utils.calendar;

import java.util.Calendar;

import org.wave.utils.enums.CompareEnum;


public class CalendarUtil {

	public static CompareEnum compare(Calendar calendar, Calendar comparedCalendar) {
		if (calendar == null || comparedCalendar == null) {
			throw new IllegalArgumentException();
		}

		clearTime(calendar);
		clearTime(comparedCalendar);

		if (calendar.before(comparedCalendar)) {
			return CompareEnum.LESSER;
		}

		if (calendar.after(comparedCalendar)) {
			return CompareEnum.GREATER;
		}

		return CompareEnum.EQUALS;
	}

	private static void clearTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

}
