package com.binomed.cineshowtime.client.ui.dialog;

import com.binomed.cineshowtime.client.cst.CineshowtimeDbCst;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class AboutDialog extends DialogBox {

	private final static String WIDTH = "300px";
	private final static String HEIGHT = "100px";

	public AboutDialog() {
		setText("CineShowTime " + CineshowtimeDbCst.APP_VERSION);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setSize(WIDTH, HEIGHT);
		setAutoHideEnabled(true);
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(WIDTH, HEIGHT);
		String strContent = I18N.instance.msgDevelopped() + " " + "<A HREF='http://blog.binomed.fr'>Binomed</A><br>"//
				+ I18N.instance.msgTraductorName() + "<br>" //
				+ "© 2010 - Binomed <br>"//
				+ "GNU General Public License v2<br>"//
				+ "Use of Google Analytics Service";
		HTMLPanel content = new HTMLPanel(strContent);
		scrollPanel.add(content);
		setWidget(scrollPanel);
		// setWidget(content);

	}
}
