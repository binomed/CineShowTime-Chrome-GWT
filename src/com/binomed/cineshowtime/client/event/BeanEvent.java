package com.binomed.cineshowtime.client.event;

public abstract class BeanEvent<Bean> implements HandledEvent {

	private Bean bean;

	public BeanEvent(Bean bean) {
		this.bean = bean;
	}

	protected Bean getBean() {
		return bean;
	}

}
