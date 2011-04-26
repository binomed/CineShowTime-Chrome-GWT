package com.binomed.cineshowtime.client.event.ui;

import com.binomed.cineshowtime.client.handler.ui.TheaterOpenHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TheaterOpenEvent extends GwtEvent<TheaterOpenHandler> {

	public static GwtEvent.Type<TheaterOpenHandler> TYPE = new Type<TheaterOpenHandler>();

	private boolean open;
	private String source;

	public TheaterOpenEvent(boolean open, String source) {
		super();
		this.open = open;
		this.source = source;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TheaterOpenHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TheaterOpenHandler handler) {
		if (open) {
			handler.onTheaterOpen(source);
		} else {
			handler.onTheaterClose(source);
		}

	}

}
