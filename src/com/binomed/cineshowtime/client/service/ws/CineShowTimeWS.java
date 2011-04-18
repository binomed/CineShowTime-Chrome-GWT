package com.binomed.cineshowtime.client.service.ws;

import java.util.HashMap;
import java.util.Map;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.cst.HttpParamsCst;
import com.binomed.cineshowtime.client.event.MovieLoadErrorEvent;
import com.binomed.cineshowtime.client.event.MovieLoadedEvent;
import com.binomed.cineshowtime.client.event.NearRespMovieErrorEvent;
import com.binomed.cineshowtime.client.event.NearRespMovieEvent;
import com.binomed.cineshowtime.client.event.NearRespNearEvent;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.parsing.ParserMovieResultDomXml;
import com.binomed.cineshowtime.client.parsing.ParserNearResultDomXml;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

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
	private IClientFactory clientFactory;

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
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadErrorEvent(exception));
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
				clientFactory.getEventBusHandler().fireEvent(new NearRespMovieErrorEvent(exception));
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
				ParserMovieResultDomXml.parseResult(response.getText(), movie);
				movie.setState(MovieBean.STATE_LOADED);
				movieMap.put(movie.getId(), movie);
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadedEvent(source, movie));
			}

			@Override
			public void onError(Request request, Throwable exception) {
				clientFactory.getEventBusHandler().fireEvent(new MovieLoadErrorEvent(exception));
			}
		});
	}

	public MovieBean getMovie(String movieId) {
		if (movieMap == null) {
			movieMap = new HashMap<String, MovieBean>();
		}
		return movieMap.get(movieId);
	}
}
