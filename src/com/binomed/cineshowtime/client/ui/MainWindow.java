package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.Date;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.db.DataBaseReadyDBEvent;
import com.binomed.cineshowtime.client.event.db.LastChangeDBEvent;
import com.binomed.cineshowtime.client.event.db.LastRequestDBEvent;
import com.binomed.cineshowtime.client.event.db.TheaterDBEvent;
import com.binomed.cineshowtime.client.event.service.NearRespNearEvent;
import com.binomed.cineshowtime.client.event.ui.FavOpenEvent;
import com.binomed.cineshowtime.client.event.ui.SearchEvent;
import com.binomed.cineshowtime.client.handler.db.DataBaseReadyHandler;
import com.binomed.cineshowtime.client.handler.db.LastChangeHandler;
import com.binomed.cineshowtime.client.handler.db.LastRequestHandler;
import com.binomed.cineshowtime.client.handler.db.TheaterDbHandler;
import com.binomed.cineshowtime.client.handler.service.NearRespHandler;
import com.binomed.cineshowtime.client.handler.ui.FavOpenHandler;
import com.binomed.cineshowtime.client.handler.ui.SearchHandler;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.RequestBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.ui.dialog.LastChangeDialog;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
import com.binomed.cineshowtime.client.util.StringUtils;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT.create(MainWindowUiBinder.class);

	private final IClientFactory clientFactory;

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	ParameterView paramsContent;
	@UiField
	SearchTheater searchField;
	@UiField
	VerticalPanel theatersContent;
	SimplePanel imageLoadingPanel;

	public MainWindow(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		appBodyPanel.addStyleName(CstResource.instance.css().tabPanel());
		searchField.setClientFactory(clientFactory);
		paramsContent.setClientFactory(clientFactory);
		initAndLoading();
		// register to events

		if (this.clientFactory.getDataBaseHelper().isDataBaseReady()) {
			clientFactory.getDataBaseHelper().getLastRequest();
			clientFactory.getEventBusHandler().addHandler(LastRequestDBEvent.TYPE, lastRequestHandler);
		} else {
			this.clientFactory.getEventBusHandler().addHandler(DataBaseReadyDBEvent.TYPE, dataReadyHandler);
		}
		if (this.clientFactory.getDataBaseHelper().isShowLastChange()) {
			new LastChangeDialog().center();
		} else {
			this.clientFactory.getEventBusHandler().addHandler(LastChangeDBEvent.TYPE, lastChangeHandler);
		}

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
		// Call the service
		clientFactory.getCineShowTimeService().requestNearTheatersFromFav(theaterFavList, 0);
	}

	public void addMovieTab(TheaterBean theater, String idMovie) {
		int index = getMovieTabIfExist(idMovie);
		if (index == -1) {
			MovieView movieView = new MovieView(theater, idMovie, clientFactory);
			appBodyPanel.add(movieView, new MovieTabHeaderWidget(movieView.getMovie().getMovieName(), movieView, appBodyPanel));
			appBodyPanel.selectTab(movieView);
		} else {
			appBodyPanel.selectTab(index);
		}
	}

	private int getMovieTabIfExist(String idMovie) {
		for (int i = 0; i < appBodyPanel.getWidgetCount(); i++) {
			if ((appBodyPanel.getWidget(i) instanceof MovieView //
					)
					&& StringUtils.equalsIC(((MovieView) appBodyPanel.getWidget(i)).getIdMovie(), idMovie)) {
				return i;
			}
		}
		return -1;
	}

	private void loadTheatersOfUserLocation() {

		UserGeolocation.getInstance().getUserGeolocation(new UserGeolocationCallback() {
			@Override
			public void onLocationResponse(JsArray<Placemark> locations, LatLng latLng) {
				if ((locations != null) && (locations.length() > 0)) {
					clientFactory.getCineShowTimeService().setCurrentCityName(locations.get(0));
					clientFactory.getCineShowTimeService().requestNearTheatersFromLatLng(latLng.getLatitude(), latLng.getLongitude(), locations.get(0).getCountry(), 0);
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
			theatersContent.remove(imageLoadingPanel);
			// TODO : Message
			Window.alert("Error=" + error.getMessage());

		}

		@Override
		public void onNearResp(NearResp nearResp) {
			// Remove loading image
			theatersContent.remove(imageLoadingPanel);
			// Remove previous theater list
			theatersContent.clear();
			// Display theaters
			if (nearResp != null) {
				for (TheaterBean theater : nearResp.getTheaterList()) {
					theatersContent.add(new TheaterView(clientFactory, theater));
				}
				removeLastTheaterBorderStyle();
			} else {
				// TODO : Message
				Window.alert("No theater found !");
			}
		}
	};

	/*
	 * 
	 * Database Handlers
	 */

	private final TheaterDbHandler theaterFavHandler = new TheaterDbHandler() {

		@Override
		public void onError(DataServiceException exception) {
			clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterFavHandler);
			// TODO : Message
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
			// In case of error, we launch the request from user position
			loadTheatersOfUserLocation();
		}

		@Override
		public void theaters(ArrayList<TheaterBean> theaterList, boolean isFav) {
			if (!isFav) {
				clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterHandler);
				theatersContent.remove(imageLoadingPanel);
				if (theaterList != null) {
					for (TheaterBean theater : theaterList) {
						theatersContent.add(new TheaterView(clientFactory, theater));
					}
				}
				removeLastTheaterBorderStyle();
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
			clientFactory.getCineShowTimeService().setRequest(request);
			boolean relaunchRequest = false;
			if ((request == null) || request.isNullResult()) {
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
				clientFactory.getDataBaseHelper().getTheatersAndMovies();
			}

		}
	};

	private final LastChangeHandler lastChangeHandler = new LastChangeHandler() {

		@Override
		public void onLastChange() {
			new LastChangeDialog().center();

		}
	};

	private final DataBaseReadyHandler dataReadyHandler = new DataBaseReadyHandler() {

		@Override
		public void dataBaseReady() {
			clientFactory.getDataBaseHelper().getLastRequest();
			clientFactory.getEventBusHandler().addHandler(LastRequestDBEvent.TYPE, lastRequestHandler);

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

	private final SearchHandler searchHandler = new SearchHandler() {
		@Override
		public void onSearch() {
			initAndLoading();
		}
	};

	private void initAndLoading() {
		// Clean and init theater content list
		theatersContent.clear();
		theatersContent.setSpacing(5);

		// Show header

		// Show image loading theaters
		if (imageLoadingPanel == null) {
			imageLoadingPanel = new SimplePanel();
			Image loadingImg = new Image(CstResource.instance.movie_countdown());
			loadingImg.addStyleName(CstResource.instance.css().center());
			imageLoadingPanel.add(loadingImg);
			imageLoadingPanel.setWidth("100%");
			imageLoadingPanel.addStyleName(CstResource.instance.css().center());
		}
		theatersContent.add(imageLoadingPanel);
	}

	/**
	 * Remove the last theater border down
	 */
	private void removeLastTheaterBorderStyle() {
		Widget lastTheater = theatersContent.getWidget(theatersContent.getWidgetCount() - 1);
		if (lastTheater != null) {
			lastTheater.removeStyleName(CstResource.instance.css().theaterContent());
		}
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
