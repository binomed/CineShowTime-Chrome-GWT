package com.binomed.cineshowtime.client.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.binomed.cineshowtime.client.db.callBack.MovieCallBack;
import com.binomed.cineshowtime.client.db.callBack.PrefCallBack;
import com.binomed.cineshowtime.client.db.callBack.TheaterCallBack;
import com.binomed.cineshowtime.client.model.LocalisationBean;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.ReviewBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.model.YoutubeBean;
import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.ScalarCallback;
import com.google.code.gwt.database.client.service.VoidCallback;

public class CineShowTimeDBHelper implements ICineShowTimeDBHelper {

	private CineShowTimeDataBase dataBase;

	@Override
	public void initDataBase(CineShowTimeDataBase dataBase) {
		this.dataBase = dataBase;

		VoidCallback callBack = new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub

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
		if (nearResp != null) {
			RowIdListCallback callBack = new RowIdListCallback() {

				@Override
				public void onFailure(DataServiceException error) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(List<Integer> rowIds) {
					// TODO Auto-generated method stub

				}
			};
			for (TheaterBean theater : nearResp.getTheaterList()) {
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

			for (MovieBean movie : nearResp.getMapMovies().values()) {
				dataBase.createOrUpdateMovie(movie, callBack);
				if (movie.getReviews() != null) {
					for (ReviewBean review : movie.getReviews()) {
						dataBase.createReview(review, movie.getId(), callBack);
					}
				}
				if (movie.getYoutubeVideos() != null) {
					for (YoutubeBean video : movie.getYoutubeVideos()) {
						dataBase.createVideo(video, movie.getId(), callBack);
					}
				}
			}
		} else {

		}

	}

	@Override
	public void completeMovie(MovieBean movie) {
		VoidCallback callBack = new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub

			}
		};
		dataBase.deleteMovies(movie.getId(), callBack);
		dataBase.deleteReviews(movie.getId(), callBack);
		dataBase.deleteVideo(movie.getId(), callBack);

		RowIdListCallback callBackInsert = new RowIdListCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<Integer> rowIds) {
				// TODO Auto-generated method stub

			}
		};

		dataBase.createOrUpdateMovie(movie, callBackInsert);
		if (movie.getReviews() != null) {
			for (ReviewBean review : movie.getReviews()) {
				dataBase.createReview(review, movie.getId(), callBackInsert);
			}
		}
		if (movie.getYoutubeVideos() != null) {
			for (YoutubeBean video : movie.getYoutubeVideos()) {
				dataBase.createVideo(video, movie.getId(), callBackInsert);
			}
		}

	}

	@Override
	public void writeRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public void writePreference(String key, String value) {

		VoidCallback callBack = new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub

			}
		};

		dataBase.deletePreference(key, callBack);
		RowIdListCallback callBackInsert = new RowIdListCallback() {

			@Override
			public void onFailure(DataServiceException error) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<Integer> rowIds) {
				// TODO Auto-generated method stub

			}
		};
		dataBase.createPreference(key, value, callBackInsert);

	}

	@Override
	public void readPreference(final String key, final PrefCallBack callBack) {
		dataBase.fetchPreference(key, new ScalarCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);
			}

			@Override
			public void onSuccess(GenericRow result) {
				String value = result.getString(CineShowTimeDataBase.KEY_PREFERENCE_VALUE);
				callBack.prefValue(key, value);
			}
		});

	}

	@Override
	public void getMovie(String movieId, final MovieCallBack callBack) {
		dataBase.fetchMovie(movieId, new ScalarCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);

			}

			@Override
			public void onSuccess(GenericRow result) {
				fillMovie(result, callBack);

			}
		});
	}

	private void fillMovie(GenericRow result, MovieCallBack callBack) {
		MovieBean movieBean = new MovieBean();

		movieBean.setId(result.getString(CineShowTimeDataBase.KEY_MOVIE_ID));
		movieBean.setCid(result.getString(CineShowTimeDataBase.KEY_MOVIE_CID));
		movieBean.setImdbId(result.getString(CineShowTimeDataBase.KEY_MOVIE_IMDB_ID));
		movieBean.setMovieName(result.getString(CineShowTimeDataBase.KEY_MOVIE_NAME));
		movieBean.setEnglishMovieName(result.getString(CineShowTimeDataBase.KEY_MOVIE_ENGLISH_NAME));
		movieBean.setImdbDesrciption(result.getInt(CineShowTimeDataBase.KEY_MOVIE_IMDB_DESC) == 1);
		movieBean.setDescription(result.getString(CineShowTimeDataBase.KEY_MOVIE_DESC));
		movieBean.setLang(result.getString(CineShowTimeDataBase.KEY_MOVIE_LANG));
		movieBean.setUrlImg(result.getString(CineShowTimeDataBase.KEY_MOVIE_IMG_URL));
		movieBean.setUrlWikipedia(result.getString(CineShowTimeDataBase.KEY_MOVIE_WIKIPEDIA_URL));
		movieBean.setRate(result.getDouble(CineShowTimeDataBase.KEY_MOVIE_RATE));
		movieBean.setMovieTime(Double.valueOf(result.getDouble(CineShowTimeDataBase.KEY_MOVIE_TIME)).longValue());
		movieBean.setStyle(result.getString(CineShowTimeDataBase.KEY_MOVIE_STYLE));
		movieBean.setActorList(result.getString(CineShowTimeDataBase.KEY_MOVIE_ACTORS));
		movieBean.setDirectorList(result.getString(CineShowTimeDataBase.KEY_MOVIE_DIRECTORS));

		getReviews(movieBean, callBack);
	}

	private void getReviews(final MovieBean movie, final MovieCallBack callBack) {

		dataBase.fetchReviews(movie.getId(), new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				movie.setReviews(new ArrayList<ReviewBean>());
				if (result != null) {
					ReviewBean reviewBean = null;
					for (GenericRow row : result) {
						reviewBean = new ReviewBean();
						movie.getReviews().add(reviewBean);

						reviewBean.setRate(row.getFloat(CineShowTimeDataBase.KEY_REVIEW_RATE));
						reviewBean.setAuthor(row.getString(CineShowTimeDataBase.KEY_REVIEW_AUTHOR));
						reviewBean.setReview(row.getString(CineShowTimeDataBase.KEY_REVIEW_CONTENT));
						reviewBean.setSource(row.getString(CineShowTimeDataBase.KEY_REVIEW_SOURCE));
						reviewBean.setUrlReview(row.getString(CineShowTimeDataBase.KEY_REVIEW_URL_REVIEW));
					}
				}
				getVideos(movie, callBack);
			}
		});

	}

	private void getVideos(final MovieBean movie, final MovieCallBack callBack) {
		dataBase.fetchVideos(movie.getId(), new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				movie.setYoutubeVideos(new ArrayList<YoutubeBean>());
				if (result != null) {
					YoutubeBean videoBean = null;
					for (GenericRow row : result) {
						videoBean = new YoutubeBean();
						movie.getYoutubeVideos().add(videoBean);

						videoBean.setVideoName(row.getString(CineShowTimeDataBase.KEY_VIDEO_NAME));
						videoBean.setUrlImg(row.getString(CineShowTimeDataBase.KEY_VIDEO_URL_IMG));
						videoBean.setUrlVideo(row.getString(CineShowTimeDataBase.KEY_VIDEO_URL_VIDEO));
					}
				}

				callBack.movie(movie);

			}
		});
	}

	@Override
	public void getTheaterFav(final TheaterCallBack callBack) {
		dataBase.fetchAllFavTheaters(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);

			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				ArrayList<TheaterBean> theaterBeanList = new ArrayList<TheaterBean>();
				TheaterBean theaterBean = null;
				LocalisationBean location = null;
				for (GenericRow row : result) {
					theaterBean = new TheaterBean();

					theaterBean.setId(row.getString(CineShowTimeDataBase.KEY_FAV_TH_THEATER_ID));
					theaterBean.setTheaterName(row.getString(CineShowTimeDataBase.KEY_FAV_TH_THEATER_NAME));
					location = new LocalisationBean();
					location.setCityName(row.getString(CineShowTimeDataBase.KEY_FAV_TH_THEATER_PLACE));
					location.setCountryNameCode(row.getString(CineShowTimeDataBase.KEY_FAV_TH_THEATER_COUNRTY_CODE));
					location.setPostalCityNumber(row.getString(CineShowTimeDataBase.KEY_FAV_TH_THEATER_POSTAL_CODE));
					location.setLatitude(row.getDouble(CineShowTimeDataBase.KEY_FAV_TH_THEATER_LAT));
					location.setLongitude(row.getDouble(CineShowTimeDataBase.KEY_FAV_TH_THEATER_LONG));

					theaterBean.setPlace(location);

					theaterBeanList.add(theaterBean);
				}

				callBack.theaters(theaterBeanList);
			}
		});
	}

	@Override
	public void getTheaters(final TheaterCallBack callBack) {
		dataBase.fetchAllTheaters(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);

			}

			@Override
			public void onSuccess(List<GenericRow> result) {

				final ArrayList<TheaterBean> theaterBeanList = new ArrayList<TheaterBean>();
				final int size = result.size();

				TheaterCallBack callBackSingle = new TheaterCallBack() {

					@Override
					public void onError(DataServiceException exception) {
						callBack.onError(exception);

					}

					@Override
					public void theaters(ArrayList<TheaterBean> theaterList) {
					}

					@Override
					public void theater(TheaterBean theater) {
						theaterBeanList.add(theater);
						if (theaterBeanList.size() == size) {
							callBack.theaters(theaterBeanList);
						}

					}
				};

				TheaterBean theaterBean = null;

				for (GenericRow row : result) {
					theaterBean = new TheaterBean();
					theaterBean.setMovieMap(new HashMap<String, List<ProjectionBean>>());
					theaterBean.setId(row.getString(CineShowTimeDataBase.KEY_THEATER_ID));
					theaterBean.setTheaterName(row.getString(CineShowTimeDataBase.KEY_THEATER_NAME));

					getShowTimes(theaterBean, callBackSingle);
				}

			}
		});
	}

	private void getShowTimes(final TheaterBean theaterBean, final TheaterCallBack callBack) {
		dataBase.fetchShowtime(theaterBean.getId(), new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				// Fetch showtimes link to theater
				ProjectionBean projectionBean = null;
				List<ProjectionBean> showtimeList = null;
				for (GenericRow row : result) {
					String movieId = row.getString(CineShowTimeDataBase.KEY_SHOWTIME_MOVIE_ID);

					showtimeList = theaterBean.getMovieMap().get(movieId);
					if (showtimeList == null) {
						showtimeList = new ArrayList<ProjectionBean>();
						theaterBean.getMovieMap().put(movieId, showtimeList);
					}

					projectionBean = new ProjectionBean();
					projectionBean.setShowtime(Double.valueOf(row.getDouble(CineShowTimeDataBase.KEY_SHOWTIME_TIME)).longValue());
					projectionBean.setSubtitle(row.getString(CineShowTimeDataBase.KEY_SHOWTIME_LANG));
					projectionBean.setReservationLink(row.getString(CineShowTimeDataBase.KEY_SHOWTIME_RESERVATION_URL));

					showtimeList.add(projectionBean);
				}

				getLocalisation(theaterBean, callBack);

			}
		});
	}

	private void getLocalisation(final TheaterBean theaterBean, final TheaterCallBack callBack) {
		dataBase.fetchLocation(theaterBean.getId(), new ScalarCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);
			}

			@Override
			public void onSuccess(GenericRow result) {
				// Fetch location link to theater
				LocalisationBean localisationBean = new LocalisationBean();
				theaterBean.setPlace(localisationBean);

				localisationBean.setCityName(result.getString(CineShowTimeDataBase.KEY_LOCALISATION_CITY_NAME));
				localisationBean.setCountryName(result.getString(CineShowTimeDataBase.KEY_LOCALISATION_COUNTRY_NAME));
				localisationBean.setCountryNameCode(result.getString(CineShowTimeDataBase.KEY_LOCALISATION_COUNTRY_CODE));
				localisationBean.setPostalCityNumber(result.getString(CineShowTimeDataBase.KEY_LOCALISATION_POSTAL_CODE));
				localisationBean.setDistance(result.getFloat(CineShowTimeDataBase.KEY_LOCALISATION_DISTANCE));
				localisationBean.setDistanceTime(Double.valueOf(result.getDouble(CineShowTimeDataBase.KEY_LOCALISATION_DISTANCE_TIME)).longValue());
				localisationBean.setLatitude(result.getDouble(CineShowTimeDataBase.KEY_LOCALISATION_LATITUDE));
				localisationBean.setLongitude(result.getDouble(CineShowTimeDataBase.KEY_LOCALISATION_LONGITUDE));
				localisationBean.setSearchQuery(result.getString(CineShowTimeDataBase.KEY_LOCALISATION_SEARCH_QUERY));

				callBack.theater(theaterBean);
			}
		});
	}

	@Override
	public void getMovies(final MovieCallBack callBack) {
		dataBase.fetchAllMovies(new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
				callBack.onError(error);
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				final ArrayList<MovieBean> movieList = new ArrayList<MovieBean>();
				final int size = result.size();

				MovieCallBack callBackSingle = new MovieCallBack() {

					@Override
					public void onError(DataServiceException exception) {
						callBack.onError(exception);
					}

					@Override
					public void movieList(ArrayList<MovieBean> movieList) {
					}

					@Override
					public void movie(MovieBean movie) {
						movieList.add(movie);
						if (movieList.size() == size) {
							callBack.movieList(movieList);
						}
					}
				};

				for (GenericRow row : result) {
					fillMovie(row, callBackSingle);
				}

			}
		});
	}
}
