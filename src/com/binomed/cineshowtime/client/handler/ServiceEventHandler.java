package com.binomed.cineshowtime.client.handler;

import com.binomed.cineshowtime.client.event.BeanEvent;
import com.binomed.cineshowtime.client.event.HandledEvent;

abstract class ServiceEventHandler<Bean, BeanEvt extends BeanEvent<Bean>> implements EventHandler {

	@Override
	public final void handleEvent(HandledEvent handledEvent) {
		handleBeanEvent((BeanEvt) handledEvent);
	}

	public abstract void handleBeanEvent(BeanEvt beanEvent);

}
