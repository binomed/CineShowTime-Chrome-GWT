package com.binomed.cineshowtime.client;

import java.util.ArrayList;
import java.util.List;

import com.binomed.cineshowtime.client.ui.coverflow.CoverData;
import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;
import com.binomed.cineshowtime.client.ui.coverflow.layout.BetterCoverflowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
		// ClientFactory contain all view and access to WebService
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		// Load and initialize the window
		RootLayoutPanel.get().add(clientFactory.getMainWindow());
		// testCoverflow();

	}

	public void testCoverflow() {
		Coverflow c = new Coverflow(800, 300, new BetterCoverflowLayout());

		List<CoverData> covers = new ArrayList<CoverData>();
		covers.add(new SimpleCover("1", "Affiche 1", "http://www.google.com/logos/2011/peruelection11-hp.jpg"));
		covers.add(new SimpleCover("2", "Affiche 2", "http://www.google.com/logos/2011/italyculture11-hp.png"));
		covers.add(new SimpleCover("3", "Affiche 3", "http://www.google.com/logos/2011/senegal_ind11-hp.jpg"));
		covers.add(new SimpleCover("4", "Affiche 4", "http://www.google.com/logos/2011/ctvrtek11-hp.jpg"));
		covers.add(new SimpleCover("5", "Affiche 5", "http://www.google.com/logos/2011/childrensday11-hp.png"));
		covers.add(new SimpleCover("6", "Affiche 6", "http://www.google.com/logos/2011/icecreamsundae11-hp.jpg"));
		covers.add(new SimpleCover("7", "Affiche 7", "http://www.google.com/logos/2011/bunsen11-hp.png"));
		covers.add(new SimpleCover("8", "Affiche 8", "http://www.google.com/logos/2011/evliyacelebi11-hp.jpg"));
		covers.add(new SimpleCover("9", "Affiche 9", "http://www.google.com/logos/2011/houdini11-hp.jpg"));
		covers.add(new SimpleCover("10", "Affiche 10", "http://www.google.com/logos/2011/persiannewyear11-hp.jpg"));
		covers.add(new SimpleCover("11", "Affiche 11", "http://www.google.com/logos/2011/holi11-hp.jpg"));
		c.init(covers);

		RootLayoutPanel.get().add(c.getCanvas());
	}

	class SimpleCover implements CoverData {

		public final String id;
		public final String label;
		public String url;

		public SimpleCover(String id, String label, String url) {
			this.id = id;
			this.label = label;
			this.url = url;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public String getCoverUrl() {
			return url;
		}

		@Override
		public void setCoverURL(String coverURL) {
			this.url = coverURL;
		}

		@Override
		public boolean isVideo() {
			return false;
		}

		@Override
		public String getVideoUrl() {
			return null;
		}
	}
}
