package com.binomed.cineshowtime.util;

import com.binomed.cineshowtime.cst.EncodingUtil;

public final class CineShowTimeEncodingUtil {

	private static CineShowTimeEncodingUtil instance;

	private String encoding;

	private CineShowTimeEncodingUtil() {
		super();
		encoding = EncodingUtil.UTF8;
	}

	private static CineShowTimeEncodingUtil getInstance() {
		if (instance == null) {
			instance = new CineShowTimeEncodingUtil();
		}
		return instance;
	}

	public static void setEncoding(String encoding) {
		CineShowTimeEncodingUtil.getInstance().encoding = encoding;
	}

	public static String getEncoding() {
		return CineShowTimeEncodingUtil.getInstance().encoding;
	}

	public static String convertLocaleToEncoding() {
		return getEncoding();
	}

}
