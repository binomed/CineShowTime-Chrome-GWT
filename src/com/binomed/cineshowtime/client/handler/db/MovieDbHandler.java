package com.binomed.cineshowtime.client.handler.db;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.model.MovieBean;

public interface MovieDbHandler extends ErrorDBHandler {

	void movie(MovieBean movie);

	void movieList(ArrayList<MovieBean> movieList);

}
