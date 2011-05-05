package com.binomed.cineshowtime.client.handler.service;

import com.google.gwt.event.shared.EventHandler;

public interface RequestImdbHandler extends EventHandler {

	void requestImdb(String source, String movieId);

}
