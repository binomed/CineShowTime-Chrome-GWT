package com.binomed.cineshowtime.client;

public class Omnibox {

	public static native void onChangeOmnibox() /*-{
		chrome.omnibox.onInputStarted.addListener(function() {
			$wnd.alert('Yo! Omniman');
		});
	}-*/;

	public static native void alert(String message) /*-{
		$wnd.alert(message);
	}-*/;

	public static native void onInputEntered() /*-{
		chrome.omnibox.onInputEntered.addListener(function(text) {
			navigate("http://codesearch.google.com/codesearch?"
					+ "vert=chromium&as_q=" + text);
			$wnd.alert('Yo! Omniman' + text);
		});
	}-*/;

}
