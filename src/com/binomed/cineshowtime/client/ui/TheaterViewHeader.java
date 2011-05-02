package com.binomed.cineshowtime.client.ui;

import java.util.ArrayList;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.ui.TheaterOpenEvent;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.I18N;
import com.binomed.cineshowtime.client.ui.dialog.MapDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TheaterViewHeader extends Composite {

	private static TheaterViewHeaderUiBinder uiBinder = GWT.create(TheaterViewHeaderUiBinder.class);

	private final IClientFactory clientFactory;
	private final TheaterBean theater;
	private boolean open;
	private boolean isFav = false;

	@UiField
	Label theaterName, theaterPlace, theaterPhone;
	@UiField
	Image theaterFav, theaterOpen, theaterMap;

	public TheaterViewHeader(final IClientFactory clientFactory, final TheaterBean theater) {
		this.clientFactory = clientFactory;
		this.theater = theater;
		open = false;

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		theaterName.setText(I18N.instance.theaterName(theater.getTheaterName(), theater.getMovieMap().size()));
		if (theater.getPlace() != null) {
			theaterPlace.setText(theater.getPlace().getSearchQuery());
		}
		theaterPhone.setText(theater.getPhoneNumber());
		theaterOpen.setResource(CstResource.instance.collapse());

		theaterOpen.addStyleName(CstResource.instance.css().pointerHand());
		theaterFav.addStyleName(CstResource.instance.css().imgRight());
		theaterMap.addStyleName(CstResource.instance.css().imgRight());

		isFav = isFav();
		showTheaterFav();

	}

	private void changeFav() {
		if (isFav) {
			clientFactory.getDataBaseHelper().removeFav(theater);
		} else {
			if (theater.getPlace().getCityName() == null) {
				theater.getPlace().setCityName(clientFactory.getCineShowTimeService().getCurrentCityName().getLocality());
				theater.getPlace().setCountryName(clientFactory.getCineShowTimeService().getCurrentCityName().getCountry());
				theater.getPlace().setCountryNameCode(clientFactory.getCineShowTimeService().getCurrentCityName().getCountry());
				theater.getPlace().setPostalCityNumber(clientFactory.getCineShowTimeService().getCurrentCityName().getPostalCode());
				theater.getPlace().setLatitude(clientFactory.getCineShowTimeService().getCurrentCityName().getPoint().getLatitude());
				theater.getPlace().setLongitude(clientFactory.getCineShowTimeService().getCurrentCityName().getPoint().getLongitude());
			}
			clientFactory.getDataBaseHelper().addFav(theater);
		}
		isFav = !isFav;
	}

	private boolean isFav() {
		boolean isFav = false;
		ArrayList<TheaterBean> theaterFav = clientFactory.getDataBaseHelper().getTheaterFavCache();
		if ((theaterFav != null) && (theaterFav.size() > 0)) {
			for (TheaterBean theater : theaterFav) {
				if (theater.getId().equals(this.theater.getId())) {
					isFav = true;
					break;
				}
			}
		}
		return isFav;
	}

	private void showTheaterFav() {
		if (!isFav) {
			theaterFav.setResource(CstResource.instance.btn_stat_big_off());
		} else {
			theaterFav.setResource(CstResource.instance.btn_stat_big_on());
		}

	}

	@UiHandler("theaterMap")
	public void onTheaterMap(ClickEvent event) {
		if (theater.getPlace() != null) {
			new MapDialog(theater.getTheaterName(), theater.getPlace().getSearchQuery()).center();
		} else {
			// TODO A gerer autrement
			Window.alert("No maps!");
		}

	}

	@UiHandler("theaterFav")
	public void onTheaterFav(ClickEvent event) {
		changeFav();
		showTheaterFav();
	}

	@UiHandler("theaterName")
	public void onTheaterName(ClickEvent event) {
		expandedCollapse();
	}

	@UiHandler("theaterOpen")
	public void onTheaterOpen(ClickEvent event) {
		expandedCollapse();
	}

	private void expandedCollapse() {
		open = !open;
		theaterOpen.setResource(open ? CstResource.instance.expand() : CstResource.instance.collapse());
		clientFactory.getEventBusHandler().fireEvent(new TheaterOpenEvent(open, theater.getId()));
	}

	interface TheaterViewHeaderUiBinder extends UiBinder<Widget, TheaterViewHeader> {
	}
}
