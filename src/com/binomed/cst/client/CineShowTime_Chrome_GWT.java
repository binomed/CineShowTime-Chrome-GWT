package com.binomed.cst.client;

import com.binomed.cst.client.ui.MainWindow;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CineShowTime_Chrome_GWT implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		MainWindow mainWindow = new MainWindow();
		RootLayoutPanel.get().add(mainWindow);
	}
}
