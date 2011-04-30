package com.binomed.cineshowtime.client.db.callback;

import java.util.List;

import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.RowIdListCallback;

public class EmptyRowInsertCallBack implements RowIdListCallback {

	@Override
	public void onFailure(DataServiceException error) {
	}

	@Override
	public void onSuccess(List<Integer> rowIds) {
	}

}
