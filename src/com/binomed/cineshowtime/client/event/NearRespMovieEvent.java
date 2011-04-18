package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.handler.NearRespHandler;
import com.binomed.cineshowtime.client.model.NearResp;
import com.google.gwt.event.shared.GwtEvent;

public class NearRespMovieEvent extends NearRespEvent {

	public static GwtEvent.Type<NearRespHandler> TYPE = new Type<NearRespHandler>();

	public NearRespMovieEvent(NearResp nearResp) {
		super(nearResp);
	}

	@Override
	public GwtEvent.Type<NearRespHandler> getAssociatedType() {
		return TYPE;
	}

}
