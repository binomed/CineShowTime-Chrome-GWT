package com.binomed.cineshowtime.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.cst.HttpParamsCst;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.service.ws.callback.ImdbRequestCallback;
import com.binomed.cineshowtime.client.ui.coverflow.ClickCoverListener;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.dialog.MapDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TheaterView extends Composite {

	private static TheaterViewUiBinder uiBinder = GWT.create(TheaterViewUiBinder.class);

	private final TheaterBean theater;
	private final Coverflow coverflow;

	@UiField
	DisclosurePanel theaterPanel;
	@UiField
	Label theaterPlace;
	@UiField
	Label theaterPhone;
	@UiField
	VerticalPanel theaterCoverflow;

	public TheaterView(final TheaterBean theater, final Map<String, MovieBean> movies, final ClickCoverListener movieOpenListener) {
		this.theater = theater;
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Add the coverflow
		coverflow = new Coverflow(800, 300, theater);
		coverflow.addClickCoverListener(movieOpenListener);
		// Disclosure panel
		HTML headerHtml = new HTML("<font color=\"#FFFFFF\">" + theater.getTheaterName() + "</font>");
		theaterPanel.setHeader(headerHtml);
		theaterPanel.setAnimationEnabled(true);

		CineShowTimeWS service = CineShowTimeWS.getInstance();

		// Images url to load in the coverflow
		final Map<String, String> imagesUrls = new HashMap<String, String>(theater.getMovieMap().size());
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
				params.put(HttpParamsCst.PARAM_ZIP, "true");
				params.put(HttpParamsCst.PARAM_MOVIE_ID, movieTmp.getId());
				imagesUrls.put(movieTmp.getId(), CstResource.instance.no_poster().getURL());

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
						coverflow.loadCover(mapMovieIndex.get(movieBean.getId()), movieBean.getUrlImg());

					}
				});
			} else {
				imagesUrls.put(movieTmp.getId(), movieTmp.getUrlImg());
			}
			i++;
		}
		coverflow.init(imagesUrls);
		theaterCoverflow.add(new Label("Programmation (" + theater.getMovieMap().size() + " films)"));
		theaterCoverflow.add(coverflow.getCanvas());

		// Update theater informations
		theaterPhone.setText(theater.getPhoneNumber());
		if (theater.getPlace() != null) {
			theaterPlace.setText(theater.getPlace().getSearchQuery());
		}
	}

	@UiHandler("showTheaterMap")
	public void onShowMapClick(ClickEvent event) {
		if (theater.getPlace() != null) {
			new MapDialog(theater.getTheaterName(), theater.getPlace().getSearchQuery()).center();
		} else {
			// TODO A gérer autrement
			Window.alert("No maps!");
		}

	}

	interface TheaterViewUiBinder extends UiBinder<Widget, TheaterView> {
	}
}
