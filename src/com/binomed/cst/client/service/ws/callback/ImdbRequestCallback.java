package com.binomed.cst.client.service.ws.callback;

public interface ImdbRequestCallback {

	// void onResponse(MovieBean movieBean);
	void onResponse(String response);

	void onError(Throwable exception);

}
