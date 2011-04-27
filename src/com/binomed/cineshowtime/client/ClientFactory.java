package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.db.ICineShowTimeDBHelper;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;
import com.google.code.gwt.database.client.Database;
import com.google.gwt.event.shared.HandlerManager;

public class ClientFactory implements IClientFactory {

	private MainWindow mainWindow = null;

	private HandlerManager eventBus = null;

	private CineShowTimeWS cineShowTimeWS = null;

	private ICineShowTimeDBHelper dataBaseHelper = null;

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	public void setCineShowTimeWS(CineShowTimeWS cineShowTimeWS) {
		this.cineShowTimeWS = cineShowTimeWS;
	}

	public void setDataBaseHelper(ICineShowTimeDBHelper dataBaseHelper) {
		this.dataBaseHelper = dataBaseHelper;
	}

	public ClientFactory() {
		super();
		// mainWindow = new MainWindow(this);
		// eventBus = new HandlerManager(null);
		// cineShowTimeWS = new CineShowTimeWS(this);
		// dataBaseHelper = new CineShowTimeDBHelper(this, (CineShowTimeDataBase) GWT.create(CineShowTimeDataBase.class));
	}

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
