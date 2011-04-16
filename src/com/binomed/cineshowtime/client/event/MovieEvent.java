package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.EventHandler;

abstract class MovieEvent<MovieHandler extends EventHandler> extends BeanEvent<MovieBean, MovieHandler> {

	public MovieEvent(MovieBean movie) {
		super(movie);
	}

	public MovieBean getMovie() {
		return getBean();
	}

}
