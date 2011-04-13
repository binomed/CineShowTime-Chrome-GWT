package com.binomed.cineshowtime.client;

import com.binomed.cineshowtime.client.ui.MainWindow;

public class ClientFactory implements IClientFactory {

	private MainWindow mainWindow = new MainWindow(this);

	public MainWindow getMainWindow() {
		return mainWindow;
	}

}
