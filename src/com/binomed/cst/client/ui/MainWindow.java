package com.binomed.cst.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT.create(MainWindowUiBinder.class);

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	VerticalPanel paramsContent;

	public MainWindow() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
