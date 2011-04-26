package com.binomed.cineshowtime.client.event.service;

import com.binomed.cineshowtime.client.event.MovieEvent;
import com.binomed.cineshowtime.client.handler.service.ImdbRespHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.GwtEvent;

public class MovieLoadedEvent extends MovieEvent<ImdbRespHandler> {

	private String source;

	public static GwtEvent.Type<ImdbRespHandler> TYPE = new Type<ImdbRespHandler>();

	public MovieLoadedEvent(String source, MovieBean movieLoaded) {
		super(movieLoaded);
		this.source = source;
	}

	public MovieLoadedEvent(String source, Throwable exception) {
		super(exception);
		this.source = source;
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ImdbRespHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ImdbRespHandler handler) {
		if (getException() != null) {
			handler.onError(getException(), source);
		} else {
			handler.onMovieLoad(getMovie(), source);
		}
	}

}
