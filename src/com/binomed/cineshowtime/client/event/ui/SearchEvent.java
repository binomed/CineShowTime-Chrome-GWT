package com.binomed.cineshowtime.client.event.ui;

import com.binomed.cineshowtime.client.handler.ui.SearchHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SearchEvent extends GwtEvent<SearchHandler> {

	public static GwtEvent.Type<SearchHandler> TYPE = new Type<SearchHandler>();

	public static final int SEARCH_FAV = 0;
	public static final int SEARCH_NEAR = 1;
	public static final int SEARCH_CINE = 2;
	public static final int SEARCH_DATE = 3;

	private final int searchType;
	private final String param;

	public SearchEvent(final int searchType, final String param) {
		this.searchType = searchType;
		this.param = param;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SearchHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SearchHandler handler) {
		handler.onSearch(searchType, param);
	}

}
