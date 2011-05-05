package com.binomed.cineshowtime.client;

import com.google.code.gwt.storage.client.Storage;

public class Omnibox {

	public static void setSearchString(String searchString) {
		Storage localStorage = Storage.getLocalStorage();
		localStorage.setItem("omniSearch", searchString);
	}

	public static String getSearchString() {
		Storage localStorage = Storage.getLocalStorage();
		return localStorage.getItem("omniSearch");
	}
	
	private static boolean canOmniSearch;

	public Omnibox() {
		super();
	}
	
	public static void setCanOmniSearch(boolean canOmniSearch){
		Omnibox.canOmniSearch = canOmniSearch;
	}
	
	public static boolean canOmniSearch(){
		return Omnibox.canOmniSearch;
	}
	
	public static native void registerOmnibox()/*-{
		chrome.windows.getAll({"populate":true},function(windowArray){
			var curWindow = null;	
			var tabName = "CineShowTime Chrome";					
			for (var i = 0; i< windowArray.length; i++){
				curWindow = windowArray[i];
				chrome.tabs.getAllInWindow(curWindow.id, function(tabArray){
					var curTab = null;
					for (var i = 0; i< tabArray.length; i++){
						curTab = tabArray[i];
						if (tabName && curTab.title.search(tabName) < 0) {
							continue;
						}
						@com.binomed.cineshowtime.client.Omnibox::setCanOmniSearch(Z)(false);
					}
				});
			} 
		});
		
	}-*/;
	

	public static native void onChangeOmnibox() /*-{
		chrome.omnibox.onInputStarted.addListener(function() {
			$wnd.alert('Yo! Omniman');
		});
	}-*/;

	public static native void alert(String message) /*-{
		$wnd.alert(message);
	}-*/;

	public static native void onInputEntered() /*-{
		chrome.omnibox.onInputEntered
				.addListener(function(text) {
					var canSearch = @com.binomed.cineshowtime.client.Omnibox::canOmniSearch()();
					
					if (canSearch){
						// We have to check if the application ins't yet launch
						chrome.windows.getAll({"populate":true},function(windowArray){
							var curWindow = null;
							var nbWindow = 0;
							var filter = "CineShowTime";	
							var tabName = "CineShowTime Chrome";					
							for (var i = 0; i< windowArray.length; i++){
								curWindow = windowArray[i];
								chrome.tabs.getAllInWindow(curWindow.id, function(tabArray){
									var curTab = null;
									nbWindow = nbWindow+1;
									var lastWindow = (nbWindow == windowArray.length);
									for (var i = 0; i< tabArray.length; i++){
										curTab = tabArray[i];
										if (tabName && curTab.title.search(tabName) < 0) {
											continue;
										}
										chrome.tabs.remove(curTab.id);
									}
									if (lastWindow){
										@com.binomed.cineshowtime.client.Omnibox::setSearchString(Ljava/lang/String;)(text);
										chrome.management.getAll(function(info) {
											var appCount = 0;
											for ( var i = 0; i < info.length; i++) {
												if (info[i].isApp) {
													appCount++;
												}
											}
											if (appCount == 0) {
												$("search").style.display = "none";
												$("appstore_link").style.display = "";
												return;
											}
											var completeList = info;
											var selectedIndex = 0;
											var appList = [];
											var appCst = null;
											for ( var i = 0; i < completeList.length; i++) {
												var item = completeList[i];
												// Skip extensions and disabled apps.
												if (!item.isApp || !item.enabled) {
													continue;
												}
												if (filter && item.name.search(filter) < 0) {
													continue;
												}
												appCst = item;
												break;
											}
											chrome.management.launchApp(appCst.id);
											window.close(); // Only needed on OSX because of crbug.com/63594
										});
									}
								});
							} 
						});
					}
				});
	}-*/;

}
