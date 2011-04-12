package com.binomed.cineshowtime.client.service.ws;

import java.util.Map;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

class AbstractCineShowTimeWS implements CineShowTimeWSParams {

	/**
	 * Execute a GET request to the CineShowTime server
	 * 
	 * @param urlContext
	 *            the CineShowTime URL Context
	 * @param params
	 *            URL Context parameters
	 * @param requestCallback
	 *            Callback
	 */
	protected void doGet(String urlContext, Map<String, String> params, RequestCallback requestCallback) {
		// Build the full URL
		StringBuilder fullUrl = new StringBuilder(APP_ENGINE_URL);
		fullUrl.append(urlContext).append(getFormatedParams(params));

		// Prepare the request
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, fullUrl.toString());
		requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
		requestBuilder.setCallback(requestCallback);

		// Execute the request
		try {
			requestBuilder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Format and encode URL Params
	 * 
	 * @param params
	 *            URL Params
	 * @return URL Parameters encoded
	 */
	private String getFormatedParams(Map<String, String> params) {
		StringBuilder ret = new StringBuilder("?");
		int i = 0;
		if (params != null && !params.isEmpty()) {
			for (final String param : params.keySet()) {
				ret.append(param).append("=").append(URL.encode(params.get(param)));
				if (params.size() > ++i) {
					ret.append("&");
				}
			}
		} else {
			return "";
		}
		return ret.toString();
	}
}
