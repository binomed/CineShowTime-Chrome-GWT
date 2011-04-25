package com.binomed.cineshowtime.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface I18N extends Messages {

	public static final I18N instance = GWT.create(I18N.class);

	// Header
	String appName();

	// Main panel
	String theaterTabLabel();

	String parametersTabLabel();

	// Theater view
	String addFavorites();

	String showTheaterMap();

	String theaterName(String theaterName, int nbMovies);

	// Search panel
	String locationLabel();

	String theaterMovieLabel();

	String dateLabel();

	String searchBtn();

	String nearLink();

	String favoriteLink();

	// Movie view

	String imdbLink();

	String groupResume();

	String groupBA();

	String groupCritiques();

	String endProjection();

}
