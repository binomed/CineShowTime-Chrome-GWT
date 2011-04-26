package com.binomed.cineshowtime.client.handler.service;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.EventHandler;

public interface ImdbRespHandler extends EventHandler {

	void onMovieLoad(MovieBean movieBean, String source);

	void onError(Throwable exception, String source);

}
