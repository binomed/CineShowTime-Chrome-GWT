package com.binomed.cineshowtime.client.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.cst.CineshowtimeDbCst;
import com.binomed.cineshowtime.client.db.callback.DbBatchVoidCallBack;
import com.binomed.cineshowtime.client.db.callback.EmptyRowInsertCallBack;
import com.binomed.cineshowtime.client.db.callback.EmptyVoidCallBack;
import com.binomed.cineshowtime.client.db.callback.IDbBatchFinalTask;
import com.binomed.cineshowtime.client.db.callback.UpdateVersionDbDone;
import com.binomed.cineshowtime.client.event.db.DataBaseReadyDBEvent;
import com.binomed.cineshowtime.client.event.db.LastChangeDBEvent;
import com.binomed.cineshowtime.client.event.db.LastRequestDBEvent;
import com.binomed.cineshowtime.client.event.db.MovieDBEvent;
import com.binomed.cineshowtime.client.event.db.PrefDBEvent;
import com.binomed.cineshowtime.client.event.db.TheaterDBEvent;
import com.binomed.cineshowtime.client.model.LocalisationBean;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.RequestBean;
import com.binomed.cineshowtime.client.model.ReviewBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.model.YoutubeBean;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.gwt.user.client.Window;

public class CineShowTimeDBHelper implements ICineShowTimeDBHelper, CineshowtimeDbCst {

	private CineShowTimeDataBase dataBase;
	private IClientFactory clientFactory;
	private ArrayList<TheaterBean> theaterFav = null;
	private CineShowTimeVersionManager versionManager;
	private HashMap<String, String> preferences;

	private boolean showLastChange = false;
	private boolean prefInLoad = false;
	private boolean dataBaseReady = false;

	private static EmptyRowInsertCallBack emptyInsertCallBack = new EmptyRowInsertCallBack();
	private static EmptyVoidCallBack emptyVoidCallBack = new EmptyVoidCallBack();

	@Override
	public boolean isShowLastChange() {
		return showLastChange;
	}

	@Override
	public boolean isDataBaseReady() {
		return dataBaseReady;
	}

	public CineShowTimeDBHelper(IClientFactory clientFactory, CineShowTimeDataBase dataBase) {
		super();
		this.clientFactory = clientFactory;
		preferences = null;
		versionManager = new CineShowTimeVersionManager(dataBase, clientFactory, this, new UpdateVersionDbDone() {

			@Override
			public void onUpdateVersionDone() {
				dataBaseAvailable();
			}
		});
		initDataBase(dataBase, true);
	}

	@Override
	public void initDataBase(CineShowTimeDataBase dataBase, final boolean launchRequest) {
		this.dataBase = dataBase;

		DbBatchVoidCallBack callBack = new DbBatchVoidCallBack(11, false, new IDbBatchFinalTask() {

			@Override
			public void onError(Exception exception) {
			}

			@Override
			public void finish() {
				manageVersions();

			}
		});

		dataBase.initTableVersion(callBack);
		dataBase.initTableFavTheater(callBack);
		dataBase.initTableLastChange(callBack);
		dataBase.initTableLocation(callBack);
		dataBase.initTableMovie(callBack);
		dataBase.initTableRequest(callBack);
		dataBase.initTableReview(callBack);
		dataBase.initTableShowtime(callBack);
		dataBase.initTableTheater(callBack);
		dataBase.initTableVideo(callBack);
		dataBase.initTablePreferences(callBack);

	}

	private void manageVersions() {
		dataBase.fetchVersion(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO Message

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				int versionDb = DATABASE_VERSION;
				String versionApp = APP_VERSION;
				if ((result != null) && (result.size() > 0)) {
					GenericRow row = result.get(0);
					versionDb = row.getInt(KEY_VERSION_DB);
					versionApp = row.getString(KEY_VERSION_APP);
				} else {
					versionDb = -1;
					versionApp = "";
				}

				// If the data base version has change or the version of app, we do something
				if ((versionDb != DATABASE_VERSION) || !APP_VERSION.equals(versionApp)) {
					final boolean dbVersionChange = versionDb != DATABASE_VERSION;
					final int versionOrigin = versionDb;
					// In all case we update the table in order to put version of application
					dataBase.deleteVersion(new DbBatchVoidCallBack(1, false, new IDbBatchFinalTask() {

						@Override
						public void onError(Exception exception) {
						}

						@Override
						public void finish() {
							// When the date are remove, we insert them
							dataBase.createVersion(DATABASE_VERSION, APP_VERSION, new RowIdListCallback() {

								@Override
								public void onFailure(DataServiceException error) {
									onCreate();
								}

								@Override
								public void onSuccess(List<Integer> rowIds) {
									onCreate();

								}

								private void onCreate() {
									// When the creation has been done, we look if we have to do update on data base (data base version change)
									if (dbVersionChange) {
										versionManager.onUpdate(versionOrigin);
									}
								}
							});

						}
					}));

					// We also have to look if the version of application has change in order to show to user the last change
					if (!APP_VERSION.equals(versionApp)) {
						clientFactory.getEventBusHandler().fireEvent(new LastChangeDBEvent());
						showLastChange = true;
					}

				} else {
					// If nothing has change, we just call the favorites
					dataBaseAvailable();
				}

			}
		});
	}

	private void dataBaseAvailable() {
		dataBaseReady = true;
		clientFactory.getEventBusHandler().fireEvent(new DataBaseReadyDBEvent());
		getTheaterFav();
		readAllPreferences();
	}

	@Override
	public void writeNearResp(NearResp nearResp) {
		RequestBean request = clientFactory.getCineShowTimeService().getRequest();
		if (request != null) {
			request.setNullResult(nearResp == null);
			request.setNearResp(nearResp != null ? nearResp.isNearResp() : false);
			writeRequest(request);
		}
		if (nearResp != null) {
			final NearResp nearRespToManage = nearResp;

			// Define call Back on cleaning database
			DbBatchVoidCallBack cleanCallBack = new DbBatchVoidCallBack(6, false, new IDbBatchFinalTask() {

				@Override
				public void onError(Exception exception) {
				}

				@Override
				public void finish() {
					for (TheaterBean theater : nearRespToManage.getTheaterList()) {
						dataBase.createTheater(theater, emptyInsertCallBack);
						if (theater.getPlace() != null) {
							dataBase.createLocation(theater.getPlace(), theater.getId(), emptyInsertCallBack);
						}

						if (theater.getMovieMap() != null) {
							for (Entry<String, List<ProjectionBean>> entryMovieProjection : theater.getMovieMap().entrySet()) {
								for (ProjectionBean projection : entryMovieProjection.getValue()) {
									dataBase.createShowtime(theater.getId(), entryMovieProjection.getKey(), projection, emptyInsertCallBack);
								}
							}
						}

					}

					for (MovieBean movie : nearRespToManage.getMapMovies().values()) {
						completeMovie(movie);
					}

				}
			});

			// We clean some datas before add new
			dataBase.deleteTheaters(cleanCallBack);
			dataBase.deleteShowtime(cleanCallBack);
			dataBase.deleteLocation(cleanCallBack);
			String idMovieList = "'";
			boolean first = true;
			for (String movieId : nearResp.getMapMovies().keySet()) {
				if (!first) {
					idMovieList += "','";
				}
				idMovieList += movieId;
				first = false;
			}
			idMovieList += "'";
			dataBase.deleteMovies(idMovieList, cleanCallBack);
			dataBase.deleteReviews(idMovieList, cleanCallBack);
			dataBase.deleteVideos(idMovieList, cleanCallBack);

		} else {

		}

	}

	@Override
	public void completeMovie(MovieBean movie) {
		// We check if movie is already present in order to update it or create it
		dataBase.fetchMovie(movie.getId(), new ListCallBackFetchMovie(movie, dataBase));
	}

	@Override
	public void writeRequest(final RequestBean request) {
		dataBase.deleteRequest(new DbBatchVoidCallBack(1, false, new IDbBatchFinalTask() {

			@Override
			public void onError(Exception exception) {
			}

			@Override
			public void finish() {
				dataBase.createMovieRequest(request.getCityName() //
						, request.getMovieName() //
						, request.getLatitude() //
						, request.getLongitude() //
						, request.getTheaterId() //
						, request.isNullResult() ? 1 : 0 //
						, request.isNearResp() ? 1 : 0 //
						, request.isFavSearch() ? 1 : 0 //
						, Long.valueOf(request.getTime().getTime()).doubleValue() //
						, emptyInsertCallBack);

			}
		}));

	}

	@Override
	public void writePreference(final String key, final String value) {
		dataBase.deletePreference(key, new DbBatchVoidCallBack(1, false, new IDbBatchFinalTask() {

			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub

			}

			@Override
			public void finish() {
				dataBase.createPreference(key, value, emptyInsertCallBack);

			}
		}));

	}

	@Override
	public void readPreference(final String key) {
		dataBase.fetchPreference(key, new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new PrefDBEvent(error));
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				if ((result != null) && (result.size() > 0)) {
					GenericRow row = result.get(0);
					String value = row.getString(KEY_PREFERENCE_VALUE);
					clientFactory.getEventBusHandler().fireEvent(new PrefDBEvent(key, value));
				}
			}
		});

	}

	@Override
	public void readAllPreferences() {
		prefInLoad = true;
		dataBase.fetchAllPreferences(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new PrefDBEvent(error));
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				preferences = new HashMap<String, String>();
				if ((result != null) && (result.size() > 0)) {
					for (GenericRow row : result) {
						preferences.put(row.getString(KEY_PREFERENCE_KEY), row.getString(KEY_PREFERENCE_VALUE));
					}
				}
				clientFactory.getEventBusHandler().fireEvent(new PrefDBEvent(preferences));
			}
		});

	}

	@Override
	public void setPreference(final String key, final String value) {
		if (preferences == null) {
			preferences = new HashMap<String, String>();
		}
		preferences.put(key, value);
		dataBase.deletePreference(key, new DbBatchVoidCallBack(1, false, new IDbBatchFinalTask() {

			@Override
			public void onError(Exception exception) {
			}

			@Override
			public void finish() {
				dataBase.createPreference(key, value, emptyInsertCallBack);

			}
		}));
	}

	@Override
	public boolean isPreferenceInCache() {
		return preferences != null;
	}

	@Override
	public String readPref(String key) {
		if ((preferences == null) && prefInLoad) {
			preferences = new HashMap<String, String>();
		} else if ((preferences == null) && !prefInLoad) {
			preferences = new HashMap<String, String>();
			readAllPreferences();
		}
		String value = preferences.get(key);
		// On va gérer les valeurs par défaut
		if (value == null) {
			if (StringUtils.equalsIC(key, I18N.instance.preference_lang_key_auto_translate())) {
				value = String.valueOf(false);
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_gen_key_auto_reload())) {
				value = String.valueOf(true);
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_gen_key_theme())) {
				value = "dark";
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_gen_key_time_format())) {
				value = I18N.instance.preference_gen_default_time_format();
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_gen_key_time_adds())) {
				value = I18N.instance.preference_gen_default_time_adds();
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_loc_key_measure())) {
				value = I18N.instance.preference_loc_default_measure();
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_loc_key_time_direction())) {
				value = String.valueOf(false);
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_loc_key_enable_localisation())) {
				value = String.valueOf(true);
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_loc_key_localisation_provider())) {
				value = I18N.instance.preference_loc_default_localisation_provider();
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_sort_key_sort_theater())) {
				value = I18N.instance.preference_sort_default_sort_theater();
			}
		}
		return value;
	}

	@Override
	public void getMovie(String movieId) {
		dataBase.fetchMovie(movieId, new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(error));

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				if ((result != null) && (result.size() > 0)) {
					fillMovie(result.get(0));
				}

			}
		});
	}

	private void fillMovie(GenericRow result) {
		MovieBean movieBean = row2MovieBean(result);

		getReviews(movieBean);
	}

	private static MovieBean row2MovieBean(GenericRow result) {
		MovieBean movieBean = new MovieBean();

		movieBean.setId(result.getString(KEY_MOVIE_ID));
		movieBean.setCid(result.getString(KEY_MOVIE_CID));
		movieBean.setImdbId(result.getString(KEY_MOVIE_IMDB_ID));
		movieBean.setMovieName(result.getString(KEY_MOVIE_NAME));
		movieBean.setEnglishMovieName(result.getString(KEY_MOVIE_ENGLISH_NAME));
		movieBean.setImdbDesrciption(result.getInt(KEY_MOVIE_IMDB_DESC) == 1);
		movieBean.setDescription(result.getString(KEY_MOVIE_DESC));
		movieBean.setLang(result.getString(KEY_MOVIE_LANG));
		movieBean.setUrlImg(result.getString(KEY_MOVIE_IMG_URL));
		movieBean.setUrlWikipedia(result.getString(KEY_MOVIE_WIKIPEDIA_URL));
		movieBean.setState(result.getInt(KEY_MOVIE_STATE));
		try {
			movieBean.setRate(result.getDouble(KEY_MOVIE_RATE));
		} catch (Exception e) {
		}
		try {
			movieBean.setMovieTime(Double.valueOf(result.getDouble(KEY_MOVIE_TIME)).longValue());
		} catch (Exception e) {
		}
		movieBean.setStyle(result.getString(KEY_MOVIE_STYLE));
		movieBean.setActorList(result.getString(KEY_MOVIE_ACTORS));
		movieBean.setDirectorList(result.getString(KEY_MOVIE_DIRECTORS));
		return movieBean;
	}

	private void getReviews(final MovieBean movie) {

		dataBase.fetchReviews(movie.getId(), new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(error));
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				movie.setReviews(new ArrayList<ReviewBean>());
				if (result != null) {
					ReviewBean reviewBean = null;
					for (GenericRow row : result) {
						reviewBean = new ReviewBean();
						movie.getReviews().add(reviewBean);

						try {
							reviewBean.setRate(row.getFloat(KEY_REVIEW_RATE));
						} catch (Exception e) {
						}
						reviewBean.setAuthor(row.getString(KEY_REVIEW_AUTHOR));
						reviewBean.setReview(row.getString(KEY_REVIEW_CONTENT));
						reviewBean.setSource(row.getString(KEY_REVIEW_SOURCE));
						reviewBean.setUrlReview(row.getString(KEY_REVIEW_URL_REVIEW));
					}
				}
				getVideos(movie);
			}
		});

	}

	private void getVideos(final MovieBean movie) {
		dataBase.fetchVideos(movie.getId(), new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(error));

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				movie.setYoutubeVideos(new ArrayList<YoutubeBean>());
				if (result != null) {
					YoutubeBean videoBean = null;
					for (GenericRow row : result) {
						videoBean = new YoutubeBean();
						movie.getYoutubeVideos().add(videoBean);

						videoBean.setVideoName(row.getString(KEY_VIDEO_NAME));
						videoBean.setUrlImg(row.getString(KEY_VIDEO_URL_IMG));
						videoBean.setUrlVideo(row.getString(KEY_VIDEO_URL_VIDEO));
					}
				}

				clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(movie));
				clientFactory.getCineShowTimeService().addMovie(movie);

			}
		});
	}

	@Override
	public void getTheaterFav() {
		dataBase.fetchAllFavTheaters(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(error));

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				ArrayList<TheaterBean> theaterBeanList = new ArrayList<TheaterBean>();
				TheaterBean theaterBean = null;
				LocalisationBean location = null;
				if (result != null) {
					for (GenericRow row : result) {
						theaterBean = new TheaterBean();

						theaterBean.setId(row.getString(KEY_FAV_TH_THEATER_ID));
						theaterBean.setTheaterName(row.getString(KEY_FAV_TH_THEATER_NAME));
						location = new LocalisationBean();
						location.setCityName(row.getString(KEY_FAV_TH_THEATER_PLACE));
						location.setCountryNameCode(row.getString(KEY_FAV_TH_THEATER_COUNRTY_CODE));
						location.setPostalCityNumber(row.getString(KEY_FAV_TH_THEATER_POSTAL_CODE));
						try {
							location.setLatitude(row.getDouble(KEY_FAV_TH_THEATER_LAT));
						} catch (Exception e) {
						}
						try {
							location.setLongitude(row.getDouble(KEY_FAV_TH_THEATER_LONG));
						} catch (Exception e) {
						}

						theaterBean.setPlace(location);

						theaterBeanList.add(theaterBean);
					}

				}
				theaterFav = theaterBeanList;
				clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(theaterBeanList, true));
			}
		});
	}

	@Override
	public void getTheatersAndMovies() {
		dataBase.fetchAllTheaters(new TheaterAndMoviesCallBack(clientFactory, dataBase));
	}

	@Override
	public ArrayList<TheaterBean> getTheaterFavCache() {
		return theaterFav;
	}

	@Override
	public void removeFav(TheaterBean theater) {
		if (theaterFav == null) {
			theaterFav = new ArrayList<TheaterBean>();
		}
		theaterFav.remove(theater);
		dataBase.deleteFavorite(theater.getId(), emptyVoidCallBack);
	}

	@Override
	public void addFav(TheaterBean theater) {
		if (theaterFav == null) {
			theaterFav = new ArrayList<TheaterBean>();
		}
		theaterFav.add(theater);
		dataBase.addTheaterToFavorites(theater, emptyInsertCallBack);
	}

	@Override
	public void getLastRequest() {
		dataBase.fetchLastMovieRequest(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				clientFactory.getEventBusHandler().fireEvent(new LastRequestDBEvent(null));
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				RequestBean request = new RequestBean();
				request.setNullResult(true);
				if ((result != null) && (result.size() > 0)) {
					GenericRow row = result.get(0);
					request.setCityName(row.getString(KEY_REQUEST_CITY_NAME));
					request.setTime(new Date(Double.valueOf(row.getDouble(KEY_REQUEST_TIME)).longValue()));
					request.setTheaterId(row.getString(KEY_REQUEST_THEATER_ID));
					request.setMovieName(row.getString(KEY_REQUEST_MOVIE_NAME));
					request.setLatitude(row.getDouble(KEY_REQUEST_LATITUDE));
					request.setLongitude(row.getDouble(KEY_REQUEST_LONGITUDE));
					request.setNullResult(row.getInt(KEY_REQUEST_NULL_RESULT) == 1);
					request.setNearResp(row.getInt(KEY_REQUEST_NEAR_RESP) == 1);
					request.setFavSearch(row.getInt(KEY_REQUEST_FAV_REQUEST) == 1);

				}
				clientFactory.getEventBusHandler().fireEvent(new LastRequestDBEvent(request));

			}
		});

	}

	@Override
	public void clean() {
		DbBatchVoidCallBack callBack = new DbBatchVoidCallBack(9, false, new IDbBatchFinalTask() {

			@Override
			public void onError(Exception exception) {

			}

			@Override
			public void finish() {
				initDataBase(dataBase, false);

			}
		});

		dataBase.dropFavorites(callBack);
		dataBase.dropLastChange(callBack);
		dataBase.dropLocation(callBack);
		dataBase.dropMovies(callBack);
		dataBase.dropRequest(callBack);
		dataBase.dropReview(callBack);
		dataBase.dropShowtime(callBack);
		dataBase.dropTheaters(callBack);
		dataBase.dropVideo(callBack);
		dataBase.dropPreferences(callBack);
	}

	/*
	 * 
	 * Static class
	 */

	static class ListCallBackFetchMovie implements ListCallback<GenericRow> {

		private MovieBean movie;
		private CineShowTimeDataBase db;

		public ListCallBackFetchMovie(MovieBean movie, CineShowTimeDataBase dataBase) {
			this.movie = movie;
			this.db = dataBase;
		}

		@Override
		public void onFailure(DataServiceException error) {
			Window.alert("Erreur en base de donée : " + error.getMessage());
			// TODO : Message

		}

		@Override
		public void onSuccess(List<GenericRow> result) {
			boolean hasResults = (result != null) && (result.size() > 0);

			if (hasResults) {
				// If there is results, we don't clean review and videos because they won't change
				db.deleteMovie(movie.getId(), new DbBatchVoidCallBack(1, false, new IDbBatchFinalTask() {

					@Override
					public void onError(Exception exception) {
					}

					@Override
					public void finish() {
						// Finaly we recreate it
						db.createOrUpdateMovie(movie, emptyInsertCallBack);
						if (movie.getReviews() != null) {
							for (ReviewBean review : movie.getReviews()) {
								db.createReview(review, movie.getId(), emptyInsertCallBack);
							}
						}
						if (movie.getYoutubeVideos() != null) {
							for (YoutubeBean video : movie.getYoutubeVideos()) {
								db.createVideo(video, movie.getId(), emptyInsertCallBack);
							}
						}

					}
				}));
			} else {
				// else we persists data
				db.createOrUpdateMovie(movie, emptyInsertCallBack);
			}
		}

	}

	static class TheaterAndMoviesCallBack implements ListCallback<GenericRow> {

		private IClientFactory clientFactory;
		private CineShowTimeDataBase db;
		private ArrayList<TheaterBean> theaterList = null;
		private ArrayList<MovieBean> movieList = null;
		private HashMap<String, List<ReviewBean>> reviewMap = null;
		private HashMap<String, List<YoutubeBean>> videoMap = null;
		private boolean finishMovie, finishVideo, finishReview;

		public TheaterAndMoviesCallBack(IClientFactory clientFactory, CineShowTimeDataBase db) {
			super();
			this.clientFactory = clientFactory;
			this.db = db;
			reviewMap = new HashMap<String, List<ReviewBean>>();
			videoMap = new HashMap<String, List<YoutubeBean>>();
		}

		private void onException(Exception exception) {
			// We clean the data base and alert
			db.deleteTheaters(emptyVoidCallBack);
			db.deleteLocation(emptyVoidCallBack);
			db.deleteShowtime(emptyVoidCallBack);
			db.deleteMovies(emptyVoidCallBack);
			db.deleteVideos(emptyVoidCallBack);
			db.deleteReviews(emptyVoidCallBack);
			clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(exception));
		}

		@Override
		public void onFailure(DataServiceException error) {
			onException(error);
		}

		@Override
		public void onSuccess(List<GenericRow> result) {
			try {
				if ((result != null) && (result.size() > 0)) {
					TheaterBean theaterBean = null;
					LocalisationBean localisationBean = null;
					ProjectionBean projectionBean = null;
					HashMap<String, TheaterBean> theaterMap = new HashMap<String, TheaterBean>();
					List<ProjectionBean> showtimeList = null;
					String theaterId = null;
					// We get all theaters, showtime and locations
					for (GenericRow row : result) {
						theaterId = row.getString(KEY_THEATER_ID);
						theaterBean = theaterMap.get(theaterId);
						if (theaterBean == null) {
							// Init theater
							theaterBean = new TheaterBean();
							theaterMap.put(theaterId, theaterBean);

							theaterBean.setMovieMap(new HashMap<String, List<ProjectionBean>>());
							theaterBean.setId(row.getString(KEY_THEATER_ID));
							theaterBean.setTheaterName(row.getString(KEY_THEATER_NAME));
							theaterBean.setPhoneNumber(row.getString(KEY_THEATER_PHONE));

							// Init Localisation
							localisationBean = new LocalisationBean();
							theaterBean.setPlace(localisationBean);

							localisationBean.setCityName(row.getString(KEY_LOCALISATION_CITY_NAME));
							localisationBean.setCountryName(row.getString(KEY_LOCALISATION_COUNTRY_NAME));
							localisationBean.setCountryNameCode(row.getString(KEY_LOCALISATION_COUNTRY_CODE));
							localisationBean.setPostalCityNumber(row.getString(KEY_LOCALISATION_POSTAL_CODE));
							try {
								localisationBean.setDistance(row.getFloat(KEY_LOCALISATION_DISTANCE));
							} catch (Exception e) {
							}
							try {
								localisationBean.setDistanceTime(Double.valueOf(row.getDouble(KEY_LOCALISATION_DISTANCE_TIME)).longValue());
							} catch (Exception e) {
							}
							try {
								localisationBean.setLatitude(row.getDouble(KEY_LOCALISATION_LATITUDE));
							} catch (Exception e) {
							}
							try {
								localisationBean.setLongitude(row.getDouble(KEY_LOCALISATION_LONGITUDE));
							} catch (Exception e) {
							}
							localisationBean.setSearchQuery(row.getString(KEY_LOCALISATION_SEARCH_QUERY));
						}

						// In all case, we add showTimes
						String movieId = row.getString(KEY_SHOWTIME_MOVIE_ID);

						showtimeList = theaterBean.getMovieMap().get(movieId);
						if (showtimeList == null) {
							showtimeList = new ArrayList<ProjectionBean>();
							theaterBean.getMovieMap().put(movieId, showtimeList);
						}

						projectionBean = new ProjectionBean();
						projectionBean.setShowtime(Double.valueOf(row.getDouble(KEY_SHOWTIME_TIME)).longValue());
						projectionBean.setSubtitle(row.getString(KEY_SHOWTIME_LANG));
						projectionBean.setReservationLink(row.getString(KEY_SHOWTIME_RESERVATION_URL));

						showtimeList.add(projectionBean);
					}

					theaterList = new ArrayList<TheaterBean>(theaterMap.values());

					finishTheaters();
				} else {
					// no theaters found
					clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(new ArrayList<TheaterBean>(), false));

				}
			} catch (Exception e) {
				onException(e);
			}
		}

		private void finishTheaters() {
			finishMovie = false;
			finishReview = false;
			finishVideo = false;

			// we now have to complete the movies
			db.fetchAllMovies(new ListCallback<GenericRow>() {

				@Override
				public void onFailure(DataServiceException error) {
					onException(error);
				}

				@Override
				public void onSuccess(List<GenericRow> result) {
					// We fill movies
					try {
						if ((result != null) && (result.size() > 0)) {
							movieList = new ArrayList<MovieBean>();
							for (GenericRow row : result) {
								movieList.add(row2MovieBean(row));
							}
						}
						finishMovie = true;
						finish();
					} catch (Exception e) {
						onException(e);
					}
				}
			});
			db.fetchAllReviews(new ListCallback<GenericRow>() {

				@Override
				public void onFailure(DataServiceException error) {
					onException(error);
				}

				@Override
				public void onSuccess(List<GenericRow> result) {
					// we fill reviews
					try {
						if ((result != null) && (result.size() > 0)) {
							List<ReviewBean> reviewList = null;
							ReviewBean reviewBean = null;
							for (GenericRow row : result) {
								reviewList = reviewMap.get(row.getString(KEY_REVIEW_MOVIE_MID));
								if (reviewList == null) {
									reviewList = new ArrayList<ReviewBean>();
									reviewList = reviewMap.put(row.getString(KEY_REVIEW_MOVIE_MID), reviewList);
								}

								reviewBean = new ReviewBean();
								reviewList.add(reviewBean);

								try {
									reviewBean.setRate(row.getFloat(KEY_REVIEW_RATE));
								} catch (Exception e) {
								}
								reviewBean.setAuthor(row.getString(KEY_REVIEW_AUTHOR));
								reviewBean.setReview(row.getString(KEY_REVIEW_CONTENT));
								reviewBean.setSource(row.getString(KEY_REVIEW_SOURCE));
								reviewBean.setUrlReview(row.getString(KEY_REVIEW_URL_REVIEW));
							}
						}
						finishReview = true;
						finish();
					} catch (Exception e) {
						onException(e);
					}
				}
			});
			db.fetchAllVideos(new ListCallback<GenericRow>() {

				@Override
				public void onFailure(DataServiceException error) {
					onException(error);
				}

				@Override
				public void onSuccess(List<GenericRow> result) {
					// We fill videos
					try {
						if ((result != null) && (result.size() > 0)) {
							List<YoutubeBean> videoList = null;
							YoutubeBean videoBean = null;
							for (GenericRow row : result) {
								videoList = videoMap.get(row.getString(KEY_VIDEO_MOVIE_MID));
								if (videoList == null) {
									videoList = new ArrayList<YoutubeBean>();
									videoList = videoMap.put(row.getString(KEY_VIDEO_MOVIE_MID), videoList);
								}

								videoBean = new YoutubeBean();
								videoList.add(videoBean);

								videoBean.setVideoName(row.getString(KEY_VIDEO_NAME));
								videoBean.setUrlImg(row.getString(KEY_VIDEO_URL_IMG));
								videoBean.setUrlVideo(row.getString(KEY_VIDEO_URL_VIDEO));
							}
						}
						finishVideo = true;
						finish();
					} catch (Exception e) {
						onException(e);
					}
				}
			});

		}

		private void finish() {
			if (finishMovie && finishReview && finishVideo) {
				clientFactory.getCineShowTimeService().cleanMovies();
				if (movieList != null) {
					for (MovieBean movie : movieList) {
						movie.setReviews(reviewMap.get(movie.getId()));
						movie.setYoutubeVideos(videoMap.get(movie.getId()));
						clientFactory.getCineShowTimeService().addMovie(movie);
					}
				}
				clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(theaterList, false));
				clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(movieList));
			}

		}

	}

}
