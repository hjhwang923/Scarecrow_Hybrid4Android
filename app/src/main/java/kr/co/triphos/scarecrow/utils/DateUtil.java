package kr.co.triphos.scarecrow.utils;

import java.util.Locale;

public class DateUtil {
	/**
	 * TODO 날짜형식(yyyy-MM-dd)의 문자열을 날짜형으로 변경
	 * @param s 문자열
	 * @return 잘못된 형식이면 현재날짜로 반환
	 */
	public static java.util.Date parseDate(String s) {
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			return  format.parse(s);
		}
		catch (Exception ex) {
			return new java.util.Date();
		}
	}

	/**
	 * TODO 두 날짜사이의 일수 반환
	 * @param d1 java.util.Date
	 * @param d2 java.util.Date
	 * @return Long
	 */
	public static Long dayDiff(java.util.Date d1, java.util.Date d2) {
		Long t1 = d1.getTime();
		Long t2 = d2.getTime();
		return (t2 - t1) / (24*3600*1000);
	}
}
