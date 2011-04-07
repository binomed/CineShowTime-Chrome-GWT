package com.binomed.cineshowtime.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class SearchTheater extends Composite {

	private static SearchTheaterUiBinder uiBinder = GWT.create(SearchTheaterUiBinder.class);

	@UiField
	DisclosurePanel searchPanel;
	@UiField
	Hyperlink nearSearch;
	@UiField
	Hyperlink favoriteSearch;

	public SearchTheater() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		// Disclosure panel
		searchPanel.setHeader(new HTML("Rechercher"));
		searchPanel.setAnimationEnabled(true);
		searchPanel.setOpen(true);
	}

	@UiHandler("searchButton")
	void handleSearchClick(ClickEvent e) {
		Window.alert("Search...");
	}

	interface SearchTheaterUiBinder extends UiBinder<Widget, SearchTheater> {
	}
}
