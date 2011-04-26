package com.binomed.cineshowtime.client.db;

import com.google.code.gwt.database.client.service.Connection;
import com.google.code.gwt.database.client.service.DataService;
import com.google.code.gwt.database.client.service.Update;
import com.google.code.gwt.database.client.service.VoidCallback;

@Connection(name = "myDatabase", version = "1.0", description = "My Database", maxsize = 10000)
public interface MyDataService extends DataService {

	@Update("CREATE TABLE IF NOT EXISTS testtable (" + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + "adate INTEGER")
	void initDatabase(VoidCallback callback);
}
