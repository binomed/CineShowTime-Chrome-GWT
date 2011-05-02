package com.binomed.cineshowtime.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HeaderSeparator extends Composite {

	private static HeaderSeparatorUiBinder uiBinder = GWT.create(HeaderSeparatorUiBinder.class);

	@UiField
	Label separatorName;

	public HeaderSeparator() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setName(String name) {
		separatorName.setText(name);
	}

	interface HeaderSeparatorUiBinder extends UiBinder<Widget, HeaderSeparator> {
	}
}
