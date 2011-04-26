package com.binomed.cineshowtime.client.handler.ui;

import com.google.gwt.event.shared.EventHandler;

public interface TheaterOpenHandler extends EventHandler {

	void onTheaterOpen(String source);

	void onTheaterClose(String source);

}
