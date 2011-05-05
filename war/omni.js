	var currentRequest = null;


function navigate(url) {
  chrome.tabs.getSelected(null, function(tab) {
    chrome.tabs.update(tab.id, {url: url});
  });
}

chrome.omnibox.onInputEntered.addListener(function(text) {
	// We have to check if the application ins't yet launch
		
	chrome.windows.getAll({"populate":true},function(windowArray){
		var curWindow = null;
		var nbWindow = 0;
		var tabName = "CineShowTime Chrome";				
		for (var i = 0; i< windowArray.length; i++){
			curWindow = windowArray[i];
			chrome.tabs.getAllInWindow(curWindow.id, function(tabArray){
				var curTab = null;
				nbWindow = nbWindow+1;
				var lastWindow = nbWindow == windowArray.length;
				for (var i = 0; i< tabArray.length; i++){
					curTab = tabArray[i];
					//alert("Tab : "+curTab.title+", "+(curTab.title.search(tabName) < 0)+", filter : "+filter);
					if (tabName && curTab.title.search(tabName) < 0) {
						continue;
					}
					chrome.tabs.remove(curTab.id);
				}
				if (lastWindow){
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
						var filter = "CineShowTime for Chrome";				
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
  
});