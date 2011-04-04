package com.binomed.cst.client.ui;

import com.binomed.cst.client.geolocation.UserGeolocation;
import com.binomed.cst.client.geolocation.UserGeolocationCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT.create(MainWindowUiBinder.class);

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	VerticalPanel paramsContent;

	public MainWindow() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		UserGeolocation.getInstance().getUserGeolocation(new UserGeolocationCallback() {

			@Override
			public void onLocationResponse(JsArray<Placemark> locations) {
				Window.alert("locations : OK");
			}

			@Override
			public void onLatitudeLongitudeResponse(LatLng latLng) {
				Window.alert("latitude, longitude : OK");
			}

			@Override
			public void onError() {
				Window.alert("Error during geolocation !");
			}
		});

	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
