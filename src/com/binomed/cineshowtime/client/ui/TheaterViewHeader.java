package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.TheaterOpenEvent;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TheaterViewHeader extends Composite {

	private static TheaterViewHeaderUiBinder uiBinder = GWT.create(TheaterViewHeaderUiBinder.class);

	private final IClientFactory clientFactory;
	private final TheaterBean theater;
	private boolean open;

	@UiField
	Label theaterName;
	@UiField
	Image theaterFav;

	public TheaterViewHeader(final IClientFactory clientFactory, final TheaterBean theater) {
		this.clientFactory = clientFactory;
		this.theater = theater;
		open = false;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// HTML headerHtml = new HTML("<font color=\"#FFFFFF\">" + theater.getTheaterName() + "</font>");
		// theaterName.add(headerHtml);
		theaterName.setText(theater.getTheaterName());

		showTheaterFav();

	}

	private boolean flag = false;// TODO to remove

	private void changeFav() {
		// TODO faire qqe chose
		flag = !flag;
	}

	private boolean isFav() {
		boolean isFav = true;
		// TODO faire qqe chose
		isFav = flag;
		return isFav;
	}

	private void showTheaterFav() {
		boolean isFav = isFav();
		if (isFav) {
			theaterFav.setResource(CstResource.instance.btn_stat_big_off());
		} else {
			theaterFav.setResource(CstResource.instance.btn_stat_big_on());
		}

	}

	@UiHandler("theaterFav")
	public void onTheaterFav(ClickEvent event) {
		changeFav();
		showTheaterFav();
	}

	@UiHandler("theaterName")
	public void onTheaterName(ClickEvent event) {
		open = !open;
		clientFactory.getEventBusHandler().fireEvent(new TheaterOpenEvent(open, theater.getId()));
	}

	interface TheaterViewHeaderUiBinder extends UiBinder<Widget, TheaterViewHeader> {
	}
}
