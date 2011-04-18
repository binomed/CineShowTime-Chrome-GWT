package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.handler.ImdbRespHandler;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.GwtEvent;

public class MovieLoadedEvent extends MovieEvent<ImdbRespHandler> {

	private String source;

	public static GwtEvent.Type<ImdbRespHandler> TYPE = new Type<ImdbRespHandler>();

	public MovieLoadedEvent(String source, MovieBean movieLoaded) {
		super(movieLoaded);
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
		handler.onMovieLoad(getMovie(), source);
	}

}
