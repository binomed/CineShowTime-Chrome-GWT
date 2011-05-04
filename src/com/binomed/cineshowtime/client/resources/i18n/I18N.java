package com.binomed.cineshowtime.client.resources.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface I18N extends Messages {

	public static final I18N instance = GWT.create(I18N.class);

	// Header
	String appName();

	String donateUrl();

	// Main panel
	String theaterTabLabel();

	String theaterResult();

	String menuPreferences();

	String menuAbout();

	String menuHelp();

	String msgDevelopped();

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

	String searchError();

	// Search panel
	String searchTitle();

	String locationLabel();

	String theaterMovieLabel();

	String dateLabel();

	String searchBtn();

	String nearLink();

	String favoriteLink();

	String msgNoCityName();

	String msgNoGps();

	String msgNoMovieName();

	String msgNoPlaceMatch();

	String msgNoDateMatch();

	String msgNoUpComming();

	String msgNoDFav();

	String msgVersionTitle();

	String msgVersionCode();

	String msgVersionName();

	String msgTraductorName();

	String msgNoResultRetryLater();

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

	String noReview();

	String noVideo();

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

	String preference_valid();

	// Values for pref

	String measure_1();

	String measure_2();

	String measure_code_1();

	String measure_code_2();

	String mode_localisation_1();

	String mode_localisation_2();

	String mode_localisation_3();

	String mode_localisation_code_1();

	String mode_localisation_code_2();

	String mode_localisation_code_3();

	String sort_theaters_values_1();

	String sort_theaters_values_2();

	String sort_theaters_values_3();

	String sort_theaters_values_4();

	String sort_theaters_values_5();

	String sort_theaters_values_code_1();

	String sort_theaters_values_code_2();

	String sort_theaters_values_code_3();

	String sort_theaters_values_code_4();

	String sort_theaters_values_code_5();

}
