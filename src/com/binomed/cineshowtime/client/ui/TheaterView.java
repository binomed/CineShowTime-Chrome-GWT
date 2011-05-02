package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.service.MovieLoadedEvent;
import com.binomed.cineshowtime.client.event.ui.TheaterOpenEvent;
import com.binomed.cineshowtime.client.handler.service.ImdbRespHandler;
import com.binomed.cineshowtime.client.handler.ui.TheaterOpenHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.coverflow.CoverData;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.coverflow.event.ClickCoverListener;
import com.binomed.cineshowtime.client.ui.coverflow.layout.BetterCoverflowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TheaterView extends Composite {

	private static TheaterViewUiBinder uiBinder = GWT.create(TheaterViewUiBinder.class);

	private final IClientFactory clientFactory;
	private final TheaterBean theater;

	private boolean isCoverflowLoaded = false;
	private boolean hasRegister = false;

	@UiField
	DisclosurePanel theaterPanel;
	@UiField(provided = true)
	TheaterViewHeader theaterHeader;
	@UiField
	SimplePanel theaterCoverflow;
	private Coverflow coverflow;

	public TheaterView(final IClientFactory clientFactory, final TheaterBean theater) {
		this.clientFactory = clientFactory;
		this.theater = theater;
		clientFactory.getEventBusHandler().addHandler(TheaterOpenEvent.TYPE, eventHandler);

		// Create theater header
		theaterHeader = new TheaterViewHeader(clientFactory, theater);

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.addStyleName(CstResource.instance.css().theaterContent());

		// Update theater informations
		theaterPanel.setAnimationEnabled(true);
		theaterPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				if (!isCoverflowLoaded) {
					isCoverflowLoaded = true;

					// Add the coverflow
					coverflow = new Coverflow(800, 300, new BetterCoverflowLayout());
					coverflow.addClickCoverListener(movieOpenListener);

					CineShowTimeWS service = clientFactory.getCineShowTimeService();
					// Images url to load in the coverflow
					final List<CoverData> coversData = new ArrayList<CoverData>();
					MovieBean movieTmp = null;
					int i = 0;
					Map<String, String> params = new HashMap<String, String>();
					final Map<String, Integer> mapMovieIndex = new HashMap<String, Integer>();
					for (java.util.Map.Entry<String, List<ProjectionBean>> entryMovie : theater.getMovieMap().entrySet()) {
						movieTmp = service.getMovie(entryMovie.getKey());
						mapMovieIndex.put(entryMovie.getKey(), i);
						if (movieTmp.getState() == MovieBean.STATE_NONE) {
							params.clear();
							// Register to event
							if (!hasRegister) {
								hasRegister = true;
								clientFactory.getEventBusHandler().addHandler(MovieLoadedEvent.TYPE, eventHandler);
							}
							// call the service
							UserGeolocation.getInstance().getPlaceMark(theater.getPlace().getSearchQuery(),
									new TheaterLocationCallBack(movieTmp, theater, clientFactory));
						} else if (movieTmp.getState() == MovieBean.STATE_IN_PROGRESS) {
							// Register the service
							if (!hasRegister) {
								hasRegister = true;
								clientFactory.getEventBusHandler().addHandler(MovieLoadedEvent.TYPE, eventHandler);
							}
						}
						coversData.add(movieTmp);
						i++;
					}
					coverflow.init(coversData);
					theaterCoverflow.add(coverflow.getCanvas());
				}
			}
		});

	}

	/** Used by MyUiBinder to instantiate CricketScores */
	@UiFactory
	TheaterViewHeader makeTheaterHeader() { // method name is insignificant
		return new TheaterViewHeader(clientFactory, theater);
	}

	private final ClickCoverListener movieOpenListener = new ClickCoverListener() {
		@Override
		public void onClickCover(String idMovie) {
			clientFactory.getMainWindow().addMovieTab(theater, idMovie);
		}

	};

	static class TheaterLocationCallBack implements LocationCallback {

		private final MovieBean movieTmp;
		private final TheaterBean theater;
		private final IClientFactory clientFactory;

		public TheaterLocationCallBack(MovieBean movieTmp, TheaterBean theater, IClientFactory clientFactory) {
			super();
			this.movieTmp = movieTmp;
			this.theater = theater;
			this.clientFactory = clientFactory;
		}

		@Override
		public void onSuccess(JsArray<Placemark> locations) {
			if ((locations != null) && (locations.length() > 0)) {
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
			final String ip = "193.253.198.44";
			clientFactory.getCineShowTimeService().requestImdbInfo(movieTmp, ip, lang != null ? lang : clientFactory.getLanguage(),
					theater.getPlace().getSearchQuery(), theater.getId());
		}
	}

	class TheaterHandler implements ImdbRespHandler, TheaterOpenHandler {

		@Override
		public void onTheaterOpen(String source) {
			if (theater.getId().equals(source)) {
				theaterPanel.setOpen(true);
			}
		}

		@Override
		public void onTheaterClose(String source) {

			if (theater.getId().equals(source)) {
				theaterPanel.setOpen(false);
			}
		}

		@Override
		public void onMovieLoad(MovieBean movieBean, String source) {
			if (theater.getId().equals(source)) {
				coverflow.updateCover(movieBean.getId(), movieBean.getUrlImg());
			}
		}

		@Override
		public void onError(Throwable exception, String source) {
			// TODO Auto-generated method stub

		}

	}

	private final TheaterHandler eventHandler = new TheaterHandler();

	interface TheaterViewUiBinder extends UiBinder<Widget, TheaterView> {
	}
}
