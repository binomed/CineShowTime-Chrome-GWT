package com.binomed.cineshowtime.client.event.db;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.event.BeanEvent;
import com.binomed.cineshowtime.client.handler.db.TheaterDbHandler;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.event.shared.GwtEvent;

public class TheaterDBEvent extends BeanEvent<TheaterBean, TheaterDbHandler> {

	public static GwtEvent.Type<TheaterDbHandler> TYPE = new Type<TheaterDbHandler>();

	private ArrayList<TheaterBean> theaterList;
	private boolean isFav;

	public TheaterDBEvent(ArrayList<TheaterBean> theaterList, boolean isFav) {
		super((TheaterBean) null);
		this.theaterList = theaterList;
		this.isFav = isFav;
	}

	public TheaterDBEvent(TheaterBean movie) {
		super(movie);
	}

	public TheaterDBEvent(Throwable exception) {
		super(exception);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TheaterDbHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TheaterDbHandler handler) {
		if (getBean() != null) {
			handler.theater(getBean());
		} else if (theaterList != null) {
			handler.theaters(theaterList, isFav);
		} else if (getException() != null) {
			handler.onError((DataServiceException) getException());
		}

	}

}
