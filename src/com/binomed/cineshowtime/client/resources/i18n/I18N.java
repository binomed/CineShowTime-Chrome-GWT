package com.binomed.cineshowtime.client.resources.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface I18N extends Messages {

	public static final I18N instance = GWT.create(I18N.class);

	// Header
	String appName();

	// Main panel
	String theaterTabLabel();

	String parametersTabLabel();

	String theaterResult();

	// Theater view
	String addFavorites();

	String showTheaterMap();

	String theaterName(String theaterName, int nbMovies);

	// Search result header
	String loadingFavorite();

	String resultFavorite();

	String loadingNear();

	String resultNear(String location);

	String loadingSearch(String location, String date);

	String resultSearch(String location, String date);

	String searchResultDate(String date);

	String searchResultToday();

	String nbTheaters(int nbTheaters);

	// Search panel
	String searchTitle();

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

	String wikipediaLink();

	String rate();

	String duration();

	String director();

	String actor();

	String genre();

	String year();

	// Projection
	String projectionFrom();

	String projectionTo();

	// Review View

	String on();

	String readMore();

	// Last Change
	String last_change_title();

	String last_change();

	// Preference View

	String preference_lang_cat();

	String preference_lang_key_auto_translate();

	String preference_lang_auto_translate();

	String preference_lang_auto_translate_summary();

	String preference_gen_cat();

	String preference_gen_key_auto_reload();

	String preference_gen_auto_reload();

	String preference_gen_auto_reload_summary();

	String preference_gen_key_time_adds();

	String preference_gen_time_adds();

	String preference_gen_time_adds_summary();

	String preference_gen_default_time_adds();

	String preference_gen_key_time_format();

	String preference_gen_time_format();

	String preference_gen_default_time_format();

	String preference_gen_theme();

	String preference_gen_key_theme();

	String preference_gen_default_theme();

	String preference_gen_reset_db();

	String preference_gen_key_reset_db();

	String preference_loc_cat();

	String preference_loc_key_measure();

	String preference_loc_default_measure();

	String preference_loc_measure();

	String preference_loc_measure_summary();

	String preference_loc_key_time_direction();

	String preference_loc_time_direction();

	String preference_loc_time_direction_summary();

	String preference_loc_key_enable_localisation();

	String preference_loc_enable_localisation();

	String preference_loc_enable_localisation_summary();

	String preference_loc_key_localisation_provider();

	String preference_loc_localisation_provider();

	String preference_loc_localisation_provider_summary();

	String preference_loc_default_localisation_provider();

	String preference_sort_cat();

	String preference_sort_key_sort_movie();

	String preference_sort_default_sort_movie();

	String preference_sort_sort_movie();

	String preference_sort_sort_movie_summary();

	String preference_sort_key_sort_theater();

	String preference_sort_default_sort_theater();

	String preference_sort_sort_theater();

	String preference_sort_sort_theater_summary();

	String preference_user_cat();

	String preference_user_agenda();

}
