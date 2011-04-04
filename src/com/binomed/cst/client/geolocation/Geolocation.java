package com.binomed.cst.client.geolocation;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Implementation of the native HTML5 Geolocation API
 */
class Geolocation {

	/** Callback for the geolocation response */
	private static GeolocationCallback geoCallback;

	/**
	 * Constructor taking a callback managing responses and errors
	 * 
	 * @param callback
	 *            GeolocationCallback
	 */
	public Geolocation(GeolocationCallback callback) {
		geoCallback = callback;
	}

	/**
	 * Get the user geolocation
	 */
	public native void getGeoLocation() /*-{
		if(null == $wnd.navigator.geolocation) {
		return;
		}

		$wnd.navigator.geolocation.getCurrentPosition(
		@com.binomed.cst.client.geolocation.Geolocation::geoLocationCallback(Lcom/binomed/cst/client/geolocation/Position;),
		@com.binomed.cst.client.geolocation.Geolocation::geoLocationCallbackError(Lcom/google/gwt/core/client/JavaScriptObject;),
		{enableHighAccuracy:true, maximumAge:60000});
	}-*/;

	/**
	 * Execute callback when the location is returned
	 * 
	 * @param position
	 *            Location position
	 */
	private static void geoLocationCallback(Position position) {
		geoCallback.onLocation(position);
	}

	/**
	 * Execute callback when an error occured during geolocation
	 * 
	 * @param jso
	 *            Error returned
	 */
	private static void geoLocationCallbackError(JavaScriptObject jso) {
		geoCallback.onError(jso);
	}

}
