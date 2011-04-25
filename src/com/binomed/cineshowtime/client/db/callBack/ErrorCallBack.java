package com.binomed.cineshowtime.client.db.callBack;

import com.google.code.gwt.database.client.service.DataServiceException;

public interface ErrorCallBack {

	void onError(DataServiceException exception);

}
