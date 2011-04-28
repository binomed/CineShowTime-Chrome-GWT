package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.ui.FavOpenEvent;
import com.binomed.cineshowtime.client.event.ui.SearchEvent;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class SearchTheater extends Composite {

	private static SearchTheaterUiBinder uiBinder = GWT.create(SearchTheaterUiBinder.class);

	@UiField
	DisclosurePanel searchPanel;
	@UiField
	TextBox locationSearch; // , movieSearch;
	@UiField
	DateBox dateSearch;

	private IClientFactory clientFactory;

	public void setClientFactory(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public SearchTheater() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		searchPanel.setHeader(new HTML("<font color=\"#FFFFFF\"><b>Rechercher</b></font>"));
		searchPanel.setAnimationEnabled(true);
		searchPanel.setOpen(true);

		DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
		dateSearch.setFormat(new DateBox.DefaultFormat(format));
	}

	@UiHandler("searchButton")
	void handleSearchClick(ClickEvent e) {
		clientFactory.getEventBusHandler().fireEvent(new SearchEvent());
		if (StringUtils.isNotEmpty(locationSearch.getText()) || dateSearch.getValue() != null) {
			long time = -1;
			if (dateSearch.getValue() != null) {
				time = dateSearch.getValue().getTime();
			}
			clientFactory.getCineShowTimeService().requestNearTheatersForSearch(locationSearch.getText(), time);
		} else {
			// TODO : Message
			Window.alert("Type a search criteria please.");
		}
	}

	@UiHandler("nearSearch")
	void handleNearSearchClick(ClickEvent e) {
		clientFactory.getEventBusHandler().fireEvent(new SearchEvent());
		loadTheatersOfUserLocation();
	}

	@UiHandler("favoriteSearch")
	void handleFavoriteSearchClick(ClickEvent e) {
		clientFactory.getEventBusHandler().fireEvent(new FavOpenEvent());
	}

	/**
	 * TODO : Refactorer avec MainWindow (code dupliqué)
	 */
	private void loadTheatersOfUserLocation() {
		UserGeolocation.getInstance().getUserGeolocation(new UserGeolocationCallback() {
			@Override
			public void onLocationResponse(JsArray<Placemark> locations) {
				if ((locations != null) && (locations.length() > 0)) {
					clientFactory.getCineShowTimeService().setCurrentCityName(locations.get(0));
				}
			}

			@Override
			public void onLatitudeLongitudeResponse(LatLng latLng) {
				// Load the favorites
				clientFactory.getCineShowTimeService().requestNearTheatersFromLatLng(latLng.getLatitude(), latLng.getLongitude());
			}

			@Override
			public void onError() {
				// TODO : Message
				Window.alert("Error during geolocation !");
			}
		});
	}

	interface SearchTheaterUiBinder extends UiBinder<Widget, SearchTheater> {
	}
}
