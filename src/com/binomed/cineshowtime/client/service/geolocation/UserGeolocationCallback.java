package com.binomed.cineshowtime.client.service.geolocation;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;

public interface UserGeolocationCallback {

	void onLocationResponse(JsArray<Placemark> locations, LatLng latLng);

	void onError();

}
