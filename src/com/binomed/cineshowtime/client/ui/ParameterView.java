package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ParameterView extends Composite {

	private static ParameterViewUiBinder uiBinder = GWT.create(ParameterViewUiBinder.class);

	private IClientFactory clientFactory;

	@UiField
	CaptionPanel preferenceCatGen;
	@UiField
	Button preferenceGenResetDb;
	@UiField
	ListBox preferenceGenTimeFormat, preferenceGenTimeAdds;

	public void setClientFactory(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public ParameterView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		preferenceCatGen.setCaptionText(I18N.instance.preference_gen_cat());

		preferenceGenTimeFormat.addStyleName("demo-ListBox");
		preferenceGenTimeFormat.addItem("12");
		preferenceGenTimeFormat.addItem("24");
		preferenceGenTimeFormat.setSelectedIndex(1);

		preferenceGenTimeAdds.addStyleName("demo-ListBox");
		preferenceGenTimeAdds.addItem("00");
		preferenceGenTimeAdds.addItem("05");
		preferenceGenTimeAdds.addItem("15");
		preferenceGenTimeAdds.addItem("15");
		preferenceGenTimeAdds.addItem("20");
		preferenceGenTimeAdds.addItem("25");
		preferenceGenTimeAdds.addItem("30");
		preferenceGenTimeAdds.addItem("35");
		preferenceGenTimeAdds.addItem("40");
		preferenceGenTimeAdds.addItem("45");
		preferenceGenTimeAdds.addItem("50");
		preferenceGenTimeAdds.setSelectedIndex(4);

	}

	@UiHandler("preferenceGenResetDb")
	public void onCleanDataBase(ClickEvent event) {
		clientFactory.getDataBaseHelper().clean();
	}

	interface ParameterViewUiBinder extends UiBinder<Widget, ParameterView> {
	}
}
