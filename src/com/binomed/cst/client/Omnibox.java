package com.binomed.cst.client;

public class Omnibox {

	public static native void onChangeOmnibox() /*-{
		chrome.omnibox.onInputStarted.addListener(function() {
			$wnd.alert('Yo! Omniman');
		});
	}-*/;
	
	public static native void alert(String message) /*-{
		$wnd.alert(message);
	}-*/;
	
}
