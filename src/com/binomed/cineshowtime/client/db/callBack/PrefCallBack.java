package com.binomed.cineshowtime.client.db.callBack;

import com.google.gwt.dev.util.collect.HashMap;

public interface PrefCallBack extends ErrorCallBack {

	void prefValue(String key, String value);

	void prefValues(HashMap<String, String> preferencesValues);

}
