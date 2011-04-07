package com.binomed.cineshowtime.client.service.geolocation;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Geolocation callback
 */
interface GeolocationCallback {

	void onLocation(Position position);

	void onError(JavaScriptObject jso);

}
