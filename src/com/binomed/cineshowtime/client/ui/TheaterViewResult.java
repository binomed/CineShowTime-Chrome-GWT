package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
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

	String location, dateSearch, cineSearch, msgError;
	int nbTheaters;
	boolean isLoading, isFavorite, isNear, isDateSearch, isError;

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
		isError = false;
	}

	public void updateResultHeader() {
		if (imageLoadingPanel != null) {
			resultHeaderPabel.remove(imageLoadingPanel);
		}
		if (isFavorite) {
			if (isLoading) {
				firstLabel.setText(I18N.instance.loadingFavorite());
			} else {
				firstLabel.setText(I18N.instance.resultFavorite());
			}
		} else if (isNear) {
			if (isLoading) {
				firstLabel.setText(I18N.instance.loadingNear());
			} else {
				firstLabel.setText(I18N.instance.resultNear(location));
			}
		} else if (isDateSearch || (cineSearch != null)) {
			String displayDate = null;
			if (isDateSearch) {
				displayDate = I18N.instance.searchResultDate(displayDate);
			} else {
				displayDate = I18N.instance.searchResultToday();
			}
			if (isLoading) {
				firstLabel.setText(I18N.instance.loadingSearch(cineSearch != null ? cineSearch : location, displayDate));
			} else {
				firstLabel.setText(I18N.instance.resultSearch(cineSearch != null ? cineSearch : location, displayDate));
			}
		} else if (isError) {
			firstLabel.setText(msgError);

		}
		if (nbTheaters >= 0) {
			secondLabel.setVisible(true);
			secondLabel.setText(I18N.instance.nbTheaters(nbTheaters));
		} else {
			secondLabel.setVisible(false);
		}
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
			isError = false;
			isDateSearch = false;
			dateSearch = null;
			msgError = null;
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
			isError = false;
			cineSearch = null;
			dateSearch = null;
			msgError = null;
		}
	}

	public void setNear(boolean isNear) {
		this.isNear = isNear;
		if (isNear) {
			isFavorite = false;
			isDateSearch = false;
			isError = false;
			cineSearch = null;
			dateSearch = null;
			msgError = null;
		}
	}

	public void setError(String errorMsg) {
		this.msgError = errorMsg;
		this.isError = errorMsg != null;
		if (isError) {
			isNear = false;
			isFavorite = false;
			isDateSearch = false;
			isLoading = false;
			cineSearch = null;
			dateSearch = null;
		}
	}

	interface TheaterViewResultUiBinder extends UiBinder<Widget, TheaterViewResult> {
	}
}
