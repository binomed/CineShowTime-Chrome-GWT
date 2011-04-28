package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.db.CineShowTimeDBHelper;
import com.binomed.cineshowtime.client.db.CineShowTimeDataBase;
import com.binomed.cineshowtime.client.db.ICineShowTimeDBHelper;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.ui.MainWindow;
import com.binomed.cineshowtime.client.util.LocaleUtils;
import com.google.code.gwt.database.client.Database;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.geom.LatLng;

public class ClientFactory implements IClientFactory {

	private MainWindow mainWindow = null;

	private HandlerManager eventBus = null;

	private CineShowTimeWS cineShowTimeWS = null;

	private ICineShowTimeDBHelper dataBaseHelper = null;

	private LatLng userLocation = null;

	public ClientFactory() {
		super();
		eventBus = new HandlerManager(null);
		cineShowTimeWS = new CineShowTimeWS(this);
		dataBaseHelper = new CineShowTimeDBHelper(this, (CineShowTimeDataBase) GWT.create(CineShowTimeDataBase.class));
		mainWindow = new MainWindow(this);
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
		return LocaleUtils.getLocale();
	}

	@Override
	public boolean isDataBaseSupport() {
		return Database.isSupported();
	}

	@Override
	public ICineShowTimeDBHelper getDataBaseHelper() {
		return dataBaseHelper;
	}

	@Override
	public LatLng getUserLocation() {
		return userLocation;
	}

	@Override
	public void setUserLocation(LatLng userLocation) {
		this.userLocation = userLocation;
	}

}
