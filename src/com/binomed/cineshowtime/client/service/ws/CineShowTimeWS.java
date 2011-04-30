package com.binomed.cineshowtime.client.service.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.cst.HttpParamsCst;
import com.binomed.cineshowtime.client.event.service.MovieLoadedEvent;
import com.binomed.cineshowtime.client.event.service.NearRespMovieEvent;
import com.binomed.cineshowtime.client.event.service.NearRespNearEvent;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.RequestBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.parsing.ParserImdbResultDomXml;
import com.binomed.cineshowtime.client.parsing.ParserNearResultDomXml;
import com.binomed.cineshowtime.client.util.LocaleUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.geocode.Placemark;

/**
 * Access class to the CineShowTime Web Services.<br/>
 * Service availables are :
 * <ul>
 * <li>requestNearTheaters</li>
 * <li>requestMovie</li>
 * <li>requestImdbInfo</li>
 * </ul>
 */
public class CineShowTimeWS extends AbstractCineShowTimeWS {

	private Map<String, MovieBean> movieMap;
	private final IClientFactory clientFactory;
	private Placemark currentPlaceMark;
	private RequestBean request;

	public CineShowTimeWS(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	/**
	 * Return nearest theaters following latitude and longitude
	 * 
	 * @param latitude
	 *            Latitude parameter
	 * @param longitude
	 *            Longitude parameter
	 * @param callback
	 *            Specific Callback
	 */
	public void requestNearTheatersFromLatLng(double latitude, double longitude, String lang, int day) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_DAY, String.valueOf(day));
		params.put(PARAM_LAT, String.valueOf(latitude));
		params.put(PARAM_LONG, String.valueOf(longitude));
		params.put(PARAM_LANG, lang != null ? lang.toLowerCase() : LocaleUtils.getLocale());
		Date currentTime = new Date();
		params.put(PARAM_CURENT_TIME, String.valueOf(currentTime.getTime()));
		params.put(PARAM_TIME_ZONE, DateTimeFormat.getFormat("z").format(currentTime));
		request = new RequestBean();
		request.setLatitude(latitude);
		request.setLongitude(longitude);
		request.setTime(currentTime);
		doGet(URL_CONTEXT_SHOWTIME_NEAR, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = ParserNearResultDomXml.parseResult(response.getText());
				movieMap = resp.getMapMovies();
				clientFactory.getDataBaseHelper().writeNearResp(resp);
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(resp));
			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(exception));
			}
		});
	}

	/**
	 * Return nearest theaters following latitude and longitude
	 * 
	 * @param cityName
	 *            The cityName parameter
	 * @param theaterId
	 *            the theaterId (optionnal)
	 */
	public void requestNearTheatersFromCityName(String cityName, final int day, String lang) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_PLACE, cityName);
		if (day != -1) {
			params.put(PARAM_DAY, String.valueOf(day));
		}
		params.put(PARAM_LANG, lang != null ? lang.toLowerCase() : LocaleUtils.getLocale());
		Date currentTime = new Date();
		params.put(PARAM_CURENT_TIME, String.valueOf(currentTime.getTime()));
		params.put(PARAM_TIME_ZONE, DateTimeFormat.getFormat("z").format(currentTime));

		request = new RequestBean();
		request.setCityName(cityName);
		request.setTime(currentTime);

		doGet(URL_CONTEXT_SHOWTIME_NEAR, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = ParserNearResultDomXml.parseResult(response.getText());
				if (movieMap == null) {
					movieMap = new HashMap<String, MovieBean>();
				}
				movieMap = resp.getMapMovies();
				clientFactory.getDataBaseHelper().writeNearResp(resp);
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(resp));
			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(exception));
			}
		});
	}

	/**
	 * Return nearest theaters following latitude and longitude
	 * 
	 * @param cityName
	 *            The cityName parameter
	 * @param theaterId
	 *            the theaterId (optionnal)
	 */
	public void requestNearTheatersFromFav(ArrayList<TheaterBean> theaterFavList, int day) {
		final int nbRequest = theaterFavList.size();
		final NearResp finalNearResp = new NearResp();
		finalNearResp.setTheaterList(new ArrayList<TheaterBean>());
		finalNearResp.setMapMovies(new HashMap<String, MovieBean>());
		finalNearResp.setNearResp(true);

		RequestCallback callBack = new RequestCallback() {
			private int compt = 0;

			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = ParserNearResultDomXml.parseResult(response.getText());
				finalNearResp.getTheaterList().addAll(resp.getTheaterList());
				finalNearResp.getMapMovies().putAll(resp.getMapMovies());

				compt++;
				if (compt == nbRequest) {
					movieMap = finalNearResp.getMapMovies();
					clientFactory.getDataBaseHelper().writeNearResp(finalNearResp);
					clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(finalNearResp));
				}

			}

			@Override
			public void onError(Request request, Throwable exception) {
				compt++;
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(exception));
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		String cityName = null;
		String lang = null;
		Date currentTime = new Date();
		for (TheaterBean theaterFav : theaterFavList) {
			params.clear();
			cityName = theaterFav.getPlace().getCityName() + ", " + theaterFav.getPlace().getCountryNameCode();
			lang = theaterFav.getPlace().getCountryNameCode();
			params.put(PARAM_DAY, String.valueOf(day));
			params.put(PARAM_PLACE, cityName);
			params.put(PARAM_THEATER_ID, theaterFav.getId());
			params.put(PARAM_LANG, lang != null ? lang.toLowerCase() : LocaleUtils.getLocale());
			params.put(PARAM_CURENT_TIME, String.valueOf(currentTime.getTime()));
			params.put(PARAM_TIME_ZONE, DateTimeFormat.getFormat("z").format(currentTime));

			doGet(URL_CONTEXT_SHOWTIME_NEAR, params, callBack);
		}
		request = new RequestBean();
		request.setFavSearch(true);
		request.setTime(currentTime);
	}

	/**
	 * Return Movie info following given parameters
	 */
	public void requestMovie(Map<String, String> params, final String source) {
		doGet(URL_CONTEXT_SHOWTIME_MOVIE, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = null;// ParserNearResultDomXml.parseResult(response.getText());
				movieMap = resp.getMapMovies();
				clientFactory.getEventBusHandler().fireEvent(new NearRespMovieEvent(resp));

			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new NearRespMovieEvent(exception));
			}
		});
	}

	/**
	 * Return IMDB info following given parameters
	 * 
	 * @param params
	 *            Parameters
	 * @param callback
	 *            Specific Callback
	 */
	public void requestImdbInfo(final MovieBean movie, final String ip, final String language, final String place, final String source) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(HttpParamsCst.PARAM_IP, ip);
		params.put(HttpParamsCst.PARAM_MOVIE_CUR_LANG_NAME, URL.encode(movie.getMovieName()));
		params.put(HttpParamsCst.PARAM_MOVIE_NAME, URL.encode(movie.getEnglishMovieName()));
		params.put(HttpParamsCst.PARAM_LANG, language);
		params.put(HttpParamsCst.PARAM_PLACE, URL.encode(place));
		params.put(HttpParamsCst.PARAM_TRAILER, "true");
		params.put(HttpParamsCst.PARAM_MOVIE_ID, movie.getId());
		Date currentTime = new Date();
		params.put(PARAM_CURENT_TIME, String.valueOf(currentTime.getTime()));
		params.put(PARAM_TIME_ZONE, DateTimeFormat.getFormat("z").format(currentTime));

		movie.setState(MovieBean.STATE_IN_PROGRESS);
		movieMap.put(movie.getId(), movie);

		doGet(URL_CONTEXT_IMDB, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				ParserImdbResultDomXml.parseResult(response.getText(), movie);
				movie.setState(MovieBean.STATE_LOADED);
				movieMap.put(movie.getId(), movie);
				clientFactory.getDataBaseHelper().completeMovie(movie);
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadedEvent(source, movie));
			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadedEvent(source, exception));
			}
		});
	}

	public void cleanMovies() {
		if (movieMap == null) {
			movieMap = new HashMap<String, MovieBean>();
		}
		movieMap.clear();
	}

	public void addMovie(MovieBean movie) {
		if (movieMap == null) {
			movieMap = new HashMap<String, MovieBean>();
		}
		movieMap.put(movie.getId(), movie);
	}

	public MovieBean getMovie(String movieId) {
		if (movieMap == null) {
			movieMap = new HashMap<String, MovieBean>();
		}
		return movieMap.get(movieId);
	}

	public Placemark getCurrentCityName() {
		return currentPlaceMark;
	}

	public void setCurrentCityName(Placemark cityName) {
		this.currentPlaceMark = cityName;
	}

	public RequestBean getRequest() {
		return request;
	}

	public void setRequest(RequestBean request) {
		this.request = request;
	}
}
