package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.resources.CstResource;
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
		CstResource.instance.css().ensureInjected();
		// ClientFactory contain all view and access to WebService
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		// Load and initialize the window
		RootLayoutPanel.get().add(clientFactory.getMainWindow());

		Omnibox.setCanOmniSearch(true);
		Omnibox.registerOmnibox();
		Omnibox.onInputEntered();

	}

}
