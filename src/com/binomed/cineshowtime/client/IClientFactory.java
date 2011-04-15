package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.handler.EventHandlerBus;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;

public interface IClientFactory {

	MainWindow getMainWindow();

	EventHandlerBus getEventBusHandler();

	CineShowTimeWS getCineShowTimeService();

}
