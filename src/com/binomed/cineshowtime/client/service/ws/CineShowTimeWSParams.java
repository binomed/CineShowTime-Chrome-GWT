package com.binomed.cineshowtime.client.service.ws;

interface CineShowTimeWSParams {

	/** Server URL */
	static final String APP_ENGINE_URL = "http://9.binomed-cineshowtime-chrome.appspot.com";

	// Web services URL contexts
	final static String URL_CONTEXT_SHOWTIME_NEAR = "/showtime/near";
	final static String URL_CONTEXT_SHOWTIME_MOVIE = "/showtime/movie";
	final static String URL_CONTEXT_IMDB = "/imdb";

	// Web services parameters
	static final String LATITUDE_PARAM = "lat";
	static final String LONGITUDE_PARAM = "long";

}
