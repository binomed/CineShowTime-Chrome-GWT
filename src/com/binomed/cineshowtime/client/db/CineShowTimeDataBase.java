package com.binomed.cineshowtime.client.db;

import com.binomed.cineshowtime.client.cst.CineshowtimeDbCst;
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
public interface CineShowTimeDataBase extends DataService, CineshowtimeDbCst {

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

	@Update(DATABASE_CREATE_VERSION_TABLE)
	void initTableVersion(VoidCallback callBack);

	@Update(DATABASE_CREATE_PREFERENCES_TABLE)
	void initTablePreferences(VoidCallback callBack);

	/*
	 * 
	 * SQL PART
	 * 
	 * INSERT PART
	 */

	@Update("INSERT INTO  " + DATABASE_VERSION_TABLE //
			+ " (" + KEY_VERSION_DB //
			+ ", " + KEY_VERSION_APP //
			+ ") values(" //
			+ "{versionDb}" //
			+ ",{versionApp}" //
			+ ")"//
	)
	void createVersion(int versionDb, String versionApp, RowIdListCallback callback);

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
			+ ", " + KEY_REQUEST_FAV_REQUEST //
			+ ") VALUES({cityName},{movieName},{theaterId},{latitude},{longitude},{time},{nullResult},{nearResp}, {favRequest}) ")
	void createMovieRequest(String cityName, String movieName, Double latitude, Double longitude, String theaterId, Integer nullResult, Integer nearResp, Integer favRequest, Double time, RowIdListCallback callBack);

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
			+ ", " + KEY_MOVIE_STATE //
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
			+ ",{movie.getState()}"//
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
			+ "{movieId}"//
			+ ",{theatherId}"//
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

	@Select("SELECT DISTINCT "//
			+ "th." + KEY_THEATER_ID//
			+ ",th." + KEY_THEATER_NAME//
			+ ",th." + KEY_THEATER_PHONE//
			+ ",loc." + KEY_LOCALISATION_CITY_NAME//
			+ ",loc." + KEY_LOCALISATION_COUNTRY_CODE//
			+ ",loc." + KEY_LOCALISATION_COUNTRY_NAME//
			+ ",loc." + KEY_LOCALISATION_DISTANCE//
			+ ",loc." + KEY_LOCALISATION_DISTANCE_TIME//
			+ ",loc." + KEY_LOCALISATION_LATITUDE//
			+ ",loc." + KEY_LOCALISATION_LONGITUDE//
			+ ",loc." + KEY_LOCALISATION_POSTAL_CODE//
			+ ",loc." + KEY_LOCALISATION_SEARCH_QUERY//
			+ ",show." + KEY_SHOWTIME_LANG//
			+ ",show." + KEY_SHOWTIME_MOVIE_ID//
			+ ",show." + KEY_SHOWTIME_RESERVATION_URL//
			+ ",show." + KEY_SHOWTIME_TIME//
			+ " FROM " + DATABASE_THEATERS_TABLE + " th, " + DATABASE_LOCATION_TABLE + " loc, " + DATABASE_SHOWTIME_TABLE + " show "//
			+ " WHERE th." + KEY_THEATER_ID + " = loc." + KEY_LOCALISATION_THEATER_ID //
			+ " AND th." + KEY_THEATER_ID + " = show." + KEY_SHOWTIME_THEATER_ID //
	)
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
			+ " FROM " + DATABASE_SHOWTIME_TABLE //
	)
	void fetchAllShowtimes(ListCallback<GenericRow> callBack);

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

	@Select("SELECT * FROM " + DATABASE_PREFERENCES_TABLE)
	void fetchAllPreferences(ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_REVIEW_TABLE //
			+ " WHERE " + KEY_REVIEW_MOVIE_MID + " = {movieId}")
	void fetchReviews(String movieId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_REVIEW_TABLE)
	void fetchAllReviews(ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_VIDEO_TABLE //
			+ " WHERE " + KEY_REVIEW_MOVIE_MID + " = {movieId}")
	void fetchVideos(String movieId, ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_VIDEO_TABLE)
	void fetchAllVideos(ListCallback<GenericRow> callBack);

	@Select("SELECT * " //
			+ " FROM " + DATABASE_LAST_CHANGE_TABLE)
	void fetchLastChange(ListCallback<GenericRow> callBack);

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

	@Update("DELETE FROM " + DATABASE_LAST_CHANGE_TABLE)
	void deleteLastChange(VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_VERSION_TABLE)
	void deleteVersion(VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_FAV_THEATER_TABLE + " WHERE " + KEY_FAV_TH_THEATER_ID + " = {theaterId}")
	void deleteFavorite(String theaterId, VoidCallback callBack);

	@Update("DELETE FROM " + DATABASE_PREFERENCES_TABLE + " WHERE " + KEY_PREFERENCE_KEY + " = {key}")
	void deletePreference(String key, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_MOVIE_TABLE + " WHERE " + KEY_MOVIE_ID + " NOT IN ({movieIdList})")
	void deleteMovies(String movieIdList, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_MOVIE_TABLE)
	void deleteMovies(VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_MOVIE_TABLE + " WHERE " + KEY_MOVIE_ID + " = {movieId}")
	void deleteMovie(String movieId, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_REVIEW_TABLE + " WHERE " + KEY_REVIEW_MOVIE_MID + " NOT IN ({movieIdList})")
	void deleteReviews(String movieIdList, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_REVIEW_TABLE)
	void deleteReviews(VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_VIDEO_TABLE + " WHERE " + KEY_VIDEO_MOVIE_MID + " NOT IN ({movieIdList})")
	void deleteVideos(String movieIdList, VoidCallback callBack);

	@Update(sql = "DELETE FROM " + DATABASE_VIDEO_TABLE)
	void deleteVideos(VoidCallback callBack);

	@Update(DROP_THEATER_TABLE)
	void dropTheaters(VoidCallback callBack);

	@Update(DROP_LAST_CHANGE_TABLE)
	void dropLastChange(VoidCallback callBack);

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
