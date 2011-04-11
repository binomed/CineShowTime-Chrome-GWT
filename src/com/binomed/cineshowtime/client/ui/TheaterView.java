package com.binomed.cineshowtime.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.cst.GoogleKeys;
import com.binomed.cineshowtime.client.cst.HttpParamsCst;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.service.ws.callback.ImdbRequestCallback;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.coverflow.IMovieOpen;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TheaterView extends Composite {

	private static TheaterViewUiBinder uiBinder = GWT.create(TheaterViewUiBinder.class);

	@UiField
	DisclosurePanel theaterPanel;
	@UiField
	Label theaterName;
	@UiField
	Label theaterPlace;
	@UiField
	Label theaterPhone;
	@UiField
	VerticalPanel theaterMap;
	@UiField
	VerticalPanel theaterCoverflow;

	private boolean firstOpen = true;

	public TheaterView(final TheaterBean theater, final Map<String, MovieBean> movies, final IMovieOpen movieOpenListener) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		HTML headerHtml = new HTML("<font color=\"#FFFFFF\">" + theater.getTheaterName() + "</font>");
		theaterPanel.setHeader(headerHtml);
		theaterPanel.setAnimationEnabled(true);
		theaterPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				if (firstOpen) {
					CineShowTimeWS service = CineShowTimeWS.getInstance();
					// Add the coverflow
					final Coverflow coverflow = new Coverflow(800, 300, theater, movieOpenListener);

					// Images url to load in the coverflow
					// final String[] imagesUrls = new String[] { "http://www.google.fr/movies/image?tbn=5fb488cff09bee9a&size=100x150", //
					// "http://www.google.fr/movies/image?tbn=6f9e9a2fd2f45c86&size=100x150", //
					// "http://www.google.fr/movies/image?tbn=272babc86fbdee70&size=100x150", //
					// "http://www.google.fr/movies/image?tbn=a6526f9c6231998c&size=100x150", //
					// "http://www.google.fr/movies/image?tbn=78ff8d3e5f3c2f93&size=100x150", //
					// "http://www.google.fr/movies/image?tbn=4456a070bd91e0f3&size=100x150", //
					// "http://www.google.fr/movies/image?tbn=afa72d7f8fb104f8&size=100x150" };
					final String[] imagesUrls = new String[theater.getMovieMap().size()];
					MovieBean movieTmp = null;
					int i = 0;
					Map<String, String> params = new HashMap<String, String>();
					final Map<String, Integer> mapMovieIndex = new HashMap<String, Integer>();
					// final String ip = InetAddress.getLocalHost().getHostAddress();
					final String ip = "193.253.198.44"; // TODO à débouchonner
					for (java.util.Map.Entry<String, List<ProjectionBean>> entryMovie : theater.getMovieMap().entrySet()) {
						movieTmp = service.getMovie(entryMovie.getKey());
						mapMovieIndex.put(entryMovie.getKey(), i);
						if (movieTmp == null) {
							movieTmp = movies.get(entryMovie.getKey());
							params.clear();
							params.put(HttpParamsCst.PARAM_IP, ip);
							params.put(HttpParamsCst.PARAM_MOVIE_CUR_LANG_NAME, URL.encode(movieTmp.getMovieName()));
							params.put(HttpParamsCst.PARAM_MOVIE_NAME, URL.encode(movieTmp.getEnglishMovieName()));
							params.put(HttpParamsCst.PARAM_LANG, "FR"); // TODO à débouchonner
							params.put(HttpParamsCst.PARAM_PLACE, URL.encode("Nantes"));// TODO à débouchonner
							imagesUrls[i] = CstResource.instance.no_poster().getURL();

							service.requestImdbInfo(params, movieTmp, new ImdbRequestCallback() {

								@Override
								public void onResponse(String response) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable exception) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onMovieResp(MovieBean movieBean) {
									coverflow.refresh(mapMovieIndex.get(movieBean.getId()), movieBean.getUrlImg());

								}
							});
						} else {
							imagesUrls[i] = movieTmp.getUrlImg();
						}
						i++;
					}
					coverflow.init(imagesUrls);
					theaterCoverflow.add(coverflow.getCanvas());

				}
			}
		});

		// Update theater informations
		theaterName.setText(theater.getTheaterName());
		theaterPhone.setText(theater.getPhoneNumber());
		if (theater.getPlace() != null) {
			theaterPlace.setText(theater.getPlace().getSearchQuery());

			// Asynchronously loads the Maps API.
			Maps.loadMapsApi(GoogleKeys.GOOGLE_MAPS_KEY, "2", false, new Runnable() {
				@Override
				public void run() {
					getAndBuildMapTheater(theater.getPlace().getSearchQuery());
				}
			});

		}
	}

	private void getAndBuildMapTheater(String address) {
		final Geocoder geocoder = new Geocoder();
		geocoder.getLocations(address, new LocationCallback() {
			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				if (locations != null && locations.length() > 0) {
					// Open a map centered on Theater
					final LatLng theaterLocalisation = locations.get(0).getPoint();
					final MapWidget map = new MapWidget(theaterLocalisation, 2);
					map.setSize("300px", "200px");
					map.setZoomLevel(16);
					// Add some controls for the zoom level
					map.addControl(new SmallMapControl());
					// Add a marker
					map.addOverlay(new Marker(theaterLocalisation));
					theaterMap.add(map);
				}
			}

			@Override
			public void onFailure(int statusCode) {
				// TODO Auto-generated method stub

			}
		});

	}

	interface TheaterViewUiBinder extends UiBinder<Widget, TheaterView> {
	}
}
