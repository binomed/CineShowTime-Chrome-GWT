package com.binomed.cineshowtime.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class SearchTheater extends Composite {

	private static SearchTheaterUiBinder uiBinder = GWT.create(SearchTheaterUiBinder.class);

	@UiField
	DisclosurePanel searchPanel;
	@UiField
	Hyperlink nearSearch;
	@UiField
	Hyperlink favoriteSearch;
	@UiField
	TextBox locationSearch, movieSearch;
	@UiField
	DateBox dateSearch;

	public SearchTheater() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		searchPanel.setHeader(new HTML("<font color=\"#FFFFFF\"><b>Rechercher</b></font>"));
		searchPanel.setAnimationEnabled(true);
		searchPanel.setOpen(true);

		DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
		dateSearch.setFormat(new DateBox.DefaultFormat(format));
	}

	@UiHandler("searchButton")
	void handleSearchClick(ClickEvent e) {
		Window.alert("Search...");
	}

	interface SearchTheaterUiBinder extends UiBinder<Widget, SearchTheater> {
	}
}
