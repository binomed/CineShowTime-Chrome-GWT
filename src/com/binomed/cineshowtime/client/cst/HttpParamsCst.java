package com.binomed.cineshowtime.client.cst;

public interface HttpParamsCst {

	public static final String PARAM_LAT = "lat"; //$NON-NLS-1$
	public static final String PARAM_LONG = "long"; //$NON-NLS-1$
	public static final String PARAM_PLACE = "place"; //$NON-NLS-1$
	public static final String PARAM_LANG = "lang"; //$NON-NLS-1$
	public static final String PARAM_OUTPUT = "output"; //$NON-NLS-1$
	public static final String PARAM_ZIP = "zip"; //$NON-NLS-1$
	public static final String PARAM_IE = "ie"; //$NON-NLS-1$
	public static final String PARAM_OE = "oe"; //$NON-NLS-1$
	public static final String PARAM_START = "start"; //$NON-NLS-1$
	public static final String PARAM_AUTO_GEO_LOCALIZED = "autogeolocalized"; //$NON-NLS-1$
	public static final String PARAM_CURENT_TIME = "curenttime"; //$NON-NLS-1$
	public static final String PARAM_TIME_ZONE = "timezone"; //$NON-NLS-1$
	public static final String PARAM_TRAILER = "trailer"; //$NON-NLS-1$

	public static final String PARAM_THEATER_ID = "tid"; //$NON-NLS-1$
	public static final String PARAM_MOVIE_ID = "mid"; //$NON-NLS-1$
	public static final String PARAM_MOVIE_IMDB_ID = "imdb_id"; //$NON-NLS-1$
	public static final String PARAM_DAY = "day"; //$NON-NLS-1$

	public static final String PARAM_MOVIE_NAME = "moviename"; //$NON-NLS-1$
	public static final String PARAM_MOVIE_CUR_LANG_NAME = "moviecurlangname"; //$NON-NLS-1$
	public static final String PARAM_COUNTRY_CODE = "countryCode"; //$NON-NLS-1$

	public static final String PARAM_IP = "ip"; //$NON-NLS-1$

	public static final String NEAR_GET_METHODE = "near"; //$NON-NLS-1$
	public static final String MOVIE_GET_METHODE = "movie"; //$NON-NLS-1$
	public static final String IMDB_GET_METHODE = "imdb"; //$NON-NLS-1$

	public static final String BINOMED_APP_PROTOCOL = "http://"; //$NON-NLS-1$
	public static final String BINOMED_APP_URL = "9.latest.binomed-cineshowtime-chrome.appspot.com"; //$NON-NLS-1$
	//	public static final String BINOMED_APP_URL = "10.0.2.2:8080"; //$NON-NLS-1$
	public static final String BINOMED_APP_PATH = "showtime"; //$NON-NLS-1$

	// Web services URL contexts
	final static String URL_CONTEXT_SHOWTIME_NEAR = "/" + BINOMED_APP_PATH + "/" + NEAR_GET_METHODE;
	final static String URL_CONTEXT_SHOWTIME_MOVIE = "/" + BINOMED_APP_PATH + "/" + MOVIE_GET_METHODE;
	final static String URL_CONTEXT_IMDB = "/" + IMDB_GET_METHODE;

	public static final String VALUE_XML = "xml"; //$NON-NLS-1$
	public static final String VALUE_JSON = "json"; //$NON-NLS-1$

	public static final String VALUE_TRUE = "true"; //$NON-NLS-1$
	public static final String VALUE_FALSE = "false"; //$NON-NLS-1$

	public static final int ERROR_WRONG_PLACE = 1;
	public static final int ERROR_WRONG_DATE = 2;
	public static final int ERROR_NO_DATA = 3;
	public static final int ERROR_CUSTOM_MESSAGE = 4;
}
