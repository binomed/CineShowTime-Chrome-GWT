package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.model.NearResp;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.service.ws.callback.NearTheatersRequestCallback;
import com.google.gwt.core.client.GWT;
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
	@UiField
	VerticalPanel theatersContent;

	public MainWindow() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// Manage UI Style
		theatersContent.setSpacing(5);

		// Load intial content
		loadTheaters();
	}

	private void loadTheaters() {
		CineShowTimeWS service = CineShowTimeWS.getInstance();
		service.requestNearTheatersFromLatLng(47.216842, -1.556744, new NearTheatersRequestCallback() {
			@Override
			public void onNearResp(NearResp nearResp) {
				if (nearResp != null) {
					for (TheaterBean theater : nearResp.getTheaterList()) {
						theatersContent.add(new TheaterView(theater, nearResp.getMapMovies()));
					}
				}
			}

			@Override
			public void onError(Throwable exception) {
				Window.alert("Error=" + exception.getMessage());
			}
		});
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
