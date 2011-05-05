package com.binomed.cineshowtime.client.event.service;

import com.binomed.cineshowtime.client.handler.service.RequestImdbHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RequestImdbEvent extends GwtEvent<RequestImdbHandler> {

	private String source;
	private String movieId;

	public static GwtEvent.Type<RequestImdbHandler> TYPE = new Type<RequestImdbHandler>();

	public RequestImdbEvent(String source, String movieId) {
		this.source = source;
		this.movieId = movieId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RequestImdbHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestImdbHandler handler) {
		handler.requestImdb(source, movieId);
	}
}
