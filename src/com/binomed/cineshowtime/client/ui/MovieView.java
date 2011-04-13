package com.binomed.cineshowtime.client.ui;

import java.util.Date;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.I18N;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.service.ws.callback.ImdbRequestCallback;
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

public class MovieView extends Composite implements ImdbRequestCallback {

	private static MovieViewUiBinder uiBinder = GWT.create(MovieViewUiBinder.class);
	private final TheaterBean theater;
	private MovieBean movie;

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

	public MovieView(final TheaterBean theater, final String idMovie) {
		this.theater = theater;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		CineShowTimeWS service = CineShowTimeWS.getInstance();
		this.movie = service.getMovie(idMovie);
		if (movie.getState() == MovieBean.STATE_NONE || movie.getState() == MovieBean.STATE_IN_PROGRESS) {
			service.register(movie.getId(), this);
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

	@Override
	public void onMovieLoadedError(Throwable exception) {
		// TODO gerer erreur
		Window.alert("Unable to load movie ! " + exception.getMessage());
	}

	@Override
	public void onMovieLoaded(MovieBean movieBean) {
		this.movie = movieBean;
		updateMovieView();
	}

	interface MovieViewUiBinder extends UiBinder<Widget, MovieView> {
	}

}
