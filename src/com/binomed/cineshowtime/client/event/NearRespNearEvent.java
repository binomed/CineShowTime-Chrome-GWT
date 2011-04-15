package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.NearResp;

public class NearRespNearEvent extends NearRespEvent {

	public NearRespNearEvent(NearResp nearResp) {
		super(nearResp);
	}

	@Override
	public EventTypeEnum getEventEnum() {
		return EventTypeEnum.NEAR_RESP_NEAR;
	}

}
