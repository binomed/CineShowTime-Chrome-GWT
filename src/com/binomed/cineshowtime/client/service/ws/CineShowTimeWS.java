package com.binomed.cineshowtime.client.service.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.parsing.ParserMovieResultDomXml;
import com.binomed.cineshowtime.client.parsing.ParserNearResultDomXml;
import com.binomed.cineshowtime.client.service.ws.callback.ImdbRequestCallback;
import com.binomed.cineshowtime.client.service.ws.callback.MovieRequestCallback;
import com.binomed.cineshowtime.client.service.ws.callback.NearTheatersRequestCallback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

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

	private static CineShowTimeWS instance;

	private static Map<String, MovieBean> movieMap;

	private final Map<String, List<ImdbRequestCallback>> listeners = new HashMap<String, List<ImdbRequestCallback>>();

	public void register(String movieId, ImdbRequestCallback listener) {
		List<ImdbRequestCallback> listenerList = listeners.get(movieId);
		if (listenerList == null) {
			listenerList = new ArrayList<ImdbRequestCallback>();
			listeners.put(movieId, listenerList);
		}
		listenerList.add(listener);
	}

	public void unregister(String movieId, ImdbRequestCallback listener) {
		List<ImdbRequestCallback> listenerList = listeners.get(movieId);
		if (listenerList != null) {
			listenerList.remove(listener);
		}
	}

	public static synchronized CineShowTimeWS getInstance() {
		if (CineShowTimeWS.instance == null) {
			CineShowTimeWS.instance = new CineShowTimeWS();
		}
		return CineShowTimeWS.instance;
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
	public void requestNearTheatersFromLatLng(double latitude, double longitude, final NearTheatersRequestCallback callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(LATITUDE_PARAM, String.valueOf(latitude));
		params.put(LONGITUDE_PARAM, String.valueOf(longitude));
		doGet(URL_CONTEXT_SHOWTIME_NEAR, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				NearResp resp = ParserNearResultDomXml.parseResult(response.getText());
				movieMap = resp.getMapMovies();
				callback.onNearResp(resp);
			}

			@Override
			public void onError(Request request, Throwable exception) {
				callback.onError(exception);
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
	public void requestMovie(Map<String, String> params, final MovieRequestCallback callback) {
		doGet(URL_CONTEXT_SHOWTIME_MOVIE, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				// TODO Parsing
				callback.onResponse(response.getText());

			}

			@Override
			public void onError(Request request, Throwable exception) {
				callback.onError(exception);
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
	public void requestImdbInfo(Map<String, String> params, final MovieBean movie) {
		movie.setState(MovieBean.STATE_IN_PROGRESS);
		movieMap.put(movie.getId(), movie);

		doGet(URL_CONTEXT_IMDB, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				ParserMovieResultDomXml.parseResult(response.getText(), movie);
				movie.setState(MovieBean.STATE_LOADED);
				movieMap.put(movie.getId(), movie);
				List<ImdbRequestCallback> listenerList = listeners.get(movie.getId());
				if (listenerList != null) {
					for (ImdbRequestCallback callback : listenerList) {
						callback.onMovieLoaded(movie);
					}
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				List<ImdbRequestCallback> listenerList = listeners.get(movie.getId());
				if (listenerList != null) {
					for (ImdbRequestCallback callback : listenerList) {
						callback.onMovieLoadedError(exception);
					}
				}
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
