package com.binomed.cineshowtime.client.handler;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.EventHandler;

public interface ImdbRespHandler extends EventHandler, ErrorHandler {

	void onMovieLoad(MovieBean movieBean, String source);

	@Override
	void onError(Throwable exception);

}
