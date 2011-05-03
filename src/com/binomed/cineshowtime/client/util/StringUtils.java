package com.binomed.cineshowtime.client.util;


public class StringUtils {

	private StringUtils() {

	}

	public static boolean isEmpty(final String str) {
		return str == null || "".equalsIgnoreCase(str.trim());
	}

	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	public static boolean equalsIC(final String str1, final String str2) {
		if (str1 == null && str2 == null) {
			return true;
		} else if (str1 != null) {
			return str1.equalsIgnoreCase(str2);
		} else if (str2 != null) {
			return str2.equalsIgnoreCase(str1);
		}
		return false;
	}
}
