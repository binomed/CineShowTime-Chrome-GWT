package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.Date;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.ui.FavOpenEvent;
import com.binomed.cineshowtime.client.event.ui.SearchEvent;
import com.binomed.cineshowtime.client.model.RequestBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.util.LocaleUtils;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class SearchTheater extends Composite {

	private static SearchTheaterUiBinder uiBinder = GWT.create(SearchTheaterUiBinder.class);

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

		DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
		dateSearch.setFormat(new DateBox.DefaultFormat(format));
		dateSearch.setValue(new Date());
	}

	@UiHandler("locationSearch")
	void handleSearchEnter(KeyPressEvent e) {
		if (e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
			doSearch();
		}
	}

	@UiHandler("searchButton")
	void handleSearchClick(ClickEvent e) {
		doSearch();
	}

	public void doSearch() {
		if (StringUtils.isNotEmpty(locationSearch.getText()) || (dateSearch.getValue() != null)) {
			// If the city is empty, then we want the result for same request but for another day
			if (StringUtils.isEmpty(locationSearch.getText())) {
				int day = getDaySearch();
				// if date is not -1 we launch the last request we the new day
				if (day != -1) {
					RequestBean lastRequest = clientFactory.getCineShowTimeService().getRequest();
					if (lastRequest != null) {
						if (lastRequest.isFavSearch()) {
							ArrayList<TheaterBean> theaterFavList = clientFactory.getDataBaseHelper().getTheaterFavCache();
							clientFactory.getCineShowTimeService().requestNearTheatersFromFav(theaterFavList, day);
						} else if ((lastRequest.getLongitude() != 0) && (lastRequest.getLatitude() != 0)) {
							clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_DATE, String.valueOf(day)));
							loadTheaterOfUserCityEnter(lastRequest.getCityName());
						} else {
							clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_CINE, locationSearch.getText()));
							loadTheaterOfUserCityEnter(lastRequest.getCityName());
						}
					} else {
						Window.alert(I18N.instance.searchError());
					}

				} else {
					clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_NEAR, String.valueOf(day)));
					// TODO : Message d'erreur de date
				}
			} else {
				int day = getDaySearch();
				// if date is not -1 we launch the last request we the new day
				if (day != -1) {
					clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_DATE, String.valueOf(day)));
					loadTheaterOfUserCityEnter(locationSearch.getText());
				} else {
					clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_CINE, locationSearch.getText()));
					loadTheaterOfUserCityEnter(locationSearch.getText());
				}
			}

		} else {
			// TODO : Message
			Window.alert(I18N.instance.searchError());
		}
	}

	public int getDaySearch() {
		int day = -1;
		if (dateSearch.getValue() != null) {
			Date currentTime = new Date();
			int dayCurrent = Integer.valueOf(DateTimeFormat.getFormat("dd").format(currentTime));
			int daySearch = Integer.valueOf(DateTimeFormat.getFormat("dd").format(dateSearch.getValue()));
			day = daySearch - dayCurrent;
			if ((day < 0) || (day > 7)) {
				day = -1;
			}

		} else {
			day = 0;
		}
		return day;
	}

	public void loadTheaterOfUserCityEnter(final String cityName) {
		UserGeolocation.getInstance().getPlaceMark(cityName, new LocationCallback() {

			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				try {
					if ((locations != null) && (locations.length() > 0)) {
						clientFactory.getCineShowTimeService().setCurrentCityName(locations.get(0), null);
						doSearch(locations.get(0).getCountry());
					} else {
						clientFactory.getCineShowTimeService().setCurrentCityName(null, cityName);
						doSearch(null);
					}
				} catch (Exception e) {
					clientFactory.getCineShowTimeService().setCurrentCityName(null, cityName);
					doSearch(null);
				}
			}

			@Override
			public void onFailure(int statusCode) {
				clientFactory.getCineShowTimeService().setCurrentCityName(null, cityName);
				doSearch(null);

			}

			private void doSearch(String lang) {
				int day = getDaySearch();
				clientFactory.getCineShowTimeService().requestNearTheatersFromCityName(cityName, day, lang != null ? lang : LocaleUtils.getLocale());
			}

		});
	}

	@UiHandler("nearSearch")
	void handleNearSearchClick(ClickEvent e) {
		clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_NEAR, null));
	}

	@UiHandler("favoriteSearch")
	void handleFavoriteSearchClick(ClickEvent e) {
		clientFactory.getEventBusHandler().fireEvent(new SearchEvent(SearchEvent.SEARCH_FAV, null));
		clientFactory.getEventBusHandler().fireEvent(new FavOpenEvent());
	}

	interface SearchTheaterUiBinder extends UiBinder<Widget, SearchTheater> {
	}

	public void setUserLocation(String city) {
		locationSearch.setText(city);
	}
}
