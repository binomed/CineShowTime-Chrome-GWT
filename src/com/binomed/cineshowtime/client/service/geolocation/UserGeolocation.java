package com.binomed.cineshowtime.client.service.geolocation;

import com.binomed.cineshowtime.client.cst.GoogleKeys;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;

public class UserGeolocation {

	private static UserGeolocation instance;

	public static synchronized UserGeolocation getInstance() {
		if (UserGeolocation.instance == null) {
			instance = new UserGeolocation();
		}
		return instance;
	}

	public void getUserGeolocation(final UserGeolocationCallback callback) {
		// Asynchronously loads the Maps API.
		Maps.loadMapsApi(GoogleKeys.GOOGLE_MAPS_KEY, "2", false, new Runnable() {
			@Override
			public void run() {
				final Geocoder geocoder = new Geocoder();
				// Get geolocation of the user
				Geolocation geolocation = new Geolocation(new GeolocationCallback() {
					@Override
					public void onLocation(Position position) {
						final LatLng latLng = LatLng.newInstance(position.getLatitude(), position.getLongitude());
						geocoder.getLocations(latLng, new LocationCallback() {
							@Override
							public void onSuccess(JsArray<Placemark> locations) {
								callback.onLocationResponse(locations, latLng);
							}

							@Override
							public void onFailure(int statusCode) {
								callback.onError();
							}
						});
					}

					@Override
					public void onError(JavaScriptObject jso) {
						callback.onError();
					}
				});
				geolocation.getGeoLocation();
			}
		});
	}

	public void getPlaceMark(final String address, final LocationCallback callBack) {
		// Asynchronously loads the Maps API.
		Maps.loadMapsApi(GoogleKeys.GOOGLE_MAPS_KEY, "2", false, new Runnable() {
			@Override
			public void run() {
				final Geocoder geocoder = new Geocoder();
				geocoder.getLocations(address, callBack);
			}
		});

	}
}
