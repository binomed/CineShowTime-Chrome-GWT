package com.binomed.cineshowtime.client.ui.dialog;

import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;

public class LastChangeDialog extends DialogBox {

	private final static String WIDTH = "640px";
	private final static String HEIGHT = "390px";

	public LastChangeDialog() {
		setText(I18N.instance.last_change_title());
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setSize(WIDTH, HEIGHT);
		setAutoHideEnabled(true);
		// ScrollPanel scrollPanel = new ScrollPanel();
		HTMLPanel content = new HTMLPanel(I18N.instance.last_change());
		// scrollPanel.add(content);
		// setWidget(scrollPanel);
		setWidget(content);

	}
}
