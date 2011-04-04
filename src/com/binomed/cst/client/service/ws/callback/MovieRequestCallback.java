package com.binomed.cst.client.service.ws.callback;

public interface MovieRequestCallback {

	// void onResponse(MovieResp theaterBean);
	void onResponse(String response);

	void onError(Throwable exception);

}
