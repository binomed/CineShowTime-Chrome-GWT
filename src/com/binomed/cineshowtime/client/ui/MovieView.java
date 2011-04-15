package com.binomed.cineshowtime.client.ui;

import java.util.Date;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.EventTypeEnum;
import com.binomed.cineshowtime.client.handler.ImdbRespHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.I18N;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
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
	Image rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9, rate10;
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

	public MovieView(final TheaterBean theater, final String idMovie, IClientFactory clientFactory) {
		this.theater = theater;
		this.clientFactory = clientFactory;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		CineShowTimeWS service = clientFactory.getCineShowTimeService();
		this.movie = service.getMovie(idMovie);
		if (movie.getState() == MovieBean.STATE_NONE || movie.getState() == MovieBean.STATE_IN_PROGRESS) {
			clientFactory.getEventBusHandler().put(EventTypeEnum.MOVIE_LOAD, eventHandler);
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

		if (movie.getUrlImg() != null) {
			updateMovieView();
		} else {
			imgPoster.setUrl(CstResource.instance.no_poster().getURL());
		}
	}

	private void updateMovieView() {
		imgPoster.setUrl(movie.getUrlImg());
		movieLinkImdb.setText(movie.getUrlImdb());
		moviePlot.setText(movie.getDescription());
	}

	public MovieBean getMovie() {
		return movie;
	}

	private ImdbRespHandler eventHandler = new ImdbRespHandler() {

		@Override
		public void handleError(Throwable error) {
			Window.alert("Unable to load movie ! " + error.getMessage());

		}

		@Override
		public void onMovieLoad(MovieBean movieBean, String source) {
			if (movieBean != null && movie.getId().equals(movieBean.getId())) {
				movie = movieBean;
				updateMovieView();
				clientFactory.getEventBusHandler().remove(EventTypeEnum.MOVIE_LOAD, eventHandler);
			}

		}
	};

	interface MovieViewUiBinder extends UiBinder<Widget, MovieView> {
	}

}
