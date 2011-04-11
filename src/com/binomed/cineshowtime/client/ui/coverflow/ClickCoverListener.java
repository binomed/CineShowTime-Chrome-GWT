package com.binomed.cineshowtime.client.ui.coverflow;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;

public interface ClickCoverListener {

	void onClickCover(TheaterBean theater, MovieBean movie);

}
