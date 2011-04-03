package com.binomed.cst.client.geolocation;

import com.google.gwt.core.client.JavaScriptObject;

public class Geolocation {

	private static GeolocationCallback geoCallback;

	public Geolocation(GeolocationCallback callback) {
		geoCallback = callback;
	}

	public native void getGeoLocation() /*-{
		if(null == $wnd.navigator.geolocation) {
		return;
		}

		$wnd.navigator.geolocation.getCurrentPosition(
		@com.binomed.cst.client.geolocation.Geolocation::geoLocationCallback(Lcom/binomed/cst/client/geolocation/Position;),
		@com.binomed.cst.client.geolocation.Geolocation::geoLocationCallbackError(Lcom/google/gwt/core/client/JavaScriptObject;),
		{enableHighAccuracy:true, maximumAge:60000});
	}-*/;

	public static void geoLocationCallback(Position position) {
		geoCallback.onLocation(position);
	}

	private static void geoLocationCallbackError(JavaScriptObject jso) {
		geoCallback.onError(jso);
	}

}
