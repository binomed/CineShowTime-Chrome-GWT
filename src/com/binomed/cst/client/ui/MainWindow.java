package com.binomed.cst.client.ui;

import com.binomed.cst.client.ui.coverflow.Coverflow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends Composite {

	private static MainWindowUiBinder uiBinder = GWT.create(MainWindowUiBinder.class);

	@UiField
	TabLayoutPanel appBodyPanel;
	@UiField
	VerticalPanel theatersContent;
	@UiField
	VerticalPanel paramsContent;

	public MainWindow() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// Images url to load in the coverflow
		final String[] imagesUrls = new String[] { "http://www.google.fr/movies/image?tbn=5fb488cff09bee9a&size=100x150", //
				"http://www.google.fr/movies/image?tbn=6f9e9a2fd2f45c86&size=100x150", //
				"http://www.google.fr/movies/image?tbn=272babc86fbdee70&size=100x150", //
				"http://www.google.fr/movies/image?tbn=a6526f9c6231998c&size=100x150", //
				"http://www.google.fr/movies/image?tbn=78ff8d3e5f3c2f93&size=100x150", //
				"http://www.google.fr/movies/image?tbn=4456a070bd91e0f3&size=100x150", //
				"http://www.google.fr/movies/image?tbn=afa72d7f8fb104f8&size=100x150" };

		// Make a new coverflow
		Coverflow coverflow = new Coverflow(800, 300);
		coverflow.init(imagesUrls);

		// Add coverflow to the view
		DisclosurePanel theater1Panel = new DisclosurePanel();
		theater1Panel.setHeader(new HTML("Cinéma 1"));
		theater1Panel.setOpen(true);
		theater1Panel.setAnimationEnabled(true);
		theater1Panel.add(coverflow.getCanvas());
		theatersContent.add(theater1Panel);
	}

	interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
	}
}
