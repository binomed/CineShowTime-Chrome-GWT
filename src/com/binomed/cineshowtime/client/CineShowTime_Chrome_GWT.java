package com.binomed.cineshowtime.client;

import java.util.HashMap;
import java.util.Map;

import com.binomed.cineshowtime.client.service.geolocation.UserGeolocation;
import com.binomed.cineshowtime.client.service.geolocation.UserGeolocationCallback;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeService;
import com.binomed.cineshowtime.client.service.ws.callback.NearTheatersRequestCallback;
import com.binomed.cineshowtime.client.ui.MainWindow;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
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
		// Load and initialize the window
		MainWindow mainWindow = new MainWindow();
		RootLayoutPanel.get().add(mainWindow);

		testHttpCall();
		testUserLocation();

	}

	private void testHttpCall() {
		CineShowTimeService service = CineShowTimeService.getInstance();
		Map<String, String> params = new HashMap<String, String>();
		params.put("lat", String.valueOf(47.216842));
		params.put("long", String.valueOf(-1.556744));
		service.getNearTheaters(params, new NearTheatersRequestCallback() {
			@Override
			public void onResponse(String response) {
				Window.alert(response);
			}

			@Override
			public void onError(Throwable exception) {
				Window.alert("Error=" + exception.getMessage());
			}

			// @Override
			// public void onNearResp(NearResp nearResp) {
			// Window.alert(nearResp.getTheaterList().get(0).getTheaterName());
			//
			// }
		});
	}

	private void testUserLocation() {
		UserGeolocation.getInstance().getUserGeolocation(new UserGeolocationCallback() {

			@Override
			public void onLocationResponse(JsArray<Placemark> locations) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < locations.length(); i++) {
					sb.append(locations.get(i).getAddress()).append("\n");
				}
				Window.alert("locations : \n" + sb.toString());
			}

			@Override
			public void onLatitudeLongitudeResponse(LatLng latLng) {
				System.out.println("latitude=" + latLng.getLatitude() + ", longitude=" + latLng.getLongitude());
			}

			@Override
			public void onError() {
				Window.alert("Error during geolocation !");
			}
		});
	}
}
