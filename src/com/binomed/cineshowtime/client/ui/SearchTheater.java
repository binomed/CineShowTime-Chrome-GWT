package com.binomed.cineshowtime.client.ui;

import java.util.Date;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.ui.FavOpenEvent;
import com.binomed.cineshowtime.client.event.ui.SearchEvent;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.util.LocaleUtils;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.geocode.LocationCallback;
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
			UserGeolocation.getInstance().getPlaceMark(locationSearch.getText(), new LocationCallback() {

				@Override
				public void onSuccess(JsArray<Placemark> locations) {

					if (locations != null && locations.length() > 0) {
						doSearch(locations.get(0).getCountry());
					} else {
						doSearch(null);
					}
				}

				@Override
				public void onFailure(int statusCode) {
					doSearch(null);

				}

				private void doSearch(String lang) {
					int day = -1;
					if (dateSearch.getValue() != null) {
						Date currentTime = new Date();
						day = dateSearch.getValue().getDay() - currentTime.getDay();
						if (day < 0 || day > 7) {
							day = -1;
						}

					}
					clientFactory.getCineShowTimeService().requestNearTheatersFromCityName(locationSearch.getText(), null, day, lang != null ? lang : LocaleUtils.getLocale());
				}
			});

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
			public void onLocationResponse(JsArray<Placemark> locations, LatLng latLng) {
				if ((locations != null) && (locations.length() > 0)) {
					clientFactory.getCineShowTimeService().setCurrentCityName(locations.get(0));
					clientFactory.getCineShowTimeService().requestNearTheatersFromLatLng(latLng.getLatitude(), latLng.getLongitude(), locations.get(0).getCountry());
				} else {
					// TODO : Message
					Window.alert("Error during geolocation !");
				}
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
