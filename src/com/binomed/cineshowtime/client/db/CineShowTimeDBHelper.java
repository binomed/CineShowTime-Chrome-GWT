package com.binomed.cineshowtime.client.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.binomed.cineshowtime.client.IClientFactory;
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
import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.VoidCallback;
import com.google.gwt.user.client.Window;

public class CineShowTimeDBHelper implements ICineShowTimeDBHelper, CineshowtimeDbCst {

	private CineShowTimeDataBase dataBase;
	private IClientFactory clientFactory;
	private ArrayList<TheaterBean> theaterFav = null;

	public CineShowTimeDBHelper(IClientFactory clientFactory, CineShowTimeDataBase dataBase) {
		super();
		this.clientFactory = clientFactory;
		initDataBase(dataBase, true);
	}

	@Override
	public void initDataBase(CineShowTimeDataBase dataBase, final boolean launchRequest) {
		this.dataBase = dataBase;

		VoidCallback callBack = new VoidCallback() {

			private int nbResp = 0;

			@Override
			public void onFailure(DataServiceException error) {
				nbResp++;

			}

			@Override
			public void onSuccess() {
				nbResp++;
				if ((nbResp == 9) && launchRequest) {
					getTheaterFav();
				}
			}
		};

		dataBase.initTableFavTheater(callBack);
		dataBase.initTableLastChange(callBack);
		dataBase.initTableLocation(callBack);
		dataBase.initTableMovie(callBack);
		dataBase.initTableRequest(callBack);
		dataBase.initTableReview(callBack);
		dataBase.initTableShowtime(callBack);
		dataBase.initTableTheater(callBack);
		dataBase.initTableVideo(callBack);

	}

	@Override
	public void writeNearResp(NearResp nearResp) {
		RequestBean request = clientFactory.getCineShowTimeService().getRequest();
		request.setNullResult(nearResp == null);
		request.setNearResp(nearResp != null ? nearResp.isNearResp() : false);
		writeRequest(request);
		if (nearResp != null) {
			final RowIdListCallback callBack = new RowIdListCallback() {

				@Override
				public void onFailure(DataServiceException error) {
					// TODO : Message

				}

				@Override
				public void onSuccess(List<Integer> rowIds) {
					// TODO : Message

				}
			};

			final NearResp nearRespToManage = nearResp;

			// Define call Back on cleaning database
			VoidCallback cleanCallBack = new VoidCallback() {

				int nbClean = 0;

				@Override
				public void onFailure(DataServiceException error) {
					incremente();
				}

				@Override
				public void onSuccess() {
					incremente();
				}

				private void incremente() {
					nbClean++;
					if (nbClean == 6) {
						for (TheaterBean theater : nearRespToManage.getTheaterList()) {
							dataBase.createTheater(theater, callBack);
							if (theater.getPlace() != null) {
								dataBase.createLocation(theater.getPlace(), theater.getId(), callBack);
							}

							if (theater.getMovieMap() != null) {
								for (Entry<String, List<ProjectionBean>> entryMovieProjection : theater.getMovieMap().entrySet()) {
									for (ProjectionBean projection : entryMovieProjection.getValue()) {
										dataBase.createShowtime(theater.getId(), entryMovieProjection.getKey(), projection, callBack);
									}
								}
							}

						}

						for (MovieBean movie : nearRespToManage.getMapMovies().values()) {
							completeMovie(movie);
						}
					}

				}
			};
			// We clean some datas before add new
			dataBase.deleteTheaters(cleanCallBack);
			dataBase.deleteShowtime(cleanCallBack);
			dataBase.deleteLocation(cleanCallBack);
			String idMovieList = null;
			boolean first = true;
			for (String movieId : nearResp.getMapMovies().keySet()) {
				if (!first) {
					idMovieList += "','";
				}
				idMovieList += movieId;
				first = false;
			}
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
		dataBase.deleteRequest(new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO : Message

			}

			@Override
			public void onSuccess() {
				dataBase.createMovieRequest(request.getCityName() //
						, request.getMovieName() //
						, request.getLatitude() //
						, request.getLongitude() //
						, request.getTheaterId() //
						, request.isNullResult() ? 1 : 0 //
						, request.isNearResp() ? 1 : 0 //
						, Long.valueOf(request.getTime().getTime()).doubleValue() //
						, new RowIdListCallback() {

							@Override
							public void onFailure(DataServiceException error) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(List<Integer> rowIds) {
								// TODO Auto-generated method stub

							}
						});
			}
		});

	}

	@Override
	public void writePreference(String key, String value) {

		VoidCallback callBack = new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO : Message

			}

			@Override
			public void onSuccess() {
				// TODO : Message

			}
		};

		dataBase.deletePreference(key, callBack);
		RowIdListCallback callBackInsert = new RowIdListCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO : Message

			}

			@Override
			public void onSuccess(List<Integer> rowIds) {
				// TODO : Message

			}
		};
		dataBase.createPreference(key, value, callBackInsert);

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
		dataBase.deleteFavorite(theater.getId(), new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO : Message

			}

			@Override
			public void onSuccess() {
				// TODO : Message

			}
		});
	}

	@Override
	public void addFav(TheaterBean theater) {
		if (theaterFav == null) {
			theaterFav = new ArrayList<TheaterBean>();
		}
		theaterFav.add(theater);
		dataBase.addTheaterToFavorites(theater, new RowIdListCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO : Message
			}

			@Override
			public void onSuccess(List<Integer> rowIds) {
				// TODO : Message

			}
		});
	}

	@Override
	public void getLastRequest() {
		dataBase.fetchLastMovieRequest(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO : Message

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

				}
				clientFactory.getEventBusHandler().fireEvent(new LastRequestDBEvent(request));

			}
		});

	}

	@Override
	public void clean() {
		VoidCallback callBack = new VoidCallback() {

			int nbRequest = 0;

			@Override
			public void onFailure(DataServiceException error) {
				nbRequest++;
				if (nbRequest == 9) {
					initDataBase(dataBase, false);
				}

			}

			@Override
			public void onSuccess() {
				nbRequest++;
				if (nbRequest == 9) {
					initDataBase(dataBase, false);
				}

			}
		};

		dataBase.dropFavorites(callBack);
		dataBase.dropLocation(callBack);
		dataBase.dropMovies(callBack);
		dataBase.dropPreferences(callBack);
		dataBase.dropRequest(callBack);
		dataBase.dropReview(callBack);
		dataBase.dropShowtime(callBack);
		dataBase.dropTheaters(callBack);
		dataBase.dropVersions(callBack);
		dataBase.dropVideo(callBack);
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

			final RowIdListCallback callBack = new RowIdListCallback() {

				@Override
				public void onFailure(DataServiceException error) {
					// TODO : Message

				}

				@Override
				public void onSuccess(List<Integer> rowIds) {
					// TODO : Message

				}
			};

			if (hasResults) {
				// If there is results, we don't clean review and videos because they won't change
				db.deleteMovie(movie.getId(), new VoidCallback() {

					@Override
					public void onFailure(DataServiceException error) {
						// Finaly we recreate it
						db.createOrUpdateMovie(movie, callBack);
					}

					@Override
					public void onSuccess() {
						// Finaly we recreate it
						db.createOrUpdateMovie(movie, callBack);

					}
				});
			} else {
				// else we persists data
				db.createOrUpdateMovie(movie, callBack);
				if (movie.getReviews() != null) {
					for (ReviewBean review : movie.getReviews()) {
						db.createReview(review, movie.getId(), callBack);
					}
				}
				if (movie.getYoutubeVideos() != null) {
					for (YoutubeBean video : movie.getYoutubeVideos()) {
						db.createVideo(video, movie.getId(), callBack);
					}
				}
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

		@Override
		public void onFailure(DataServiceException error) {
			clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(error));
		}

		@Override
		public void onSuccess(List<GenericRow> result) {

			if ((result != null) && (result.size() > 0)) {
				TheaterBean theaterBean = null;
				LocalisationBean localisationBean = null;
				ProjectionBean projectionBean = null;
				HashMap<String, TheaterBean> theaterMap = new HashMap<String, TheaterBean>();
				List<ProjectionBean> showtimeList = null;
				String theaterId = null;
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

				finishMovie = false;
				finishReview = false;
				finishVideo = false;

				db.fetchAllMovies(new ListCallback<GenericRow>() {

					@Override
					public void onFailure(DataServiceException error) {
						clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(error));

					}

					@Override
					public void onSuccess(List<GenericRow> result) {
						if (result != null && result.size() > 0) {
							movieList = new ArrayList<MovieBean>();
							for (GenericRow row : result) {
								movieList.add(row2MovieBean(row));
							}
						}
						finishMovie = true;
						finish();

					}
				});
				db.fetchAllReviews(new ListCallback<GenericRow>() {

					@Override
					public void onFailure(DataServiceException error) {
						clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(error));

					}

					@Override
					public void onSuccess(List<GenericRow> result) {
						if (result != null && result.size() > 0) {
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

					}
				});
				db.fetchAllVideos(new ListCallback<GenericRow>() {

					@Override
					public void onFailure(DataServiceException error) {
						clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(error));

					}

					@Override
					public void onSuccess(List<GenericRow> result) {
						if (result != null && result.size() > 0) {
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

					}
				});

			}
		}

		private void finish() {
			if (finishMovie && finishReview && finishVideo) {
				clientFactory.getCineShowTimeService().cleanMovies();
				for (MovieBean movie : movieList) {
					movie.setReviews(reviewMap.get(movie.getId()));
					movie.setYoutubeVideos(videoMap.get(movie.getId()));
					clientFactory.getCineShowTimeService().addMovie(movie);
				}

				clientFactory.getEventBusHandler().fireEvent(new TheaterDBEvent(theaterList, false));
				clientFactory.getEventBusHandler().fireEvent(new MovieDBEvent(movieList));
			}

		}

	}

}
