package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.handler.ImdbRespHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MovieLoadErrorEvent extends ErrorEvent<ImdbRespHandler> {

	public MovieLoadErrorEvent(Throwable exception) {
		super(exception);
	}

	public static GwtEvent.Type<ImdbRespHandler> TYPE = new Type<ImdbRespHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ImdbRespHandler> getAssociatedType() {
		return TYPE;
	}

}
