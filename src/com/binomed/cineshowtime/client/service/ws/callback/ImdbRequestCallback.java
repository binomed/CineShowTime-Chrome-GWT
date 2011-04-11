package com.binomed.cineshowtime.client.service.ws.callback;

import com.binomed.cineshowtime.client.model.MovieBean;

public interface ImdbRequestCallback {

	// void onResponse(MovieBean movieBean);
	void onResponse(String response);

	void onError(Throwable exception);

	void onMovieResp(MovieBean movieBean);

}
