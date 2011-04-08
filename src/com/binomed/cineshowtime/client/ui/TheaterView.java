package com.binomed.cineshowtime.client.ui;

import java.util.Map;

import com.binomed.cineshowtime.client.cst.GoogleKeys;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.coverflow.IMovieOpen;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TheaterView extends Composite {

	private static TheaterViewUiBinder uiBinder = GWT.create(TheaterViewUiBinder.class);

	@UiField
	DisclosurePanel theaterPanel;
	@UiField
	Label theaterName;
	@UiField
	Label theaterPlace;
	@UiField
	Label theaterPhone;
	@UiField
	VerticalPanel theaterMap;
	@UiField
	VerticalPanel theaterCoverflow;

	public TheaterView(final TheaterBean theater, Map<String, MovieBean> movies, IMovieOpen movieOpenListener) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		HTML headerHtml = new HTML("<font color=\"#FFFFFF\">" + theater.getTheaterName() + "</font>");
		theaterPanel.setHeader(headerHtml);
		theaterPanel.setAnimationEnabled(true);

		// Images url to load in the coverflow
		final String[] imagesUrls = new String[] { "http://www.google.fr/movies/image?tbn=5fb488cff09bee9a&size=100x150", //
				"http://www.google.fr/movies/image?tbn=6f9e9a2fd2f45c86&size=100x150", //
				"http://www.google.fr/movies/image?tbn=272babc86fbdee70&size=100x150", //
				"http://www.google.fr/movies/image?tbn=a6526f9c6231998c&size=100x150", //
				"http://www.google.fr/movies/image?tbn=78ff8d3e5f3c2f93&size=100x150", //
				"http://www.google.fr/movies/image?tbn=4456a070bd91e0f3&size=100x150", //
				"http://www.google.fr/movies/image?tbn=afa72d7f8fb104f8&size=100x150" };

		// Update theater informations
		theaterName.setText(theater.getTheaterName());
		theaterPhone.setText(theater.getPhoneNumber());
		if (theater.getPlace() != null) {
			theaterPlace.setText(theater.getPlace().getSearchQuery());

			// Asynchronously loads the Maps API.
			Maps.loadMapsApi(GoogleKeys.GOOGLE_MAPS_KEY, "2", false, new Runnable() {
				@Override
				public void run() {
					getAndBuildMapTheater(theater.getPlace().getSearchQuery());
				}
			});

			// Add the coverflow
			Coverflow coverflow = new Coverflow(800, 300, theater, movieOpenListener);
			coverflow.init(imagesUrls);
			theaterCoverflow.add(coverflow.getCanvas());
		}
	}

	private void getAndBuildMapTheater(String address) {
		final Geocoder geocoder = new Geocoder();
		geocoder.getLocations(address, new LocationCallback() {
			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				if (locations != null && locations.length() > 0) {
					// Open a map centered on Theater
					final LatLng theaterLocalisation = locations.get(0).getPoint();
					final MapWidget map = new MapWidget(theaterLocalisation, 2);
					map.setSize("300px", "200px");
					map.setZoomLevel(16);
					// Add some controls for the zoom level
					map.addControl(new SmallMapControl());
					// Add a marker
					map.addOverlay(new Marker(theaterLocalisation));
					theaterMap.add(map);
				}
			}

			@Override
			public void onFailure(int statusCode) {
				// TODO Auto-generated method stub

			}
		});

	}

	interface TheaterViewUiBinder extends UiBinder<Widget, TheaterView> {
	}
}
