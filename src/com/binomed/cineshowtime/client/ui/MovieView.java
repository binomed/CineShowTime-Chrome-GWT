package com.binomed.cineshowtime.client.ui;

import java.util.Date;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
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

	public MovieView(final TheaterBean theater, MovieBean movie) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		groupMoviePlot.setHeader(new HTML("<font color=#FFFFFF>" + I18N.instance.groupResume() + "</font>"));
		groupMovieBA.setHeader(new HTML("<font color=#FFFFFF>" + I18N.instance.groupBA() + "</font>"));
		groupMovieCritique.setHeader(new HTML("<font color=#FFFFFF>" + I18N.instance.groupCritiques() + "</font>"));

		movieSeanceGroup.setCaptionText(theater.getTheaterName());

		movieName.setText(movie.getMovieName());
		Date time = new Date(movie.getMovieTime());
		movieTime.setText(time.getHours() + "h" + time.getMinutes() + "m");

		imgPoster.setUrl("http://www.google.fr/movies/image?tbn=272babc86fbdee70&size=100x150");

	}

	interface MovieViewUiBinder extends UiBinder<Widget, MovieView> {
	}
}
