package com.binomed.cineshowtime.client.handler;

import com.binomed.cineshowtime.client.event.HandledEvent;

public interface EventHandler {

	void handleEvent(HandledEvent handledEvent);

	void handleError(Throwable error);

}
