package com.binomed.cineshowtime.client.event;

import com.binomed.cineshowtime.client.handler.ErrorHandler;
import com.google.gwt.event.shared.GwtEvent;

abstract class ErrorEvent<Handler extends ErrorHandler> extends GwtEvent<Handler> {

	private Throwable exception;

	public ErrorEvent(Throwable exception) {
		super();
		this.exception = exception;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onError(exception);
	}

}
