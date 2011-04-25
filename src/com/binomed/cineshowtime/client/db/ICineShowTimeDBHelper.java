package com.binomed.cineshowtime.client.db;

import com.binomed.cineshowtime.client.db.callBack.MovieCallBack;
import com.binomed.cineshowtime.client.db.callBack.PrefCallBack;
import com.binomed.cineshowtime.client.db.callBack.TheaterCallBack;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.NearResp;

public interface ICineShowTimeDBHelper {

	void initDataBase(CineShowTimeDataBase dataBase);

	void writeNearResp(NearResp nearResp);

	void completeMovie(MovieBean movie);

	void writeRequest();

	void writePreference(String key, String value);

	void readPreference(String key, PrefCallBack callBack);

	void getMovie(String movieId, MovieCallBack callBack);

	void getTheaterFav(TheaterCallBack callBack);

	void getTheaters(TheaterCallBack callBack);

	void getMovies(MovieCallBack callBack);

}
