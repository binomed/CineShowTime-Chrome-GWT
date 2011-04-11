package com.binomed.cineshowtime.client.ui;

import java.util.Map;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.dialog.MapDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
	private final Map<String, MovieBean> movies;

	@UiField
	DisclosurePanel theaterPanel;
	@UiField
	Label theaterPlace;
	@UiField
	Label theaterPhone;
	@UiField
	VerticalPanel theaterCoverflow;

	public TheaterView(final TheaterBean theater, Map<String, MovieBean> movies) {
		this.theater = theater;
		this.movies = movies;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		HTML headerHtml = new HTML("<font color=\"#FFFFFF\">" + theater.getTheaterName() + "</font>");
		theaterPanel.setHeader(headerHtml);
		theaterPanel.setAnimationEnabled(true);

		// Images url to load in the coverflow
		final String[] imagesUrls = new String[] { "http://www.google.fr/movies/image?tbn=5fb488cff09bee9a&size=100x150", //
				"http://www.google.fr/movies/image?tbn=6f9e9a2fd2f45c86&size=100x150", //
				"http://www.google.fr/movies/image?tbn=272babc86fbdee70&size=100x150", //
				"http://www.google.fr/movies/image?tbn=a6526f9c6231998c&size=100x150", //
				"http://www.google.fr/movies/image?tbn=78ff8d3e5f3c2f93&size=100x150", //
				"http://www.google.fr/movies/image?tbn=4456a070bd91e0f3&size=100x150", //
				"http://www.google.fr/movies/image?tbn=afa72d7f8fb104f8&size=100x150" };

		// Update theater informations
		theaterPhone.setText(theater.getPhoneNumber());
		if (theater.getPlace() != null) {
			theaterPlace.setText(theater.getPlace().getSearchQuery());
			// Add the coverflow
			Coverflow coverflow = new Coverflow(800, 300);
			coverflow.init(imagesUrls);
			theaterCoverflow.add(coverflow.getCanvas());
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
