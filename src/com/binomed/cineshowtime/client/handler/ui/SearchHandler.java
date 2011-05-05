package com.binomed.cineshowtime.client.handler.ui;

import com.google.gwt.event.shared.EventHandler;

public interface SearchHandler extends EventHandler {

	void onSearch(int searchType, String city, int date);

}
