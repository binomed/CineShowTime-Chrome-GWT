package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.cst.HttpParamsCst;
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
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.ui.dialog.AboutDialog;
import com.binomed.cineshowtime.client.ui.dialog.LastChangeDialog;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
import com.binomed.cineshowtime.client.util.StringUtils;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
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
	TheaterViewResult searchResultHeader;
	@UiField
	VerticalPanel theatersContent;
	@UiField
	Label menuAbout;

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

	private void loadTheatersOfUserLocation(final int day) {
		UserGeolocation.getInstance().getUserGeolocation(new UserGeolocationCallback() {
			@Override
			public void onLocationResponse(JsArray<Placemark> locations, LatLng latLng) {
				if ((locations != null) && (locations.length() > 0)) {
					clientFactory.getCineShowTimeService().setCurrentCityName(locations.get(0));
					clientFactory.getCineShowTimeService().requestNearTheatersFromLatLng(latLng.getLatitude(), latLng.getLongitude(), locations.get(0).getCountry(), day);
					searchResultHeader.setLocation(locations.get(0).getCity());
					searchResultHeader.updateResultHeader();
				} else {
					searchResultHeader.setError(I18N.instance.msgNoGps());
					searchResultHeader.updateResultHeader();
				}
			}

			@Override
			public void onError() {
				searchResultHeader.setError(I18N.instance.msgNoGps());
				searchResultHeader.updateResultHeader();
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
			searchResultHeader.setLoading(false);
			searchResultHeader.updateResultHeader();
			theatersContent.setVisible(true);
			// TODO : Message
			Window.alert("Error=" + error.getMessage());

		}

		@Override
		public void onNearResp(NearResp nearResp) {
			// Remove loading image
			searchResultHeader.setLoading(false);
			theatersContent.setVisible(true);
			// Remove previous theater list
			theatersContent.clear();
			// Display theaters
			if (nearResp != null) {
				List<TheaterBean> theaterList = nearResp.getTheaterList();
				String errorMsg = null;
				boolean error = false;
				if ((theaterList != null) && (theaterList.size() == 1)) {
					TheaterBean errorTheater = nearResp.getTheaterList().get(0);
					if (errorTheater.getId().equals(String.valueOf(HttpParamsCst.ERROR_WRONG_DATE))//
							|| errorTheater.getId().equals(String.valueOf(HttpParamsCst.ERROR_WRONG_PLACE)) //
							|| errorTheater.getId().equals(String.valueOf(HttpParamsCst.ERROR_NO_DATA)) //
							|| errorTheater.getId().equals(String.valueOf(HttpParamsCst.ERROR_CUSTOM_MESSAGE)) //
					) {
						error = true;
						switch (Integer.valueOf(errorTheater.getId())) {
						case HttpParamsCst.ERROR_WRONG_DATE:
							errorMsg = I18N.instance.msgNoDateMatch();
							break;
						case HttpParamsCst.ERROR_WRONG_PLACE:
							errorMsg = I18N.instance.msgNoPlaceMatch();
							break;
						case HttpParamsCst.ERROR_NO_DATA:
							errorMsg = I18N.instance.msgNoResultRetryLater();
							break;
						case HttpParamsCst.ERROR_CUSTOM_MESSAGE:
							errorMsg = errorTheater.getTheaterName();
							// Nothing to do special the custom message is in theaterTitle
							break;

						default:
							break;
						}
					}
				} else if ((theaterList == null) || (theaterList.size() == 0)) {
					error = true;
					errorMsg = I18N.instance.msgNoResultRetryLater();
				}
				if (!error) {

					searchResultHeader.setNbTheaters(nearResp.getTheaterList().size());
					for (TheaterBean theater : nearResp.getTheaterList()) {
						theatersContent.add(new TheaterView(clientFactory, theater));
					}
				} else {
					searchResultHeader.setError(errorMsg);

				}
				removeLastTheaterBorderStyle();
			} else {
				// TODO : Message
				Window.alert("No theater found !");
			}
			searchResultHeader.updateResultHeader();
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
					loadTheatersOfUserLocation(0);
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
			loadTheatersOfUserLocation(0);
		}

		@Override
		public void theaters(ArrayList<TheaterBean> theaterList, boolean isFav) {
			searchResultHeader.setLoading(false);
			if (!isFav) {
				clientFactory.getEventBusHandler().removeHandler(TheaterDBEvent.TYPE, theaterHandler);
				theatersContent.setVisible(true);
				if (theaterList != null) {
					searchResultHeader.setNbTheaters(theaterList.size());
					for (TheaterBean theater : theaterList) {
						theatersContent.add(new TheaterView(clientFactory, theater));
					}
				}
				removeLastTheaterBorderStyle();
			}
			searchResultHeader.updateResultHeader();
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
	@UiHandler("menuAbout")
	public void onClickAbout(ClickEvent e) {
		new AboutDialog().center();
	}

	private final FavOpenHandler favOpenHandler = new FavOpenHandler() {

		@Override
		public void onFavOpen() {
			ArrayList<TheaterBean> theaterFavList = clientFactory.getDataBaseHelper().getTheaterFavCache();
			if ((theaterFavList != null) && (theaterFavList.size() > 0)) {
				showTheaterFav(theaterFavList);
			} else {
				searchResultHeader.setError(I18N.instance.msgNoDFav());
				searchResultHeader.updateResultHeader();
			}

		}
	};

	private final SearchHandler searchHandler = new SearchHandler() {
		@Override
		public void onSearch(int searchType, String param) {
			initAndLoading();
			if (searchType == SearchEvent.SEARCH_NEAR) {
				loadTheatersOfUserLocation(0);
				searchResultHeader.setNear(true);
			} else if (searchType == SearchEvent.SEARCH_FAV) {
				searchResultHeader.setFavorite(true);
			} else if (searchType == SearchEvent.SEARCH_CINE) {
				searchResultHeader.setCineSearch(param);
			} else if (searchType == SearchEvent.SEARCH_DATE) {
				if (Integer.valueOf(param) != -1) {
					searchResultHeader.setDateSearch(param);
				} else {
					searchResultHeader.setError(I18N.instance.msgNoDateMatch());
				}
			}
			searchResultHeader.updateResultHeader();
		}
	};

	private void initAndLoading() {
		// Clean and init theater content list
		theatersContent.setVisible(false);
		theatersContent.clear();
		theatersContent.setSpacing(5);
		// Show image loading theaters
		searchResultHeader.setLoading(true);
		searchResultHeader.updateResultHeader();
	}

	/**
	 * Remove the last theater border down
	 */
	private void removeLastTheaterBorderStyle() {
		if (theatersContent.getWidgetCount() > 0) {
			Widget lastTheater = theatersContent.getWidget(theatersContent.getWidgetCount() - 1);
			if (lastTheater != null) {
				lastTheater.removeStyleName(CstResource.instance.css().theaterContent());
			}
		}
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
