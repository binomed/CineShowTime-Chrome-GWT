package com.binomed.cineshowtime.client.service.ws.callback;

import com.binomed.cineshowtime.client.model.NearResp;

public interface NearTheatersRequestCallback {

	void onNearResp(NearResp nearResp);

	void onError(Throwable exception);

}
