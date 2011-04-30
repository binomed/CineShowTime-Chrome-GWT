package com.binomed.cineshowtime.client.event.db;

import com.binomed.cineshowtime.client.handler.db.DataBaseReadyHandler;
import com.binomed.cineshowtime.client.handler.db.LastChangeHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DataBaseReadyDBEvent extends GwtEvent<DataBaseReadyHandler> {

	public DataBaseReadyDBEvent() {
		super();
	}

	public static final GwtEvent.Type<DataBaseReadyHandler> TYPE = new Type<DataBaseReadyHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DataBaseReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DataBaseReadyHandler handler) {
		handler.dataBaseReady();

	}

}
