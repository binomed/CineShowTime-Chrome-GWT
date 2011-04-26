package com.binomed.cineshowtime.client.event.ui;

import com.binomed.cineshowtime.client.event.MovieEvent;
import com.binomed.cineshowtime.client.handler.ui.MovieOpenHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.GwtEvent;

public class MovieOpenEvent extends MovieEvent<MovieOpenHandler> {

	public static GwtEvent.Type<MovieOpenHandler> TYPE = new Type<MovieOpenHandler>();

	public MovieOpenEvent(MovieBean movieLoaded) {
		super(movieLoaded);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MovieOpenHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MovieOpenHandler handler) {
		handler.onMovieOpen(getMovie());

	}

}
