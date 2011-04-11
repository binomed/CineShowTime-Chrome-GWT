package com.binomed.cineshowtime.client.ui;

import java.util.Date;
import java.util.List;

import com.binomed.cineshowtime.client.events.IMovieResponse;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.I18N;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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

	public MovieView(final TheaterBean theater, MovieBean movie, List<IMovieResponse> movieListener) {

		movieListener.add(new IMovieResponse() {

			@Override
			public void movieResponse(MovieBean movie) {
				fillMovie(movie);
			}
		});
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

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
			fillMovie(movie);
		} else {
			imgPoster.setUrl(CstResource.instance.no_poster().getURL());
		}

	}

	private void fillMovie(MovieBean movie) {
		imgPoster.setUrl(movie.getUrlImg());

		movieLinkImdb.setText(movie.getUrlImdb());
		moviePlot.setText(movie.getDescription());

	}

	interface MovieViewUiBinder extends UiBinder<Widget, MovieView> {
	}
}
