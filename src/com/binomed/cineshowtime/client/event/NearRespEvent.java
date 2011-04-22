package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.handler.NearRespHandler;
import com.binomed.cineshowtime.client.model.NearResp;

abstract class NearRespEvent extends BeanEvent<NearResp, NearRespHandler> {

	public NearRespEvent(NearResp nearResp) {
		super(nearResp);
	}

	public NearRespEvent(Throwable exception) {
		super(exception);
	}

	public NearResp getNearResp() {
		return getBean();
	}

	@Override
	protected void dispatch(NearRespHandler handler) {
		if (getException() != null) {
			handler.onError(getException());
		} else {
			handler.onNearResp(getNearResp());
		}
	}

}
