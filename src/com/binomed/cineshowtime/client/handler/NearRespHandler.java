package com.binomed.cineshowtime.client.handler;

import com.binomed.cineshowtime.client.event.NearRespNearEvent;
import com.binomed.cineshowtime.client.model.NearResp;

public abstract class NearRespHandler extends ServiceEventHandler<NearResp, NearRespNearEvent> {

	@Override
	public final void handleBeanEvent(NearRespNearEvent beanEvent) {
		onNearResp(beanEvent.getNearResp());
	}

	public abstract void onNearResp(NearResp nearResp);

}
