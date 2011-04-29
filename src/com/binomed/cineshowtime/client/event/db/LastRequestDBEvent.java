package com.binomed.cineshowtime.client.event.db;

import com.binomed.cineshowtime.client.event.BeanEvent;
import com.binomed.cineshowtime.client.handler.db.LastRequestHandler;
import com.binomed.cineshowtime.client.model.RequestBean;
import com.google.gwt.event.shared.GwtEvent;

public class LastRequestDBEvent extends BeanEvent<RequestBean, LastRequestHandler> {

	public LastRequestDBEvent(RequestBean bean) {
		super(bean);
	}

	public static final GwtEvent.Type<LastRequestHandler> TYPE = new Type<LastRequestHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LastRequestHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LastRequestHandler handler) {
		handler.onLastRequest(getBean());

	}

}
