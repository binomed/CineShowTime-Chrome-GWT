package com.binomed.cineshowtime.client.ui.dialog;

import com.binomed.cineshowtime.client.cst.GoogleKeys;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.DialogBox;

public class MapDialog extends DialogBox {

	private final static String WIDTH = "600px";
	private final static String HEIGHT = "500px";

	public MapDialog(final String name, final String address) {
		setText(name);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setSize(WIDTH, HEIGHT);
		setAutoHideEnabled(true);

		// Asynchronously loads the Maps API.
		Maps.loadMapsApi(GoogleKeys.GOOGLE_MAPS_KEY, "2", false, new Runnable() {
			@Override
			public void run() {
				getAndBuildMapTheater(address);
			}
		});
	}

	private void getAndBuildMapTheater(final String address) {
		final Geocoder geocoder = new Geocoder();
		geocoder.getLocations(address, new LocationCallback() {
			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				if (locations != null && locations.length() > 0) {
					// Open a map centered on Theater
					final LatLng theaterLocalisation = locations.get(0).getPoint();
					final MapWidget map = new MapWidget(theaterLocalisation, 2);
					map.setSize(WIDTH, HEIGHT);
					map.setZoomLevel(16);
					// Add some controls for the zoom level
					map.addControl(new SmallMapControl());
					// Add a marker
					map.addOverlay(new Marker(theaterLocalisation));
					// Add an info window to highlight a point of interest
					map.getInfoWindow().open(map.getCenter(), new InfoWindowContent(address));
					setWidget(map);
				}
			}

			@Override
			public void onFailure(int statusCode) {
				// TODO Auto-generated method stub

			}
		});

	}

}
