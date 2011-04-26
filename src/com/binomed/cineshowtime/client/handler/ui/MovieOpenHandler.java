package com.binomed.cineshowtime.client.handler.ui;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.google.gwt.event.shared.EventHandler;

public interface MovieOpenHandler extends EventHandler {

	void onMovieOpen(MovieBean movieBean);

}
