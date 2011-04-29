package com.binomed.cineshowtime.client.event.ui;

import com.binomed.cineshowtime.client.handler.ui.SearchHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SearchEvent extends GwtEvent<SearchHandler> {

	public static GwtEvent.Type<SearchHandler> TYPE = new Type<SearchHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SearchHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SearchHandler handler) {
		handler.onSearch();
	}

}
