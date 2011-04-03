package com.binomed.cst.client;

import com.binomed.cst.client.geolocation.Geolocation;
import com.binomed.cst.client.geolocation.GeolocationCallback;
import com.binomed.cst.client.geolocation.Position;
import com.binomed.cst.client.ui.MainWindow;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CineShowTime_Chrome_GWT implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// Get geolocation of the user
		Geolocation geolocation = new Geolocation(new GeolocationCallback() {
			@Override
			public void onLocation(Position position) {
				System.out.println(position.getLatitude() + " : " + position.getLongitude());
				Window.alert(position.getLatitude() + " : " + position.getLongitude());
			}

			@Override
			public void onError(JavaScriptObject jso) {
				Window.alert("Geolocation Error");
			}
		});
		geolocation.getGeoLocation();

		// Load and initialize the window
		MainWindow mainWindow = new MainWindow();
		RootLayoutPanel.get().add(mainWindow);
	}
}
