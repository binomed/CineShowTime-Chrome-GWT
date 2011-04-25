package com.binomed.cineshowtime.client.db.callBack;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.model.TheaterBean;

public interface TheaterCallBack extends ErrorCallBack {

	void theaters(ArrayList<TheaterBean> theaterList);

	void theater(TheaterBean theater);

}
