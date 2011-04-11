package com.binomed.cineshowtime.client.events;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;

public interface IMovieOpen {

	void movieOpen(TheaterBean theater, MovieBean movie);

}
