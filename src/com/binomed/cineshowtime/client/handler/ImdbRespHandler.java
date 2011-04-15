package com.binomed.cineshowtime.client.handler;

import com.binomed.cineshowtime.client.event.MovieLoadedEvent;
import com.binomed.cineshowtime.client.model.MovieBean;

public abstract class ImdbRespHandler extends ServiceEventHandler<MovieBean, MovieLoadedEvent> {

	@Override
	public final void handleBeanEvent(MovieLoadedEvent beanEvent) {
		onMovieLoad(beanEvent.getMovie(), beanEvent.getSource());
	}

	public abstract void onMovieLoad(MovieBean movieBean, String source);

}
