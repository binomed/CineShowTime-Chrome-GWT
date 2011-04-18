package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.handler.NearRespHandler;
import com.google.gwt.event.shared.GwtEvent;

public class NearRespNearErrorEvent extends ErrorEvent<NearRespHandler> {

	public NearRespNearErrorEvent(Throwable exception) {
		super(exception);
	}

	public static GwtEvent.Type<NearRespHandler> TYPE = new Type<NearRespHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NearRespHandler> getAssociatedType() {
		return TYPE;
	}

}
