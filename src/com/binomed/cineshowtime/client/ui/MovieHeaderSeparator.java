package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.ui.SeparatorOpenEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MovieHeaderSeparator extends Composite {

	private static MovieHeaderSeparatorUiBinder uiBinder = GWT.create(MovieHeaderSeparatorUiBinder.class);

	@UiField
	Label separatorName;
	private IClientFactory factory;
	private String name;
	private String source;

	public MovieHeaderSeparator() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setFactory(IClientFactory factory) {
		this.factory = factory;
	}

	public void setName(String name) {
		this.name = name;
		separatorName.setText(name);
	}

	public void setSource(String source) {
		this.source = source;
	}

	@UiHandler("separatorName")
	public void onOpenSeparator(ClickEvent event) {
		factory.getEventBusHandler().fireEvent(new SeparatorOpenEvent(source, name));
	}

	interface MovieHeaderSeparatorUiBinder extends UiBinder<Widget, MovieHeaderSeparator> {
	}
}
