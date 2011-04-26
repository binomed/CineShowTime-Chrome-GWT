package com.binomed.cineshowtime.client.db;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.TheaterBean;

public interface ICineShowTimeDBHelper {

	void initDataBase(CineShowTimeDataBase dataBase);

	void writeNearResp(NearResp nearResp);

	void completeMovie(MovieBean movie);

	void writeRequest();

	void writePreference(String key, String value);

	void readPreference(String key);

	void getMovie(String movieId);

	void getTheaterFav();

	void getTheaters();

	void getMovies();

	ArrayList<TheaterBean> getTheaterFavCache();

	void removeFav(TheaterBean theater);

	void addFav(TheaterBean theater);

}
