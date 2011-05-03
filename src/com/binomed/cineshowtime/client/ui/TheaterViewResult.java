package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.resources.CstResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TheaterViewResult extends Composite {

	private static TheaterViewResultUiBinder uiBinder = GWT.create(TheaterViewResultUiBinder.class);

	@UiField
	VerticalPanel resultHeaderPabel;
	@UiField
	Label firstLabel, secondLabel;

	String location, dateSearch, cineSearch;
	int nbTheaters;
	boolean isLoading, isFavorite, isNear, isDateSearch;

	SimplePanel imageLoadingPanel;

	public void setClientFactory() {
	}

	public TheaterViewResult() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		updateResultHeader();
		location = "no location";
		nbTheaters = 0;
		isLoading = true;
		isFavorite = false;
		isNear = false;
		isDateSearch = false;
	}

	public void updateResultHeader() {
		if (imageLoadingPanel != null) {
			resultHeaderPabel.remove(imageLoadingPanel);
		}
		if (isFavorite) {
			if (isLoading) {
				firstLabel.setText("Chargement des cinémas favoris...");
			} else {
				firstLabel.setText("Mes cinémas favoris");
			}
		} else if (isNear) {
			if (isLoading) {
				firstLabel.setText("Chargement des cinémas proche de ma position...");
			} else {
				firstLabel.setText("Cinémas proche de ma position : " + location);
			}
		} else if (isDateSearch) {
			if (isLoading) {
				firstLabel.setText("Chargement des cinémas de " + location + " à partir du " + dateSearch);
			} else {
				firstLabel.setText("Cinémas de " + location + " à partir du " + dateSearch);
			}
		} else if (cineSearch != null) {
			if (isLoading) {
				firstLabel.setText("Chargement des cinémas pour " + cineSearch);
			} else {
				firstLabel.setText("Cinémas trouvés pour " + cineSearch);
			}
		}
		secondLabel.setText(nbTheaters + " Cinémas");
		if (isLoading) {
			addLoadingImage();
		}
	}

	private void addLoadingImage() {
		if (imageLoadingPanel == null) {
			imageLoadingPanel = new SimplePanel();
			Image loadingImg = new Image(CstResource.instance.movie_countdown());
			loadingImg.addStyleName(CstResource.instance.css().center());
			imageLoadingPanel.add(loadingImg);
			imageLoadingPanel.setWidth("100%");
			imageLoadingPanel.addStyleName(CstResource.instance.css().center());
		}
		resultHeaderPabel.add(imageLoadingPanel);
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDateSearch(String dateSearch) {
		this.dateSearch = dateSearch;
		if (dateSearch != null) {
			isDateSearch = true;
			isNear = false;
			isFavorite = false;
			cineSearch = null;
		}
	}

	public void setCineSearch(String cineSearch) {
		this.cineSearch = cineSearch;
		if (cineSearch != null) {
			isNear = false;
			isFavorite = false;
			isDateSearch = false;
			dateSearch = null;
		}
	}

	public void setNbTheaters(int nbTheaters) {
		this.nbTheaters = nbTheaters;
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
		if (isFavorite) {
			isNear = false;
			isDateSearch = false;
			cineSearch = null;
			dateSearch = null;
		}
	}

	public void setNear(boolean isNear) {
		this.isNear = isNear;
		if (isNear) {
			isFavorite = false;
			isDateSearch = false;
			cineSearch = null;
			dateSearch = null;
		}
	}

	interface TheaterViewResultUiBinder extends UiBinder<Widget, TheaterViewResult> {
	}
}
