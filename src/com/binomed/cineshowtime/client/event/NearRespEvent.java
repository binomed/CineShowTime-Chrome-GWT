package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.NearResp;

abstract class NearRespEvent extends BeanEvent<NearResp> {

	public NearRespEvent(NearResp nearResp) {
		super(nearResp);
	}

	public NearResp getNearResp() {
		return getBean();
	}

}
