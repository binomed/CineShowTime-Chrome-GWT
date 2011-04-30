package com.binomed.cineshowtime.client.db.callback;

import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.VoidCallback;

public final class EmptyVoidCallBack implements VoidCallback {

	@Override
	public void onFailure(DataServiceException error) {
	}

	@Override
	public void onSuccess() {
	}

}
