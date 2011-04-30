package com.binomed.cineshowtime.client.db.callback;

public interface IDbBatchFinalTask {

	void finish();

	void onError(Exception exception);

}
