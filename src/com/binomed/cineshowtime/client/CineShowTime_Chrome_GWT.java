package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.ui.MainWindow;
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
		// Load and initialize the window
		MainWindow mainWindow = new MainWindow();
		RootLayoutPanel.get().add(mainWindow);

		// testUserLocation();

	}

}
