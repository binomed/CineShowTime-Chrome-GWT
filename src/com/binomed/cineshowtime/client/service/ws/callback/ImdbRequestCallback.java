package com.binomed.cineshowtime.client.service.ws.callback;

import com.binomed.cineshowtime.client.model.MovieBean;

public interface ImdbRequestCallback {

	void onMovieLoadedError(Throwable exception);

	void onMovieLoaded(MovieBean movieBean);

}
