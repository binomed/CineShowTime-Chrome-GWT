package com.binomed.cineshowtime.client.service.ws.callback;


public interface NearTheatersRequestCallback {

	// void onResponse(NearResp nearPositionResponse);
	void onResponse(String response);

	void onError(Throwable exception);

	// void onNearResp(NearResp nearResp);

}
