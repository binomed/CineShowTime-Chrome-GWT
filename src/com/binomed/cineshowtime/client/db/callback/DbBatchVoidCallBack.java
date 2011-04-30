package com.binomed.cineshowtime.client.db.callback;

import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.VoidCallback;

public class DbBatchVoidCallBack implements VoidCallback {

	private int nbTask;
	private boolean manageError;
	private IDbBatchFinalTask callBack;
	private int compt;

	public DbBatchVoidCallBack(int nbTask, boolean manageError, IDbBatchFinalTask callBack) {
		super();
		this.nbTask = nbTask;
		this.manageError = manageError;
		this.callBack = callBack;
	}

	@Override
	public void onFailure(DataServiceException error) {
		// TODO Auto-generated method stub
		if (manageError) {
			callBack.onError(error);
		}
		incremente();
	}

	@Override
	public void onSuccess() {
		incremente();

	}

	private void incremente() {
		compt++;
		if (nbTask == compt) {
			callBack.finish();
		}
	}

}
