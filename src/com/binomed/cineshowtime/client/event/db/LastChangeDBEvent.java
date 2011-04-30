package com.binomed.cineshowtime.client.event.db;

import com.binomed.cineshowtime.client.handler.db.LastChangeHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LastChangeDBEvent extends GwtEvent<LastChangeHandler> {

	public LastChangeDBEvent() {
		super();
	}

	public static final GwtEvent.Type<LastChangeHandler> TYPE = new Type<LastChangeHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LastChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LastChangeHandler handler) {
		handler.onLastChange();

	}

}
