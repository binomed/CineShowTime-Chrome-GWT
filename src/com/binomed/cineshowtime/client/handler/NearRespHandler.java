package com.binomed.cineshowtime.client.handler;

import com.binomed.cineshowtime.client.model.NearResp;
import com.google.gwt.event.shared.EventHandler;

public interface NearRespHandler extends EventHandler {

	void onNearResp(NearResp nearResp);

	void onError(Throwable exception);

}
