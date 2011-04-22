package com.binomed.cineshowtime.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public abstract class BeanEvent<Bean, BeanHandler extends EventHandler> extends GwtEvent<BeanHandler> {

	private Bean bean;

	private Throwable exception;

	public BeanEvent(Bean bean) {
		this.bean = bean;
	}

	public BeanEvent(Throwable exception) {
		this.exception = exception;
	}

	protected Bean getBean() {
		return bean;
	}

	protected Throwable getException() {
		return exception;
	}

}
