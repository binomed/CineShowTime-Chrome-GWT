package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.service.ws.callback.NearTheatersRequestCallback;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT.create(MainWindowUiBinder.class);

	private IClientFactory clientFactory;

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	VerticalPanel paramsContent;
	@UiField
	VerticalPanel theatersContent;

	public MainWindow(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// Manage UI Style
		theatersContent.setSpacing(5);

		// Load intial content
		loadTheatersOfUserLocation();
	}

	public void addMovieTab(TheaterBean theater, String idMovie) {
		MovieView movieView = new MovieView(theater, idMovie);
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
				System.out.println("latitude=" + latLng.getLatitude() + ", longitude=" + latLng.getLongitude());
				loadTheaters(latLng.getLatitude(), latLng.getLongitude());
			}

			@Override
			public void onError() {
				Window.alert("Error during geolocation !");
			}
		});
	}

	private void loadTheaters(double lat, double lng) {
		CineShowTimeWS service = CineShowTimeWS.getInstance();
		service.requestNearTheatersFromLatLng(lat, lng, new NearTheatersRequestCallback() {
			// service.requestNearTheatersFromLatLng(47.216842, -1.556744, new NearTheatersRequestCallback() {
			@Override
			public void onNearResp(NearResp nearResp) {
				if (nearResp != null) {
					for (TheaterBean theater : nearResp.getTheaterList()) {
						theatersContent.add(new TheaterView(clientFactory, theater));
					}
				}
			}

			@Override
			public void onError(Throwable exception) {
				Window.alert("Error=" + exception.getMessage());
			}
		});
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
