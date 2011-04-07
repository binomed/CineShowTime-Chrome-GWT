package com.binomed.cineshowtime.client.service.ws;

import java.util.Map;

import com.binomed.cineshowtime.client.service.ws.callback.ImdbRequestCallback;
import com.binomed.cineshowtime.client.service.ws.callback.MovieRequestCallback;
import com.binomed.cineshowtime.client.service.ws.callback.NearTheatersRequestCallback;
import com.binomed.cineshowtime.parsing.ParserNearResultDomXml;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class CineShowTimeService extends AbstractCineShowTimeService {

	private final static String URL_CONTEXT_SHOWTIME_NEAR = "/showtime/near";
	private final static String URL_CONTEXT_SHOWTIME_MOVIE = "/showtime/movie";
	private final static String URL_CONTEXT_IMDB = "/imdb";

	private static CineShowTimeService instance;

	// private XMLReader parser;

	public CineShowTimeService() {
		// try {
		// this.parser = XMLReaderFactory.createXMLReader();
		// } catch (SAXException e) {
		// e.printStackTrace();
		// }
	}

	public static synchronized CineShowTimeService getInstance() {
		if (CineShowTimeService.instance == null) {
			CineShowTimeService.instance = new CineShowTimeService();
		}
		return CineShowTimeService.instance;
	}

	/**
	 * Return nearest theaters following given parameters
	 * 
	 * @param params
	 *            Parameters
	 * @param callback
	 *            Specific Callback
	 */
	public void getNearTheaters(Map<String, String> params, final NearTheatersRequestCallback callback) {
		doGet(URL_CONTEXT_SHOWTIME_NEAR, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				// // Obtain an instance of an XMLReader implementation
				// ParserNearResultXml handler = new ParserNearResultXml();
				// try {
				// // Create a new instance and register it with the parser
				// parser.setContentHandler(handler);
				// // Don't worry about this for now -- we'll get to it later
				// parser.parse(response.getText());
				// } catch (SAXException e) {
				// callback.onError(e);
				// } catch (IOException e) {
				// callback.onError(e);
				// }
				// // Execute custom callback
				// callback.onResponse(handler.getNearRespBean());
				// callback.onResponse(response.getStatusText() + " - " + response.getHeadersAsString() + " / " + response.getText());
				callback.onNearResp(ParserNearResultDomXml.parseResult(response.getText()));

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
	public void getMovie(Map<String, String> params, final MovieRequestCallback callback) {
		doGet(URL_CONTEXT_SHOWTIME_MOVIE, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				// Obtain an instance of an XMLReader implementation
				// ParserMovieResultXml handler = new ParserMovieResultXml();
				// try {
				// // Create a new instance and register it with the parser
				// parser.setContentHandler(handler);
				// // Don't worry about this for now -- we'll get to it later
				// parser.parse(response.getText());
				// } catch (SAXException e) {
				// callback.onError(e);
				// } catch (IOException e) {
				// callback.onError(e);
				// }
				// // Execute custom callback
				// callback.onResponse(handler.getMovieRespBean());
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
	public void getImdbInfo(Map<String, String> params, final ImdbRequestCallback callback) {
		doGet(URL_CONTEXT_IMDB, params, new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				// Obtain an instance of an XMLReader implementation
				// ParserImdbResultXml handler = new ParserImdbResultXml();
				// try {
				// // Create a new instance and register it with the parser
				// parser.setContentHandler(handler);
				// // Don't worry about this for now -- we'll get to it later
				// parser.parse(response.getText());
				// } catch (SAXException e) {
				// callback.onError(e);
				// } catch (IOException e) {
				// callback.onError(e);
				// }
				// // Execute custom callback
				// callback.onResponse(handler.getMovieBean());
				callback.onResponse(response.getText());
			}

			@Override
			public void onError(Request request, Throwable exception) {
				callback.onError(exception);
			}
		});
	}
}
