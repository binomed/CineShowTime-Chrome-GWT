package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.MovieBean;

abstract class MovieEvent extends BeanEvent<MovieBean> {

	public MovieEvent(MovieBean movie) {
		super(movie);
	}

	public MovieBean getMovie() {
		return getBean();
	}

}
