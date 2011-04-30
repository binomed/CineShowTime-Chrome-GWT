package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.resources.I18N;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ParameterView extends Composite {

	private static ParameterViewUiBinder uiBinder = GWT.create(ParameterViewUiBinder.class);

	private IClientFactory clientFactory;

	@UiField
	CaptionPanel preferenceCatGen;
	@UiField
	Button preferenceGenResetDb;

	public void setClientFactory(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public ParameterView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		preferenceCatGen.setCaptionText(I18N.instance.preference_gen_cat());
	}

	@UiHandler("preferenceGenResetDb")
	public void onCleanDataBase(ClickEvent event) {
		clientFactory.getDataBaseHelper().clean();
	}

	interface ParameterViewUiBinder extends UiBinder<Widget, ParameterView> {
	}
}
