package com.binomed.cineshowtime.client.db;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.cst.CineshowtimeDbCst;
import com.binomed.cineshowtime.client.db.callback.UpdateVersionDbDone;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.VoidCallback;

public class CineShowTimeVersionManager implements CineshowtimeDbCst {

	private CineShowTimeDataBase dataBase;
	private IClientFactory clientFactory;
	private ICineShowTimeDBHelper dbHelper;
	private UpdateVersionDbDone callBack;
	private int taskDone, nbTask;

	public CineShowTimeVersionManager(CineShowTimeDataBase dataBase, IClientFactory clientFactory, ICineShowTimeDBHelper dbHelper, UpdateVersionDbDone callBack) {
		super();
		this.dataBase = dataBase;
		this.clientFactory = clientFactory;
		this.dbHelper = dbHelper;
		this.callBack = callBack;
	}

	public void onUpdate(int versionDb) {
		nbTask = DATABASE_VERSION - versionDb;
		taskDone = 0;
		if (versionDb == -1) {
			// First time
		} else {
			if (versionDb < 2) {
				VoidCallback callBackV2 = new VoidCallback() {

					int compt = 0;

					@Override
					public void onFailure(DataServiceException error) {
						incremente();

					}

					@Override
					public void onSuccess() {
						incremente();

					}

					private void incremente() {
						compt++;
						if (compt == 2) {
							taskDone();
						}
					}
				};

				dataBase.dropRequest(callBackV2);
				dataBase.initTableRequest(callBackV2);
			}
		}
	}

	private void taskDone() {
		taskDone++;
		if (nbTask == taskDone) {
			callBack.onUpdateVersionDone();
		}
	}

}
