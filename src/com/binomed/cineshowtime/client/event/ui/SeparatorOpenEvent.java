package com.binomed.cineshowtime.client.event.ui;

import com.binomed.cineshowtime.client.event.BeanEvent;
import com.binomed.cineshowtime.client.handler.ui.SeparatorHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SeparatorOpenEvent extends BeanEvent<String, SeparatorHandler> {

	public static GwtEvent.Type<SeparatorHandler> TYPE = new Type<SeparatorHandler>();

	private String source;

	public SeparatorOpenEvent(String source, String bean) {
		super(bean);
		this.source = source;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SeparatorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SeparatorHandler handler) {
		handler.onSeparatorOpen(source, getBean());

	}

}
