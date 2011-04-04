package com.binomed.cst.client.geolocation;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;

public interface UserGeolocationCallback {

	void onLatitudeLongitudeResponse(LatLng latLng);

	void onLocationResponse(JsArray<Placemark> locations);

	void onError();

}
