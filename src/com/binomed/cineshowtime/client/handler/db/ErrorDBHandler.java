package com.binomed.cineshowtime.client.handler.db;

import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.event.shared.EventHandler;

interface ErrorDBHandler extends EventHandler {

	void onError(DataServiceException exception);

}
