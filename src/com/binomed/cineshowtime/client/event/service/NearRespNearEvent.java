package com.binomed.cineshowtime.client.event.service;

import com.binomed.cineshowtime.client.event.NearRespEvent;
import com.binomed.cineshowtime.client.handler.service.NearRespHandler;
import com.binomed.cineshowtime.client.model.NearResp;
import com.google.gwt.event.shared.GwtEvent;

public class NearRespNearEvent extends NearRespEvent {

	public static GwtEvent.Type<NearRespHandler> TYPE = new Type<NearRespHandler>();

	public NearRespNearEvent(NearResp nearResp) {
		super(nearResp);
	}

	public NearRespNearEvent(Throwable exception) {
		super(exception);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NearRespHandler> getAssociatedType() {
		return TYPE;
	}

}
