package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.MovieBean;

public class MovieLoadedEvent extends MovieEvent {

	private String source;

	public MovieLoadedEvent(String source, MovieBean movieLoaded) {
		super(movieLoaded);
		this.source = source;
	}

	@Override
	public EventTypeEnum getEventEnum() {
		return EventTypeEnum.MOVIE_LOAD;
	}

	public String getSource() {
		return source;
	}

}
