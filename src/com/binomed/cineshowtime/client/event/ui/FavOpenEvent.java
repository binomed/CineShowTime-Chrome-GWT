package com.binomed.cineshowtime.client.event.ui;

import com.binomed.cineshowtime.client.handler.ui.FavOpenHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FavOpenEvent extends GwtEvent<FavOpenHandler> {

	public static GwtEvent.Type<FavOpenHandler> TYPE = new Type<FavOpenHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<FavOpenHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FavOpenHandler handler) {
		handler.onFavOpen();
	}

}
