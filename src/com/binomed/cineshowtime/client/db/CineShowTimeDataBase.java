package com.binomed.cineshowtime.client.db;

import com.binomed.cineshowtime.client.model.LocalisationBean;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.ReviewBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.model.YoutubeBean;
import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.Connection;
import com.google.code.gwt.database.client.service.DataService;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.Select;
import com.google.code.gwt.database.client.service.Update;
import com.google.code.gwt.database.client.service.VoidCallback;

@Connection(name = "CineShowTime", version = "1.0", description = "CineShowTime data base", maxsize = 1024000)
public interface CineShowTimeDataBase extends DataService {

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

	static final int DATABASE_VERSION = 1;

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
			+ ", " + KEY_LOCALISATION_DISTANCE_TIME + " long " //$NON-NLS-1$ //$NON-NLS-2$
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
			+ ", " + KEY_REQUEST_NULL_RESULT + " short " //$NON-NLS-1$ //$NON-NLS-2$
			+ ", " + KEY_REQUEST_NEAR_RESP + " short " //$NON-NLS-1$ //$NON-NLS-2$
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

	/*
	 * SQL PART
	 * 
	 * 
	 * INIT OF TABLES
	 */

	@Update(DATABASE_CREATE_THEATER_TABLE)
	void initTableTheater(VoidCallback callBack);

	@Update(DATABASE_CREATE_FAV_THEATER_TABLE)
	void initTableFavTheater(VoidCallback callBack);

	@Update(DATABASE_CREATE_MOVIE_TABLE)
	void initTableMovie(VoidCallback callBack);

	@Update(DATABASE_CREATE_SHOWTIME_TABLE)
	void initTableShowtime(VoidCallback callBack);

	@Update(DATABASE_CREATE_LOCATION_TABLE)
	void initTableLocation(VoidCallback callBack);

	@Update(DATABASE_CREATE_REQUEST_TABLE)
	void initTableRequest(VoidCallback callBack);

	@Update(DATABASE_CREATE_REVIEW_TABLE)
	void initTableReview(VoidCallback callBack);

	@Update(DATABASE_CREATE_VIDEO_TABLE)
	void initTableVideo(VoidCallback callBack);

	@Update(DATABASE_CREATE_LAST_CHANGE_TABLE)
	void initTableLastChange(VoidCallback callBack);

	/*
	 * 
	 * SQL PART
	 * 
	 * INSERT PART
	 */

	@Update("INSERT INTO  " + DATABASE_FAV_THEATER_TABLE //
			+ " (" + KEY_FAV_TH_THEATER_ID //
			+ ", " + KEY_FAV_TH_THEATER_NAME //
			+ ", " + KEY_FAV_TH_THEATER_PLACE //
			+ ", " + KEY_FAV_TH_THEATER_COUNRTY_CODE //
			+ ", " + KEY_FAV_TH_THEATER_POSTAL_CODE //
			+ ", " + KEY_FAV_TH_THEATER_LAT //
			+ ", " + KEY_FAV_TH_THEATER_LONG //
			+ ") values(" //
			+ "{theater.getId()}" //
			+ ",{theater.getTheaterName()}" //
			+ ",{theater.getPlace().getCityName()}" //
			+ ",{theater.getPlace().getCountryNameCode()}" //
			+ ",{theater.getPlace().getPostalCityNumber()}" //
			+ ",{theater.getPlace().getLatitude()}" //
			+ ",{theater.getPlace().getLongitude()}" //
			+ ")"//
	)
	void addTheaterToFavorites(TheaterBean theater, RowIdListCallback callback);

	@Update(" INSERT INTO " + DATABASE_REQUEST_TABLE //
			+ " (" + KEY_REQUEST_CITY_NAME //
			+ ", " + KEY_REQUEST_MOVIE_NAME //
			+ ", " + KEY_REQUEST_THEATER_ID //
			+ ", " + KEY_REQUEST_LATITUDE //
			+ ", " + KEY_REQUEST_LONGITUDE //
			+ ", " + KEY_REQUEST_TIME //
			+ ", " + KEY_REQUEST_NULL_RESULT//
			+ ", " + KEY_REQUEST_NEAR_RESP //
			+ ") VALUES({cityName},{movieName},{theaterId},{latitude},{longitude},{time},{nullResult},{nearResp}) ")
	void createMovieRequest(String cityName, String movieName, Double latitude, Double longitude, String theaterId, short nullResult, short nearResp, long time, RowIdListCallback callBack);

	@Update("INSERT INTO " + DATABASE_THEATERS_TABLE //
			+ " (" + KEY_THEATER_ID //
			+ ", " + KEY_THEATER_NAME //
			+ ", " + KEY_THEATER_PHONE //
			+ ") VALUES ({theater.getId()},{theater.getTheaterName()},{theater.getPhoneNumber()})")
	void createTheater(TheaterBean theater, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_MOVIE_TABLE //
			+ " (" + KEY_MOVIE_ID //
			+ ", " + KEY_MOVIE_CID //
			+ ", " + KEY_MOVIE_IMDB_ID//
			+ ", " + KEY_MOVIE_NAME //
			+ ", " + KEY_MOVIE_ENGLISH_NAME//
			+ ", " + KEY_MOVIE_IMDB_DESC //
			+ ", " + KEY_MOVIE_DESC //
			+ ", " + KEY_MOVIE_TR_DESC //
			+ ", " + KEY_MOVIE_IMG_URL //
			+ ", " + KEY_MOVIE_WIKIPEDIA_URL//
			+ ", " + KEY_MOVIE_STYLE //
			+ ", " + KEY_MOVIE_RATE //
			+ ", " + KEY_MOVIE_TIME //
			+ ", " + KEY_MOVIE_ACTORS //
			+ ", " + KEY_MOVIE_DIRECTORS //
			+ ") VALUES("//
			+ "{movie.getId()}"//
			+ ",{ movie.getCid()}"//
			+ ",{movie.getImdbId()}"//
			+ ",{movie.getMovieName()}"//
			+ ",{movie.getEnglishMovieName()}"//
			+ ",{movie.isImdbDesrciption() ? 1 : 0}"//
			+ ",{movie.getDescription()}"//
			+ ",{movie.getTrDescription()}"//
			+ ",{movie.getUrlImg()}"//
			+ ",{movie.getUrlWikipedia()}"//
			+ ",{movie.getStyle()}"//
			+ ",{movie.getRate()}"//
			+ ",{movie.getMovieTime()}"//
			+ ",{movie.getActorList()}"//
			+ ",{movie.getDirectorList()}"//
			+ ")"//
	)
	void createOrUpdateMovie(MovieBean movie, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_SHOWTIME_TABLE //
			+ " (" + KEY_SHOWTIME_MOVIE_ID //
			+ ", " + KEY_SHOWTIME_THEATER_ID //
			+ ", " + KEY_SHOWTIME_TIME //
			+ ", " + KEY_SHOWTIME_LANG //
			+ ", " + KEY_SHOWTIME_RESERVATION_URL//
			+ ") VALUES(" //
			+ "{theatherId}"//
			+ ",{movieId}"//
			+ ",{time.getShowtime()}"//
			+ ",{time.getLang() != null ? time.getLang() : \"\"}"//
			+ ",{time.getReservationLink() != null ? time.getReservationLink() : \"\"}"//
			+ ")"//
	)
	void createShowtime(String theatherId, String movieId, ProjectionBean time, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_LOCATION_TABLE //
			+ " (" + KEY_LOCALISATION_THEATER_ID //
			+ ", " + KEY_LOCALISATION_CITY_NAME //
			+ ", " + KEY_LOCALISATION_COUNTRY_NAME //
			+ ", " + KEY_LOCALISATION_COUNTRY_CODE //
			+ ", " + KEY_LOCALISATION_POSTAL_CODE //
			+ ", " + KEY_LOCALISATION_DISTANCE //
			+ ", " + KEY_LOCALISATION_DISTANCE_TIME//
			+ ", " + KEY_LOCALISATION_LATITUDE //
			+ ", " + KEY_LOCALISATION_LONGITUDE //
			+ ", " + KEY_LOCALISATION_SEARCH_QUERY //
			+ ") VALUES("//
			+ "{theaterId}"//
			+ ",{location.getCityName()}"//
			+ ",{location.getCountryName()}"//
			+ ",{location.getCountryNameCode()}"//
			+ ",{location.getPostalCityNumber()}"//
			+ ",{location.getDistance()}"//
			+ ",{location.getDistanceTime()}"//
			+ ",{location.getLatitude()}"//
			+ ",{location.getLongitude()}"//
			+ ",{location.getSearchQuery()}"//
			+ ")"//
	)
	void createLocation(LocalisationBean location, String theaterId, RowIdListCallback callBack);

	@Update("INSERT INTO " + DATABASE_FAV_THEATER_TABLE //
			+ " (" + KEY_FAV_TH_THEATER_ID //
			+ ", " + KEY_FAV_TH_THEATER_NAME //
			+ ", " + KEY_FAV_TH_THEATER_PLACE //
			+ ", " + KEY_FAV_TH_THEATER_COUNRTY_CODE//
			+ ", " + KEY_FAV_TH_THEATER_POSTAL_CODE //
			+ ", " + KEY_FAV_TH_THEATER_LAT //
			+ ", " + KEY_FAV_TH_THEATER_LONG //
			+ ") VALUES (" //
			+ "{theater.getId()}"//
			+ ",{theater.getTheaterName()}"//
			+ ",{theater.getPlace().getCityName()}"//
			+ ",{theater.getPlace().getCountryNameCode()}"//
			+ ",{theater.getPlace().getPostalCityNumber()}"//
			+ ",{theater.getPlace().getLatitude()}"//
			+ ",{theater.getPlace().getLongitude()}"//
			+ ")"//
	)
	void setWidgetTheater(TheaterBean theater, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_REVIEW_TABLE //
			+ " (" + KEY_REVIEW_MOVIE_MID //
			+ ", " + KEY_REVIEW_AUTHOR //
			+ ", " + KEY_REVIEW_SOURCE //
			+ ", " + KEY_REVIEW_URL_REVIEW //
			+ ", " + KEY_REVIEW_RATE//
			+ ", " + KEY_REVIEW_CONTENT//
			+ ") VALUES(" //
			+ "{movieId}" //
			+ ",{review.getAuthor()}"//
			+ ",{review.getSource()}"//
			+ ",{review.getUrlReview()}"//
			+ ",{review.getRate()}"//
			+ ",{review.getReview()}"//
			+ ")"//
	)
	void createReview(ReviewBean review, String movieId, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_VIDEO_TABLE //
			+ " (" + KEY_VIDEO_MOVIE_MID//
			+ ", " + KEY_VIDEO_URL_VIDEO //
			+ ", " + KEY_VIDEO_URL_IMG //
			+ ", " + KEY_VIDEO_NAME //
			+ ") VALUES("//
			+ "{movieId}"//
			+ ",{video.getUrlVideo()}"//
			+ ",{video.getUrlImg()}"//
			+ ",{video.getVideoName()}"//
			+ ")"//
	)
	void createVideo(YoutubeBean video, String movieId, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_VERSION_TABLE //
			+ " (" + KEY_VERSION_DB //
			+ ", " + KEY_VERSION_APP //
			+ ") VALUES({versionDb},{codeVersion})")
	void createLastChange(int codeVersion, int versionDb, RowIdListCallback callBack);

	@Update(" INSERT INTO " + DATABASE_PREFERENCES_TABLE //
			+ " (" + KEY_PREFERENCE_KEY //
			+ ", " + KEY_PREFERENCE_VALUE //
			+ ") VALUES({key},{value})")
	void createPreference(String key, String value, RowIdListCallback callBack);

	/*
	 * 
	 * SQL PART
	 * 
	 * REQUEST PART
	 */

	@Select("SELECT * "//
			+ " FROM " + DATABASE_THEATERS_TABLE + " th, " + DATABASE_LOCATION_TABLE + " loc"//
			+ " WHERE th." + KEY_THEATER_ID + " = loc." + KEY_LOCALISATION_THEATER_ID)
	void fetchAllTheaters(ListCallback<GenericRow> callBack);

	@Select("SELECT * " + " FROM " + DATABASE_REQUEST_TABLE)
	void fetchAllMovieRequest(ListCallback<GenericRow> callBack);

	@Select("SELECT * " + " FROM " + DATABASE_REQUEST_TABLE //
			+ " WHERE " + KEY_REQUEST_TIME + " = (SELECT MAX(" + KEY_REQUEST_TIME + ") FROM " + DATABASE_REQUEST_TABLE + ")")
	void fetchLastMovieRequest(ListCallback<GenericRow> callBack);

	@Select("SELECT * "//
			+ " FROM " + DATABASE_FAV_THEATER_TABLE)
	void fetchAllFavTheaters(ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_THEATERS_TABLE + " th, " + DATABASE_LOCATION_TABLE + " loc" //
			+ " WHERE th." + KEY_THEATER_ID + " = loc." + KEY_LOCALISATION_THEATER_ID //
			+ " AND th." + KEY_THEATER_ID + " = {theaterId} "//
	)
	void fetchTheater(String theaterId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_MOVIE_TABLE //
			+ " WHERE " + KEY_MOVIE_ID + " = {movieId}")
	void fetchMovie(String movieId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_MOVIE_TABLE)
	void fetchAllMovies(ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_SHOWTIME_TABLE //
			+ " WHERE " + KEY_SHOWTIME_MOVIE_ID + " = {movieId}"//
			+ " AND " + KEY_SHOWTIME_THEATER_ID + " = {theaterId}")
	void fetchShowtime(String theaterId, String movieId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_LOCATION_TABLE //
			+ " WHERE " + KEY_LOCALISATION_THEATER_ID + " = {theaterId}")
	void fetchLocation(String theaterId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_SHOWTIME_TABLE //
			+ " WHERE " + KEY_SHOWTIME_THEATER_ID + " = {theaterId}")
	void fetchShowtime(String theaterId, ListCallback<GenericRow> callBack);

	@Select("SELECT * FROM " + DATABASE_VERSION_TABLE)
	void fetchVersion(ListCallback<GenericRow> callBack);

	@Select("SELECT * FROM " + DATABASE_PREFERENCES_TABLE + " WHERE " + KEY_PREFERENCE_KEY + " = {key} ")
	void fetchPreference(String key, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_REVIEW_TABLE //
			+ " WHERE " + KEY_REVIEW_MOVIE_MID + " = {movieId}")
	void fetchReviews(String movieId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_VIDEO_TABLE //
			+ " WHERE " + KEY_REVIEW_MOVIE_MID + " = {movieId}")
	void fetchVideos(String movieId, ListCallback<GenericRow> callBack);

	/*
	 * SQL Part
	 * 
	 * REMOVE OPERATIONS
	 */

	@Update("DELETE FROM " + DATABASE_THEATERS_TABLE)
	void deleteTheaters(VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_SHOWTIME_TABLE)
	void deleteShowtime(VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_REQUEST_TABLE)
	void deleteRequest(VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_LOCATION_TABLE)
	void deleteLocation(VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_FAV_THEATER_TABLE + " WHERE " + KEY_FAV_TH_THEATER_ID + " = {theaterId}")
	void deleteFavorite(String theaterId, VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_PREFERENCES_TABLE + " WHERE " + KEY_PREFERENCE_KEY + " = {key}")
	void deletePreference(String key, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_MOVIE_TABLE + " WHERE " + KEY_MOVIE_ID + " NOT IN = ({movieIdList})")
	void deleteMovies(String movieIdList, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_REVIEW_TABLE + " WHERE " + KEY_REVIEW_MOVIE_MID + " NOT IN = ({movieIdList})")
	void deleteReviews(String movieIdList, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_VIDEO_TABLE + " WHERE " + KEY_VIDEO_MOVIE_MID + " NOT IN = ({movieIdList})")
	void deleteVideo(String movieIdList, VoidCallback callBack);

	@Update(DROP_THEATER_TABLE)
	void dropTheaters(VoidCallback callBack);

	@Update(DROP_SHOWTIME_TABLE)
	void dropShowtime(VoidCallback callBack);

	@Update(DROP_REQUEST_TABLE)
	void dropRequest(VoidCallback callBack);

	@Update(DROP_LOCATION_TABLE)
	void dropLocation(VoidCallback callBack);

	@Update(DROP_MOVIE_TABLE)
	void dropMovies(VoidCallback callBack);

	@Update(DROP_FAV_THEATER_TABLE)
	void dropFavorites(VoidCallback callBack);

	@Update(DROP_REVIEW_TABLE)
	void dropReview(VoidCallback callBack);

	@Update(DROP_VIDEO_TABLE)
	void dropVideo(VoidCallback callBack);

	@Update(DROP_PREFERENCES_TABLE)
	void dropPreferences(VoidCallback callBack);

	@Update(DROP_VERSION_TABLE)
	void dropVersions(VoidCallback callBack);

}
