package com.binomed.cst.client.ui;

import com.binomed.cst.client.ui.coverflow.Coverflow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.SmallMapControl;
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

	public TheaterView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		theaterPanel.setHeader(new HTML("Nom du cinéma - Lieu"));
		theaterPanel.setAnimationEnabled(true);
		theaterPanel.setOpen(true);

		// Images url to load in the coverflow
		final String[] imagesUrls = new String[] { "http://www.google.fr/movies/image?tbn=5fb488cff09bee9a&size=100x150", //
				"http://www.google.fr/movies/image?tbn=6f9e9a2fd2f45c86&size=100x150", //
				"http://www.google.fr/movies/image?tbn=272babc86fbdee70&size=100x150", //
				"http://www.google.fr/movies/image?tbn=a6526f9c6231998c&size=100x150", //
				"http://www.google.fr/movies/image?tbn=78ff8d3e5f3c2f93&size=100x150", //
				"http://www.google.fr/movies/image?tbn=4456a070bd91e0f3&size=100x150", //
				"http://www.google.fr/movies/image?tbn=afa72d7f8fb104f8&size=100x150" };

		// Update theater informations
		theaterName.setText("Multiplexe Gaumont Nantes");
		theaterPlace.setText("12 place du Commerce, 44000 Nantes");
		theaterPhone.setText("08 92 68 75 55");

		// Asynchronously loads the Maps API.
		Maps.loadMapsApi("MapKeyHere!!!", "2", false, new Runnable() {
			@Override
			public void run() {
				buildMapUi();
			}
		});

		// Add the coverflow
		Coverflow coverflow = new Coverflow(800, 300);
		coverflow.init(imagesUrls);
		theaterCoverflow.add(coverflow.getCanvas());
	}

	private void buildMapUi() {
		// Open a map centered on Theater
		LatLng theaterLocalisation = LatLng.newInstance(47.212973, -1.558114);
		final MapWidget map = new MapWidget(theaterLocalisation, 2);
		map.setSize("300px", "200px");
		map.setZoomLevel(16);
		// Add some controls for the zoom level
		map.addControl(new SmallMapControl());
		// Add a marker
		map.addOverlay(new Marker(theaterLocalisation));
		theaterMap.add(map);
	}

	interface TheaterViewUiBinder extends UiBinder<Widget, TheaterView> {
	}
}
