package com.binomed.cineshowtime.client.handler.db;

import java.util.HashMap;

public interface PrefDbHandler extends ErrorDBHandler {

	void prefValue(String key, String value);

	void prefValues(HashMap<String, String> preferencesValues);

}
