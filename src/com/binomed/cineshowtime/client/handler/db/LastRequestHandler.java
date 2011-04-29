package com.binomed.cineshowtime.client.handler.db;

import com.binomed.cineshowtime.client.model.RequestBean;
import com.google.gwt.event.shared.EventHandler;

public interface LastRequestHandler extends EventHandler {

	void onLastRequest(RequestBean request);

}
