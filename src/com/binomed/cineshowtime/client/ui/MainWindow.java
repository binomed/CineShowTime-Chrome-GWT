package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.Date;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.db.LastRequestDBEvent;
import com.binomed.cineshowtime.client.event.db.TheaterDBEvent;
import com.binomed.cineshowtime.client.event.service.NearRespNearEvent;
import com.binomed.cineshowtime.client.event.ui.FavOpenEvent;
import com.binomed.cineshowtime.client.handler.db.LastRequestHandler;
import com.binomed.cineshowtime.client.handler.db.TheaterDbHandler;
import com.binomed.cineshowtime.client.handler.service.NearRespHandler;
import com.binomed.cineshowtime.client.handler.ui.FavOpenHandler;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.RequestBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
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
	@UiField
	Button cleanBtn;

	public MainWindow(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// Manage UI Style
		theatersContent.setSpacing(5);
		imageLoading = new Image(CstResource.instance.movie_countdown());
		theatersContent.add(imageLoading);

		// Manage intialization

		searchField.setClientFactory(clientFactory);

		// register to events
		this.clientFactory.getEventBusHandler().addHandler(FavOpenEvent.TYPE, favOpenHandler);
		this.clientFactory.getEventBusHandler().addHandler(NearRespNearEvent.TYPE, nearRespHandler);
		this.clientFactory.getDataBaseHelper().getLastRequest();
		this.clientFactory.getEventBusHandler().addHandler(LastRequestDBEvent.TYPE, lastRequestHandler);
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
		theatersContent.clear();
		// Manage UI Style
		theatersContent.setSpacing(5);
		imageLoading = new Image(CstResource.instance.movie_countdown());
		theatersContent.add(imageLoading);
		for (TheaterBean theaterFav : theaterFavList) {
			// clientFactory.getDataBaseHelper().removeFav(theaterFav);
			// Call the service
			clientFactory.getCineShowTimeService().requestNearTheatersFromCityName(theaterFav.getPlace().getCityName() + ", " + theaterFav.getPlace().getCountryNameCode(), theaterFav.getId(), clientFactory.getLanguage());
		}
	}

	public void addMovieTab(TheaterBean theater, String idMovie) {
		MovieView movieView = new MovieView(theater, idMovie, clientFactory);
		appBodyPanel.add(movieView, new MovieTabHeaderWidget(movieView.getMovie().getMovieName(), movieView, appBodyPanel));
		appBodyPanel.addStyleName("gwt-TabPanel");
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
				// Load the favorites
				loadTheaters(latLng.getLatitude(), latLng.getLongitude());
			}

			@Override
			public void onError() {
				Window.alert("Error during geolocation !");
			}
		});
	}

	private void loadTheaters(double lat, double lng) {
		CineShowTimeWS service = clientFactory.getCineShowTimeService();
		// Call the service
		service.requestNearTheatersFromLatLng(lat, lng, clientFactory.getLanguage());
	}

	/*
	 * ***
	 * HANDLERS PARTS ****
	 */

	/*
	 * 
	 * Service Handlers
	 */

	private final NearRespHandler nearRespHandler = new NearRespHandler() {

		@Override
		public void onError(Throwable error) {
			theatersContent.remove(imageLoading);
			Window.alert("Error=" + error.getMessage());

		}

		@Override
		public void onNearResp(NearResp nearResp) {
			theatersContent.remove(imageLoading);
			if (nearResp != null) {
				for (TheaterBean theater : nearResp.getTheaterList()) {
					theatersContent.add(new TheaterView(clientFactory, theater));
				}
			}
			clientFactory.getDataBaseHelper().writeNearResp(nearResp);

		}
	};

	/*
	 * 
	 * Database Handlers
	 */

	private final TheaterDbHandler theaterFavHandler = new TheaterDbHandler() {

		@Override
		public void onError(DataServiceException exception) {
			// TODO Auto-generated method stub
			clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterFavHandler);
			Window.alert("Error=" + exception.getMessage());
		}

		@Override
		public void theaters(ArrayList<TheaterBean> theaterList, boolean isFav) {
			if (isFav) {
				clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterFavHandler);
				if ((theaterList != null) && (theaterList.size() > 0)) {
					showTheaterFav(theaterList);
				} else {
					// Load intial content
					loadTheatersOfUserLocation();
				}
			}

		}

		@Override
		public void theater(TheaterBean theater) {
			// TODO Auto-generated method stub

		}
	};

	private final TheaterDbHandler theaterHandler = new TheaterDbHandler() {

		@Override
		public void onError(DataServiceException exception) {
			Window.alert("Error=" + exception.getMessage());
		}

		@Override
		public void theaters(ArrayList<TheaterBean> theaterList, boolean isFav) {
			if (!isFav) {
				clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterHandler);
				theatersContent.remove(imageLoading);
				if (theaterList != null) {
					for (TheaterBean theater : theaterList) {
						theatersContent.add(new TheaterView(clientFactory, theater));
					}
				}
			}
		}

		@Override
		public void theater(TheaterBean theater) {
		}
	};

	private final LastRequestHandler lastRequestHandler = new LastRequestHandler() {

		@Override
		public void onLastRequest(RequestBean request) {
			clientFactory.getEventBusHandler().removeHandler(LastRequestDBEvent.TYPE, lastRequestHandler);
			boolean relaunchRequest = false;
			if (request.isNullResult()) {
				relaunchRequest = true;
			} else {
				Date curentTime = new Date();
				if ((curentTime.getDay() != request.getTime().getDay() //
						)
						|| (curentTime.getMonth() != request.getTime().getMonth() //
						) || (curentTime.getYear() != request.getTime().getYear() //
						)) {
					relaunchRequest = true;
				}
			}

			if (relaunchRequest) {
				initTheatersFav();
			} else {
				clientFactory.getEventBusHandler().addHandler(TheaterDBEvent.TYPE, theaterHandler);
				clientFactory.getDataBaseHelper().getMovies();
				clientFactory.getDataBaseHelper().getTheaters();
			}

		}
	};

	/*
	 * 
	 * UI Handlers
	 */

	private final FavOpenHandler favOpenHandler = new FavOpenHandler() {

		@Override
		public void onFavOpen() {
			ArrayList<TheaterBean> theaterFavList = clientFactory.getDataBaseHelper().getTheaterFavCache();
			if ((theaterFavList != null) && (theaterFavList.size() > 0)) {
				showTheaterFav(theaterFavList);
			}

		}
	};

	@UiHandler("cleanBtn")
	public void onCleanDataBase(ClickEvent event) {
		clientFactory.getDataBaseHelper().clean();
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
