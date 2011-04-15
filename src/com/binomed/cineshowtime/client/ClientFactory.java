package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.handler.EventHandlerBus;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;

public class ClientFactory implements IClientFactory {

	private MainWindow mainWindow = new MainWindow(this);

	private EventHandlerBus eventBus = new EventHandlerBus();

	private CineShowTimeWS cineShowTimeWS = new CineShowTimeWS(this);

	@Override
	public MainWindow getMainWindow() {
		return mainWindow;
	}

	@Override
	public EventHandlerBus getEventBusHandler() {
		return eventBus;
	}

	@Override
	public CineShowTimeWS getCineShowTimeService() {
		return cineShowTimeWS;
	}

}
