package com.binomed.cineshowtime.client.util;

import com.google.gwt.i18n.client.LocaleInfo;

public class LocaleUtils {

	private final static String DEFAULT = "default";
	private final static String DEFAULT_LOCALE = "en";

	private LocaleUtils() {

	}

	public static String getLocale() {
		LocaleInfo locale = LocaleInfo.getCurrentLocale();
		if (locale == null || StringUtils.equalsIC(locale.getLocaleName(), DEFAULT)) {
			return DEFAULT_LOCALE;
		}
		return locale.getLocaleName();
	}

}
