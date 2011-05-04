package com.binomed.cineshowtime.client.ui;

import java.util.HashMap;
import java.util.Map.Entry;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.db.PrefDBEvent;
import com.binomed.cineshowtime.client.handler.db.PrefDbHandler;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ParameterView extends Composite {

	private static ParameterViewUiBinder uiBinder = GWT.create(ParameterViewUiBinder.class);

	private IClientFactory clientFactory;
	private HashMap<String, Integer> timeAddsMap, timeFormatMap;
	private HashMap<String, String> modificationMap;

	@UiField
	CaptionPanel preferenceCatGen;
	@UiField
	Button preferenceGenResetDb, preferenceValidModification;
	@UiField
	ListBox preferenceGenTimeFormat, preferenceGenTimeAdds;

	public void setClientFactory(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;

		if (!clientFactory.getDataBaseHelper().isPreferenceInCache()) {
			clientFactory.getEventBusHandler().addHandler(PrefDBEvent.TYPE, prefHandler);
		} else {
			show();
		}

	}

	public ParameterView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		preferenceCatGen.setCaptionText(I18N.instance.preference_gen_cat());

		timeFormatMap = new HashMap<String, Integer>();
		preferenceGenTimeFormat.addStyleName("demo-ListBox");
		timeFormatMap.put("12", 0);
		preferenceGenTimeFormat.addItem("12h", "12");
		timeFormatMap.put("24", 1);
		preferenceGenTimeFormat.addItem("24h", "24");

		timeAddsMap = new HashMap<String, Integer>();
		preferenceGenTimeAdds.addStyleName("demo-ListBox");
		timeAddsMap.put("00", 0);
		preferenceGenTimeAdds.addItem("00m", "00");
		timeAddsMap.put("05", 1);
		preferenceGenTimeAdds.addItem("05m", "05");
		timeAddsMap.put("10", 2);
		preferenceGenTimeAdds.addItem("15m", "10");
		timeAddsMap.put("15", 3);
		preferenceGenTimeAdds.addItem("15m", "15");
		timeAddsMap.put("20", 4);
		preferenceGenTimeAdds.addItem("20m", "20");
		timeAddsMap.put("25", 5);
		preferenceGenTimeAdds.addItem("25m", "25");
		timeAddsMap.put("30", 6);
		preferenceGenTimeAdds.addItem("30m", "30");
		timeAddsMap.put("35", 7);
		preferenceGenTimeAdds.addItem("35m", "35");
		timeAddsMap.put("40", 8);
		preferenceGenTimeAdds.addItem("40m", "40");
		timeAddsMap.put("45", 9);
		preferenceGenTimeAdds.addItem("45m", "45");
		timeAddsMap.put("50", 10);
		preferenceGenTimeAdds.addItem("50m", "50");

		preferenceGenTimeFormat.addChangeHandler(new PrefChangeHandler(I18N.instance.preference_gen_key_time_format()));
		preferenceGenTimeAdds.addChangeHandler(new PrefChangeHandler(I18N.instance.preference_gen_key_time_adds()));
		modificationMap = new HashMap<String, String>();

		preferenceValidModification.setEnabled(false);
		enableModification(false);
	}

	private void enableModification(boolean enable) {
		preferenceGenTimeFormat.setEnabled(enable);
		preferenceGenTimeAdds.setEnabled(enable);
	}

	private void show() {
		String timeFormat = clientFactory.getDataBaseHelper().readPref(I18N.instance.preference_gen_key_time_format());
		String timeAdds = clientFactory.getDataBaseHelper().readPref(I18N.instance.preference_gen_key_time_adds());

		preferenceGenTimeFormat.setSelectedIndex(timeFormatMap.get(timeFormat));
		preferenceGenTimeAdds.setSelectedIndex(timeAddsMap.get(timeAdds));
	}

	@UiHandler("preferenceGenResetDb")
	public void onCleanDataBase(ClickEvent event) {
		clientFactory.getDataBaseHelper().clean();
	}

	@UiHandler("preferenceValidModification")
	public void onPersist(ClickEvent event) {
		preferenceValidModification.setEnabled(false);
		for (Entry<String, String> modification : modificationMap.entrySet()) {
			clientFactory.getDataBaseHelper().setPreference(modification.getKey(), modification.getValue());
		}
	}

	private PrefDbHandler prefHandler = new PrefDbHandler() {

		@Override
		public void onError(DataServiceException exception) {
		}

		@Override
		public void prefValues(HashMap<String, String> preferencesValues) {
			show();
			enableModification(true);
		}

		@Override
		public void prefValue(String key, String value) {
		}
	};

	class PrefChangeHandler implements ChangeHandler {

		private String key;

		public PrefChangeHandler(String key) {
			super();
			this.key = key;
		}

		@Override
		public void onChange(ChangeEvent event) {
			if (StringUtils.equalsIC(key, I18N.instance.preference_gen_key_time_format())) {
				modificationMap.put(I18N.instance.preference_gen_key_time_format(), preferenceGenTimeFormat.getValue(preferenceGenTimeFormat.getSelectedIndex()));
			} else if (StringUtils.equalsIC(key, I18N.instance.preference_gen_key_time_adds())) {
				modificationMap.put(I18N.instance.preference_gen_key_time_adds(), preferenceGenTimeAdds.getValue(preferenceGenTimeAdds.getSelectedIndex()));

			}
			preferenceValidModification.setEnabled(true);

		}

	}

	interface ParameterViewUiBinder extends UiBinder<Widget, ParameterView> {
	}
}
