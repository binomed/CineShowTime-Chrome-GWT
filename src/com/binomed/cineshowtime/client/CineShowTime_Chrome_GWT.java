package com.binomed.cineshowtime.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
		// ClientFactory contain all view and access to WebService
		IClientFactory clientFactory = GWT.create(ClientFactory.class);
		// Load and initialize the window
		RootLayoutPanel.get().add(clientFactory.getMainWindow());

	}

}
