package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.NearResp;

public class NearRespMovieEvent extends NearRespEvent {

	public NearRespMovieEvent(NearResp nearResp) {
		super(nearResp);
	}

	@Override
	public EventTypeEnum getEventEnum() {
		return EventTypeEnum.NEAR_RESP_MOVIE;
	}

}
