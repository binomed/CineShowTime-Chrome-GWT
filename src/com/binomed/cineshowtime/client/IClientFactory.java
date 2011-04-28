package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.db.ICineShowTimeDBHelper;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;
import com.google.gwt.event.shared.HandlerManager;

public interface IClientFactory {

	MainWindow getMainWindow();

	HandlerManager getEventBusHandler();

	CineShowTimeWS getCineShowTimeService();

	ICineShowTimeDBHelper getDataBaseHelper();

	String getLanguage();

	boolean isDataBaseSupport();

}
