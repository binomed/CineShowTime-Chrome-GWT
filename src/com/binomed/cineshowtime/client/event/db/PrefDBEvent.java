package com.binomed.cineshowtime.client.event.db;

import java.util.HashMap;

import com.binomed.cineshowtime.client.handler.db.PrefDbHandler;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.event.shared.GwtEvent;

public class PrefDBEvent extends GwtEvent<PrefDbHandler> {

	public static GwtEvent.Type<PrefDbHandler> TYPE = new Type<PrefDbHandler>();

	private HashMap<String, String> preferenceMap;
	private String key, value;
	private DataServiceException exception;

	public PrefDBEvent(HashMap<String, String> prefrenceMap) {
		super();
		this.preferenceMap = prefrenceMap;
	}

	public PrefDBEvent(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public PrefDBEvent(DataServiceException exception) {
		super();
		this.exception = exception;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PrefDbHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PrefDbHandler handler) {
		if (preferenceMap != null) {
			handler.prefValues(preferenceMap);
		} else if (key != null) {
			handler.prefValue(key, value);
		} else if (exception != null) {
			handler.onError(exception);
		}

	}

}
