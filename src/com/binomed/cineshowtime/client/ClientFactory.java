package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;
import com.google.gwt.event.shared.HandlerManager;

public class ClientFactory implements IClientFactory {

	private MainWindow mainWindow = new MainWindow(this);

	private HandlerManager eventBus = new HandlerManager(null);

	private CineShowTimeWS cineShowTimeWS = new CineShowTimeWS(this);

	@Override
	public MainWindow getMainWindow() {
		return mainWindow;
	}

	@Override
	public HandlerManager getEventBusHandler() {
		return eventBus;
	}

	@Override
	public CineShowTimeWS getCineShowTimeService() {
		return cineShowTimeWS;
	}

	@Override
	public String getLanguage() {
		return "FR";
	}

}
