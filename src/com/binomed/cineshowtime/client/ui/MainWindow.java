package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.cst.HttpParamsCst;
import com.binomed.cineshowtime.client.events.IMovieResponse;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.service.ws.callback.ImdbRequestCallback;
import com.binomed.cineshowtime.client.service.ws.callback.NearTheatersRequestCallback;
import com.binomed.cineshowtime.client.ui.coverflow.ClickCoverListener;
import com.binomed.cineshowtime.client.ui.widget.MovieTabHeaderWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
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

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	VerticalPanel paramsContent;
	@UiField
	VerticalPanel theatersContent;

	public MainWindow() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// Manage UI Style
		theatersContent.setSpacing(5);

		// Load intial content
		loadTheatersOfUserLocation();
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
						theatersContent.add(new TheaterView(theater, nearResp.getMapMovies(), listener));
					}
				}
			}

			@Override
			public void onError(Throwable exception) {
				Window.alert("Error=" + exception.getMessage());
			}
		});
	}

	private final ClickCoverListener listener = new ClickCoverListener() {

		@Override
		public void onClickCover(TheaterBean theater, MovieBean movie) {
			CineShowTimeWS service = CineShowTimeWS.getInstance();
			movie = service.getMovie(movie.getId());
			ImdbRequestCallback callBack;
			final List<IMovieResponse> movieListener = new ArrayList<IMovieResponse>();
			if (movie == null) {

				callBack = new ImdbRequestCallback() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onMovieResp(MovieBean movieBean) {
						for (IMovieResponse listener : movieListener) {
							listener.movieResponse(movieBean);
						}
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(Throwable exception) {
						// TODO Auto-generated method stub

					}
				};
				Map<String, String> params = new HashMap<String, String>();
				params.clear();
				// final String ip = InetAddress.getLocalHost().getHostAddress();
				final String ip = "193.253.198.44"; // TODO à débouchonner
				params.put(HttpParamsCst.PARAM_IP, ip);
				params.put(HttpParamsCst.PARAM_MOVIE_CUR_LANG_NAME, URL.encode(movie.getMovieName()));
				params.put(HttpParamsCst.PARAM_MOVIE_NAME, URL.encode(movie.getEnglishMovieName()));
				params.put(HttpParamsCst.PARAM_LANG, "FR"); // TODO à débouchonner
				params.put(HttpParamsCst.PARAM_PLACE, URL.encode("Nantes"));// TODO à débouchonner
				params.put(HttpParamsCst.PARAM_ZIP, "true");
				params.put(HttpParamsCst.PARAM_MOVIE_ID, movie.getId());
				service.requestImdbInfo(params, movie, callBack);
			}
			MovieView movieView = new MovieView(theater, movie, movieListener);
			appBodyPanel.add(movieView, new MovieTabHeaderWidget(movie.getMovieName(), movieView, appBodyPanel));
			appBodyPanel.selectTab(movieView);

		}

	};

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
