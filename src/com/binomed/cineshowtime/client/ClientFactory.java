package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.db.CineShowTimeDBHelper;
import com.binomed.cineshowtime.client.db.CineShowTimeDataBase;
import com.binomed.cineshowtime.client.db.ICineShowTimeDBHelper;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;
import com.google.code.gwt.database.client.Database;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;

public class ClientFactory implements IClientFactory {

	private MainWindow mainWindow = new MainWindow(this);

	private HandlerManager eventBus = new HandlerManager(null);

	private CineShowTimeWS cineShowTimeWS = new CineShowTimeWS(this);

	private ICineShowTimeDBHelper dataBaseHelper = new CineShowTimeDBHelper(this, (CineShowTimeDataBase) GWT.create(CineShowTimeDataBase.class));

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
		return "fr";
	}

	@Override
	public boolean isDataBaseSupport() {
		return Database.isSupported();
	}

	@Override
	public ICineShowTimeDBHelper getDataBaseHelper() {
		return dataBaseHelper;
	}
}
