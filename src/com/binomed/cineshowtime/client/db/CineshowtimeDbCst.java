package com.binomed.cineshowtime.client.db;

public interface CineshowtimeDbCst {

	/*
	 * Version application
	 */

	static final int DATABASE_VERSION = 2;
	static final String APP_VERSION = "1.0";

	/*
	 * Data base Column
	 */

	static final String KEY_THEATER_NAME = "theater_name"; //$NON-NLS-1$
	static final String KEY_THEATER_PHONE = "phone_number"; //$NON-NLS-1$
	static final String KEY_THEATER_ID = "_id"; //$NON-NLS-1$

	static final String KEY_MOVIE_ID = "_id"; //$NON-NLS-1$
	static final String KEY_MOVIE_CID = "cid"; //$NON-NLS-1$
	static final String KEY_MOVIE_IMDB_ID = "imdb_id"; //$NON-NLS-1$
	static final String KEY_MOVIE_NAME = "movie_name"; //$NON-NLS-1$
	static final String KEY_MOVIE_ENGLISH_NAME = "movie_english_name"; //$NON-NLS-1$
	static final String KEY_MOVIE_IMG_URL = "url_img"; //$NON-NLS-1$
	static final String KEY_MOVIE_WIKIPEDIA_URL = "url_wikipedia"; //$NON-NLS-1$
	static final String KEY_MOVIE_IMDB_DESC = "imdb_desc"; //$NON-NLS-1$
	static final String KEY_MOVIE_DESC = "desc"; //$NON-NLS-1$
	static final String KEY_MOVIE_TR_DESC = "trDesc"; //$NON-NLS-1$
	static final String KEY_MOVIE_TIME = "movie_time"; //$NON-NLS-1$
	static final String KEY_MOVIE_STYLE = "style"; //$NON-NLS-1$
	static final String KEY_MOVIE_RATE = "rate"; //$NON-NLS-1$
	static final String KEY_MOVIE_LANG = "lang"; //$NON-NLS-1$
	static final String KEY_MOVIE_ACTORS = "actors"; //$NON-NLS-1$
	static final String KEY_MOVIE_DIRECTORS = "directors"; //$NON-NLS-1$
	static final String KEY_MOVIE_STATE = "state"; //$NON-NLS-1$

	static final String KEY_LOCALISATION_THEATER_ID = "theater_id"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_CITY_NAME = "city_name"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_COUNTRY_NAME = "country_name"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_COUNTRY_CODE = "country_code"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_DISTANCE = "distance"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_DISTANCE_TIME = "distance_time"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_LATITUDE = "latitude"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_LONGITUDE = "longitude"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_POSTAL_CODE = "postal_code"; //$NON-NLS-1$
	static final String KEY_LOCALISATION_SEARCH_QUERY = "search_query"; //$NON-NLS-1$

	static final String KEY_SHOWTIME_ID = "_id"; //$NON-NLS-1$
	static final String KEY_SHOWTIME_THEATER_ID = "theater_id"; //$NON-NLS-1$
	static final String KEY_SHOWTIME_MOVIE_ID = "movie_id"; //$NON-NLS-1$
	static final String KEY_SHOWTIME_TIME = "showtime"; //$NON-NLS-1$
	static final String KEY_SHOWTIME_LANG = "lang"; //$NON-NLS-1$
	static final String KEY_SHOWTIME_RESERVATION_URL = "reservation_url"; //$NON-NLS-1$

	static final String KEY_REQUEST_ID = "_id"; //$NON-NLS-1$
	static final String KEY_REQUEST_LATITUDE = "latitude"; //$NON-NLS-1$
	static final String KEY_REQUEST_LONGITUDE = "longitude"; //$NON-NLS-1$
	static final String KEY_REQUEST_CITY_NAME = "city_name"; //$NON-NLS-1$
	static final String KEY_REQUEST_MOVIE_NAME = "movie_name"; //$NON-NLS-1$
	static final String KEY_REQUEST_TIME = "time"; //$NON-NLS-1$
	static final String KEY_REQUEST_THEATER_ID = "theater_id"; //$NON-NLS-1$
	static final String KEY_REQUEST_NULL_RESULT = "nullResult"; //$NON-NLS-1$
	static final String KEY_REQUEST_NEAR_RESP = "nearResp"; //$NON-NLS-1$
	static final String KEY_REQUEST_FAV_REQUEST = "favRequest"; //$NON-NLS-1$

	static final String KEY_FAV_TH_THEATER_ID = "theater_id"; //$NON-NLS-1$
	static final String KEY_FAV_TH_THEATER_NAME = "theater_name"; //$NON-NLS-1$
	static final String KEY_FAV_TH_THEATER_PLACE = "theater_place_city_name"; //$NON-NLS-1$
	static final String KEY_FAV_TH_THEATER_COUNRTY_CODE = "theater_place_counry"; //$NON-NLS-1$
	static final String KEY_FAV_TH_THEATER_POSTAL_CODE = "theater_place_postal_code"; //$NON-NLS-1$
	static final String KEY_FAV_TH_THEATER_LAT = "theater_place_lat"; //$NON-NLS-1$
	static final String KEY_FAV_TH_THEATER_LONG = "theater_place_long"; //$NON-NLS-1$

	static final String KEY_REVIEW_ID = "id"; //$NON-NLS-1$
	static final String KEY_REVIEW_MOVIE_MID = "movie_id"; //$NON-NLS-1$
	static final String KEY_REVIEW_RATE = "rate"; //$NON-NLS-1$
	static final String KEY_REVIEW_SOURCE = "source"; //$NON-NLS-1$
	static final String KEY_REVIEW_URL_REVIEW = "url_review"; //$NON-NLS-1$
	static final String KEY_REVIEW_AUTHOR = "author"; //$NON-NLS-1$
	static final String KEY_REVIEW_CONTENT = "review"; //$NON-NLS-1$

	static final String KEY_VIDEO_ID = "id"; //$NON-NLS-1$
	static final String KEY_VIDEO_MOVIE_MID = "movie_id"; //$NON-NLS-1$
	static final String KEY_VIDEO_URL_IMG = "url_img"; //$NON-NLS-1$
	static final String KEY_VIDEO_URL_VIDEO = "url_video"; //$NON-NLS-1$
	static final String KEY_VIDEO_NAME = "video_name"; //$NON-NLS-1$

	static final String KEY_LAST_CHANGE_VERSION = "version"; //$NON-NLS-1$

	static final String KEY_VERSION_DB = "db_version";//$NON-NLS-1$
	static final String KEY_VERSION_APP = "app_version";//$NON-NLS-1$

	static final String KEY_PREFERENCE_KEY = "key";//$NON-NLS-1$
	static final String KEY_PREFERENCE_VALUE = "value";//$NON-NLS-1$

	static final String DATABASE_NAME = "andShowtime"; //$NON-NLS-1$
	static final String DATABASE_THEATERS_TABLE = "theaters"; //$NON-NLS-1$
	static final String DATABASE_MOVIE_TABLE = "movies"; //$NON-NLS-1$
	static final String DATABASE_SHOWTIME_TABLE = "showtimes"; //$NON-NLS-1$
	static final String DATABASE_LOCATION_TABLE = "location"; //$NON-NLS-1$
	static final String DATABASE_FAV_THEATER_TABLE = "favTheaters"; //$NON-NLS-1$
	static final String DATABASE_REQUEST_TABLE = "request"; //$NON-NLS-1$
	static final String DATABASE_LAST_CHANGE_TABLE = "last_change"; //$NON-NLS-1$
	static final String DATABASE_REVIEW_TABLE = "reviews"; //$NON-NLS-1$
	static final String DATABASE_VIDEO_TABLE = "videos"; //$NON-NLS-1$
	static final String DATABASE_VERSION_TABLE = "versions";//$NON-NLS-1$
	static final String DATABASE_PREFERENCES_TABLE = "preferences";//$NON-NLS-1$

	/**
	 * Database creation sql statement
	 */
	static final String DATABASE_CREATE_THEATER_TABLE = "create table if not exists " + DATABASE_THEATERS_TABLE //$NON-NLS-1$
			+ " (" + KEY_THEATER_ID + " text primary key" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_THEATER_NAME + " text not null" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_THEATER_PHONE + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ");";//$NON-NLS-1$
	static final String DATABASE_CREATE_FAV_THEATER_TABLE = "create table if not exists " + DATABASE_FAV_THEATER_TABLE //$NON-NLS-1$
			+ " (" + KEY_FAV_TH_THEATER_ID + " text primary key" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_FAV_TH_THEATER_NAME + " text not null" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_FAV_TH_THEATER_PLACE + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_FAV_TH_THEATER_COUNRTY_CODE + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_FAV_TH_THEATER_POSTAL_CODE + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_FAV_TH_THEATER_LAT + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_FAV_TH_THEATER_LONG + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ");";//$NON-NLS-1$
	static final String DATABASE_CREATE_MOVIE_TABLE = " create table if not exists " + DATABASE_MOVIE_TABLE //$NON-NLS-1$
			+ " (" + KEY_MOVIE_ID + " text primary key" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_MOVIE_CID + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_IMDB_ID + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_NAME + " text not null" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_ENGLISH_NAME + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_IMDB_DESC + " integer" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_DESC + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_TR_DESC + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_IMG_URL + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_WIKIPEDIA_URL + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_LANG + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_STYLE + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_RATE + " double" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_TIME + " double" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_ACTORS + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_DIRECTORS + " text" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_MOVIE_STATE + " integer" //$NON-NLS-1$ //$NON-NLS-2$
			+ ");";//$NON-NLS-1$
	static final String DATABASE_CREATE_SHOWTIME_TABLE = " create table if not exists " + DATABASE_SHOWTIME_TABLE //$NON-NLS-1$
			+ " (" + KEY_SHOWTIME_ID + " integer primary key autoincrement" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_SHOWTIME_MOVIE_ID + " text not null" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_SHOWTIME_THEATER_ID + " text not null" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_SHOWTIME_TIME + " double not null" //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_SHOWTIME_LANG + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_SHOWTIME_RESERVATION_URL + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ");"//$NON-NLS-1$
	;
	static final String DATABASE_CREATE_LOCATION_TABLE = " create table if not exists " + DATABASE_LOCATION_TABLE //$NON-NLS-1$
			+ " (" + KEY_LOCALISATION_THEATER_ID + " text primary key" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_CITY_NAME + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_COUNTRY_NAME + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_COUNTRY_CODE + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_POSTAL_CODE + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_DISTANCE + " float " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_DISTANCE_TIME + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_LATITUDE + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_LONGITUDE + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_LOCALISATION_SEARCH_QUERY + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ");"//$NON-NLS-1$
	;

	static final String DATABASE_CREATE_REQUEST_TABLE = " create table if not exists " + DATABASE_REQUEST_TABLE //$NON-NLS-1$
			+ " (" + KEY_REQUEST_ID + " integer primary key autoincrement" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_REQUEST_CITY_NAME + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_MOVIE_NAME + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_THEATER_ID + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_LATITUDE + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_LONGITUDE + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_TIME + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_NULL_RESULT + " integer " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_NEAR_RESP + " integer " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_FAV_REQUEST + " integer " //$NON-NLS-1$ //$NON-NLS-2$
			+ ");"//$NON-NLS-1$
	;

	static final String DATABASE_CREATE_REVIEW_TABLE = " create table if not exists " + DATABASE_REVIEW_TABLE //$NON-NLS-1$
			+ " (" + KEY_REVIEW_ID + " integer primary key autoincrement" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_REVIEW_MOVIE_MID + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REVIEW_RATE + " double " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REVIEW_URL_REVIEW + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REVIEW_AUTHOR + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REVIEW_SOURCE + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REVIEW_CONTENT + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ");"//$NON-NLS-1$
	;

	static final String DATABASE_CREATE_VIDEO_TABLE = " create table if not exists " + DATABASE_VIDEO_TABLE //$NON-NLS-1$
			+ " (" + KEY_VIDEO_ID + " integer primary key autoincrement" //$NON-NLS-1$//$NON-NLS-2$
			+ ", " + KEY_VIDEO_MOVIE_MID + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_VIDEO_NAME + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_VIDEO_URL_IMG + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_VIDEO_URL_VIDEO + " text " //$NON-NLS-1$ //$NON-NLS-2$
			+ ");"//$NON-NLS-1$
	;

	;
	static final String DATABASE_CREATE_LAST_CHANGE_TABLE = " create table if not exists " + DATABASE_LAST_CHANGE_TABLE//$NON-NLS-1$
			+ " (" + KEY_LAST_CHANGE_VERSION + " integer primary key" //$NON-NLS-1$//$NON-NLS-2$
			+ ");"//$NON-NLS-1$
	;

	static final String DATABASE_CREATE_VERSION_TABLE = " create table if not exists " + DATABASE_VERSION_TABLE //
			+ " (" + KEY_VERSION_DB + " integer primary key" //
			+ ", " + KEY_VERSION_APP + " text " //
			+ ");";
	static final String DATABASE_CREATE_PREFERENCES_TABLE = " create table if not exists " + DATABASE_PREFERENCES_TABLE //
			+ " (" + KEY_PREFERENCE_KEY + " text primary key" //
			+ ", " + KEY_PREFERENCE_VALUE + " text not null " //
			+ ");";

	static final String DROP_THEATER_TABLE = "DROP TABLE IF EXISTS " + DATABASE_THEATERS_TABLE; //$NON-NLS-1$
	static final String DROP_FAV_THEATER_TABLE = "DROP TABLE IF EXISTS " + DATABASE_FAV_THEATER_TABLE; //$NON-NLS-1$
	static final String DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + DATABASE_MOVIE_TABLE; //$NON-NLS-1$
	static final String DROP_SHOWTIME_TABLE = "DROP TABLE IF EXISTS " + DATABASE_SHOWTIME_TABLE; //$NON-NLS-1$
	static final String DROP_LOCATION_TABLE = "DROP TABLE IF EXISTS " + DATABASE_LOCATION_TABLE; //$NON-NLS-1$
	static final String DROP_REQUEST_TABLE = "DROP TABLE IF EXISTS " + DATABASE_REQUEST_TABLE; //$NON-NLS-1$
	static final String DROP_LAST_CHANGE_TABLE = "DROP TABLE IF EXISTS " + DATABASE_LAST_CHANGE_TABLE; //$NON-NLS-1$
	static final String DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS " + DATABASE_REVIEW_TABLE; //$NON-NLS-1$
	static final String DROP_VIDEO_TABLE = "DROP TABLE IF EXISTS " + DATABASE_VIDEO_TABLE; //$NON-NLS-1$
	static final String DROP_VERSION_TABLE = "DROP TABLE IF EXISTS " + DATABASE_VERSION_TABLE;//$NON-NLS-1$
	static final String DROP_PREFERENCES_TABLE = "DROP TABLE IF EXISTS " + DATABASE_PREFERENCES_TABLE;//$NON-NLS-1$

}
