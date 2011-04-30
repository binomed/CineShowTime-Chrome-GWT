package com.binomed.cineshowtime.client.ui.dialog;

import com.binomed.cineshowtime.client.resources.I18N;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

public class LastChangeDialog extends DialogBox {

	private final static String WIDTH = "640px";
	private final static String HEIGHT = "390px";

	public LastChangeDialog() {
		setText(I18N.instance.last_change_title());
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setSize(WIDTH, HEIGHT);
		setAutoHideEnabled(true);
		ScrollPanel scrollPanel = new ScrollPanel();
		HTML content = new HTML(I18N.instance.last_change());
		scrollPanel.add(content);
		setWidget(scrollPanel);

	}
}
