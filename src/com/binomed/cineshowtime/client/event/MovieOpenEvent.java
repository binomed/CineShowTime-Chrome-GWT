package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.MovieBean;

public class MovieOpenEvent extends MovieEvent {

	public MovieOpenEvent(MovieBean movieLoaded) {
		super(movieLoaded);
	}

	@Override
	public EventTypeEnum getEventEnum() {
		return EventTypeEnum.MOVIE_OPEN;
	}

}
