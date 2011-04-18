package com.binomed.cineshowtime.client.handler;

import com.google.gwt.event.shared.EventHandler;

public interface ErrorHandler extends EventHandler {

	void onError(Throwable exception);

}
