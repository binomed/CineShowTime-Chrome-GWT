package com.binomed.cineshowtime.client.service.ws;

import java.util.HashMap;
import java.util.Map;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.cst.HttpParamsCst;
import com.binomed.cineshowtime.client.event.service.MovieLoadedEvent;
import com.binomed.cineshowtime.client.event.service.NearRespMovieEvent;
import com.binomed.cineshowtime.client.event.service.NearRespNearEvent;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.parsing.ParserImdbResultDomXml;
import com.binomed.cineshowtime.client.parsing.ParserNearResultDomXml;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
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
	 * @param lang
	 * @param callback
	 *            Specific Callback
	 */
	public void requestNearTheatersFromLatLng(double latitude, double longitude, String lang) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_LAT, String.valueOf(latitude));
		params.put(PARAM_LONG, String.valueOf(longitude));
		params.put(PARAM_LANG, lang);
		doGet(URL_CONTEXT_SHOWTIME_NEAR, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = ParserNearResultDomXml.parseResult(response.getText());
				movieMap = resp.getMapMovies();
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
	 * @param lang
	 * @param callback
	 *            Specific Callback
	 */
	public void requestNearTheatersFromCityName(String cityName, final String theaterId, String lang) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_PLACE, cityName);
		if (theaterId != null) {
			params.put(PARAM_THEATER_ID, theaterId);
		}
		params.put(PARAM_LANG, lang);
		doGet(URL_CONTEXT_SHOWTIME_NEAR, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = ParserNearResultDomXml.parseResult(response.getText());
				if (movieMap == null) {
					movieMap = new HashMap<String, MovieBean>();
				}
				if (theaterId != null) {
					movieMap.putAll(resp.getMapMovies());
				} else {
					movieMap = resp.getMapMovies();
				}
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(resp));
			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new NearRespNearEvent(exception));
			}
		});
	}

	/**
	 * Return Movie info following given parameters
	 * 
	 * @param params
	 *            Parameters
	 * @param callback
	 *            Specific Callback
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

		movie.setState(MovieBean.STATE_IN_PROGRESS);
		movieMap.put(movie.getId(), movie);

		doGet(URL_CONTEXT_IMDB, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				ParserImdbResultDomXml.parseResult(response.getText(), movie);
				movie.setState(MovieBean.STATE_LOADED);
				movieMap.put(movie.getId(), movie);
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadedEvent(source, movie));
			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadedEvent(source, exception));
			}
		});
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
}
