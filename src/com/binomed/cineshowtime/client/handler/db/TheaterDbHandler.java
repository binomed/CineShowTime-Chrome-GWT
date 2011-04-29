package com.binomed.cineshowtime.client.handler.db;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.model.TheaterBean;

public interface TheaterDbHandler extends ErrorDBHandler {

	void theaters(ArrayList<TheaterBean> theaterList, boolean isFav);

	void theater(TheaterBean theater);

}
