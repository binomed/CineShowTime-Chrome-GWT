package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.MovieLoadedEvent;
import com.binomed.cineshowtime.client.handler.ImdbRespHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.ReviewBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.model.YoutubeBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.I18N;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.coverflow.CoverData;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.coverflow.event.ClickCoverListener;
import com.binomed.cineshowtime.client.ui.dialog.VideoDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MovieView extends Composite {

	private static MovieViewUiBinder uiBinder = GWT.create(MovieViewUiBinder.class);
	private final TheaterBean theater;
	private MovieBean movie;
	private final IClientFactory clientFactory;

	@UiField
	Image imgPoster;
	@UiField
	VerticalPanel movieRate;
	@UiField
	Label movieName, movieTime, movieStyle, sepMovieTimeStyle, moviePlot;
	@UiField
	Hyperlink movieLinkImdb;
	@UiField
	DisclosurePanel groupMoviePlot, groupMovieBA, groupMovieCritique;
	@UiField
	CaptionPanel movieSeanceGroup;
	@UiField
	VerticalPanel movieSeanceList, movieTrailerCoverflow, movieReview;
	// @UiField
	// HorizontalPanel movieHeader;
	private Coverflow coverflow;

	public MovieView(final TheaterBean theater, final String idMovie, IClientFactory clientFactory) {
		this.theater = theater;
		this.clientFactory = clientFactory;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// movieHeader.setSpacing(10);

		CineShowTimeWS service = clientFactory.getCineShowTimeService();
		this.movie = service.getMovie(idMovie);

		if ((movie.getState() == MovieBean.STATE_NONE) || (movie.getState() == MovieBean.STATE_IN_PROGRESS)) {
			clientFactory.getEventBusHandler().addHandler(MovieLoadedEvent.TYPE, eventHandler);
		} else {
			fillMovieView();
		}

	}

	private void fillMovieView() {
		groupMoviePlot.setHeader(new HTML("<font color=#FFFFFF>" + I18N.instance.groupResume() + "</font>"));
		groupMovieBA.setHeader(new HTML("<font color=#FFFFFF>" + I18N.instance.groupBA() + "</font>"));
		groupMovieCritique.setHeader(new HTML("<font color=#FFFFFF>" + I18N.instance.groupCritiques() + "</font>"));

		movieSeanceGroup.setCaptionText(theater.getTheaterName());

		movieName.setText(movie.getMovieName());
		Date time = new Date(movie.getMovieTime());
		movieTime.setText(time.getHours() + "h" + time.getMinutes() + "m");

		for (ProjectionBean projection : theater.getMovieMap().get(movie.getId())) {
			movieSeanceList.add(new ProjectionView(projection, movie.getMovieTime()));
		}

		if (movie.getState() == MovieBean.STATE_LOADED) {
			updateMovieView();
		} else {
			imgPoster.setUrl(CstResource.instance.no_poster().getURL());
		}

		imgPoster.setWidth("185px");
		imgPoster.setHeight("245px");
	}

	private void updateMovieView() {
		imgPoster.setUrl(movie.getUrlImg());
		movieLinkImdb.setText(movie.getUrlImdb());
		moviePlot.setText(movie.getDescription());
		groupMoviePlot.setOpen(true);

		movieRate.add(new RateView(true, movie.getRate()));

		// Add the coverflow
		if (movie.getYoutubeVideos() != null) {
			coverflow = new Coverflow(800, 300);
			coverflow.addClickCoverListener(movieOpenListener);
			final List<CoverData> coversData = new ArrayList<CoverData>();
			for (YoutubeBean video : movie.getYoutubeVideos()) {
				coversData.add(video);
			}
			coverflow.init(coversData);
			movieTrailerCoverflow.add(coverflow.getCanvas());
		}

		if (movie.getReviews() != null) {
			for (ReviewBean review : movie.getReviews()) {
				movieReview.add(new ReviewView(review));
			}
		}
	}

	public MovieBean getMovie() {
		return movie;
	}

	private ImdbRespHandler eventHandler = new ImdbRespHandler() {

		@Override
		public void onError(Throwable error, String source) {
			if ((source != null) && movie.getId().equals(source)) {
				Window.alert("Unable to load movie ! " + error.getMessage());
			}
		}

		@Override
		public void onMovieLoad(MovieBean movieBean, String source) {
			if ((movieBean != null) && movie.getId().equals(movieBean.getId())) {
				movie = movieBean;
				updateMovieView();
				clientFactory.getEventBusHandler().removeHandler(MovieLoadedEvent.TYPE, eventHandler);
			}

		}
	};

	private final ClickCoverListener movieOpenListener = new ClickCoverListener() {
		@Override
		public void onClickCover(String idMovie) {

			YoutubeBean videoBean = null;
			for (YoutubeBean videoTmp : movie.getYoutubeVideos()) {
				if (videoTmp.getId().equals(idMovie)) {
					videoBean = videoTmp;
				}
			}

			new VideoDialog(videoBean.getVideoName(), videoBean.getVideoId()).center();

			//
			// int left = coverflow.getCanvas().getAbsoluteLeft() + 10;
			// int top = coverflow.getCanvas().getAbsoluteTop() + 10;
			//
			// DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
			// simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
			// simplePopup.setWidth("150px");
			// simplePopup.show();
			// simplePopup.setPopupPosition(left, top);
			//
			// Video video = new Video(videoBean.getVideoUrl());
			//
			// final PopupPanel imagePopup = new PopupPanel(true);
			// imagePopup.setAnimationEnabled(true);
			// imagePopup.ensureDebugId("cwBasicPopup-imagePopup");
			// imagePopup.setWidget(video);
			// video.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// imagePopup.hide();
			// }
			// });

		}

	};

	interface MovieViewUiBinder extends UiBinder<Widget, MovieView> {
	}

}
