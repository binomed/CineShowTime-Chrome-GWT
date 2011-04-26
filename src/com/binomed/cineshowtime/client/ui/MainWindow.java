package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.service.NearRespNearEvent;
import com.binomed.cineshowtime.client.handler.service.NearRespHandler;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
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
	VerticalPanel theatersContent;
	Image imageLoading;

	public MainWindow(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// Manage UI Style
		theatersContent.setSpacing(5);

		imageLoading = new Image(CstResource.instance.movie_countdown());
		theatersContent.add(imageLoading);

		// Load intial content
		loadTheatersOfUserLocation();

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
			}

			@Override
			public void onLatitudeLongitudeResponse(LatLng latLng) {
				// Load the favorites
				clientFactory.getDataBaseHelper().getTheaterFav();
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
		// Define register to event
		clientFactory.getEventBusHandler().addHandler(NearRespNearEvent.TYPE, nearRespHandler);
		// Call the service
		service.requestNearTheatersFromLatLng(lat, lng, clientFactory.getLanguage());
	}

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

		}
	};

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
