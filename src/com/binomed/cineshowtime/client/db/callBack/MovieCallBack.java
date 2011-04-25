package com.binomed.cineshowtime.client.db.callBack;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.model.MovieBean;

public interface MovieCallBack extends ErrorCallBack {

	void movie(MovieBean movie);

	void movieList(ArrayList<MovieBean> movieList);

}
