package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.db.TheaterDBEvent;
import com.binomed.cineshowtime.client.event.service.NearRespNearEvent;
import com.binomed.cineshowtime.client.event.ui.FavOpenEvent;
import com.binomed.cineshowtime.client.event.ui.SearchEvent;
import com.binomed.cineshowtime.client.handler.db.TheaterDbHandler;
import com.binomed.cineshowtime.client.handler.service.NearRespHandler;
import com.binomed.cineshowtime.client.handler.ui.FavOpenHandler;
import com.binomed.cineshowtime.client.handler.ui.SearchHandler;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT.create(MainWindowUiBinder.class);

	private final IClientFactory clientFactory;

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	VerticalPanel paramsContent;
	@UiField
	SearchTheater searchField;
	@UiField
	VerticalPanel theatersContent;
	Image imageLoading;

	public MainWindow(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		searchField.setClientFactory(clientFactory);
		initAndLoading();
		initTheatersFav();

		// register to events
		this.clientFactory.getEventBusHandler().addHandler(SearchEvent.TYPE, searchHandler);
		this.clientFactory.getEventBusHandler().addHandler(FavOpenEvent.TYPE, favOpenHandler);
		this.clientFactory.getEventBusHandler().addHandler(NearRespNearEvent.TYPE, nearRespHandler);
	}

	private void initTheatersFav() {
		ArrayList<TheaterBean> theaterFavList = this.clientFactory.getDataBaseHelper().getTheaterFavCache();
		if (theaterFavList == null) {
			this.clientFactory.getEventBusHandler().addHandler(TheaterDBEvent.TYPE, theaterFavHandler);
			this.clientFactory.getDataBaseHelper().getTheaterFav();
		} else {
			showTheaterFav(theaterFavList);
		}
	}

	private void showTheaterFav(ArrayList<TheaterBean> theaterFavList) {
		initAndLoading();
		for (TheaterBean theaterFav : theaterFavList) {
			// Call the service
			clientFactory.getCineShowTimeService().requestNearTheatersFromCityName(
					theaterFav.getPlace().getCityName() + ", " + theaterFav.getPlace().getCountryNameCode(), theaterFav.getId());
		}
	}

	public void addMovieTab(TheaterBean theater, String idMovie) {
		MovieView movieView = new MovieView(theater, idMovie, clientFactory);
		appBodyPanel.add(movieView, new MovieTabHeaderWidget(movieView.getMovie().getMovieName(), movieView, appBodyPanel));
		appBodyPanel.selectTab(movieView);
	}

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
				// Save the user location
				clientFactory.setUserLocation(latLng);
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

	private final NearRespHandler nearRespHandler = new NearRespHandler() {

		@Override
		public void onError(Throwable error) {
			theatersContent.remove(imageLoading);
			// TODO : Message
			Window.alert("Error=" + error.getMessage());

		}

		@Override
		public void onNearResp(NearResp nearResp) {
			// Remove loading image
			theatersContent.remove(imageLoading);
			// Remove previous theater list
			theatersContent.clear();
			// Display theaters
			if (nearResp != null) {
				for (TheaterBean theater : nearResp.getTheaterList()) {
					theatersContent.add(new TheaterView(clientFactory, theater));
				}
			} else {
				// TODO : Message
				Window.alert("No theater found !");
			}

		}
	};

	private final TheaterDbHandler theaterFavHandler = new TheaterDbHandler() {

		@Override
		public void onError(DataServiceException exception) {
			clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterFavHandler);
			// TODO : Message
			Window.alert("Error=" + exception.getMessage());
		}

		@Override
		public void theaters(ArrayList<TheaterBean> theaterList) {
			clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterFavHandler);
			if ((theaterList != null) && (theaterList.size() > 0)) {
				showTheaterFav(theaterList);
			} else {
				// Load intial content
				loadTheatersOfUserLocation();
			}

		}

		@Override
		public void theater(TheaterBean theater) {
			// TODO Auto-generated method stub

		}
	};

	private final FavOpenHandler favOpenHandler = new FavOpenHandler() {

		@Override
		public void onFavOpen() {
			ArrayList<TheaterBean> theaterFavList = clientFactory.getDataBaseHelper().getTheaterFavCache();
			if ((theaterFavList != null) && (theaterFavList.size() > 0)) {
				showTheaterFav(theaterFavList);
			}

		}
	};

	private final SearchHandler searchHandler = new SearchHandler() {

		@Override
		public void onSearch() {
			initAndLoading();
		}
	};

	private void initAndLoading() {
		theatersContent.clear();
		theatersContent.setSpacing(5);
		if (imageLoading == null) {
			imageLoading = new Image(CstResource.instance.movie_countdown());
		}
		theatersContent.add(imageLoading);
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
