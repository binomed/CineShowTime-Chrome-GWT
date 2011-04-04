package com.binomed.cst.client.service.ws.callback;

public interface NearTheatersRequestCallback {

	// void onResponse(NearResp nearPositionResponse);
	void onResponse(String response);

	void onError(Throwable exception);

}
