package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.model.MovieBean;

public interface MovieLoadedListener {

	void onMovieLoad(MovieBean movie);

}
