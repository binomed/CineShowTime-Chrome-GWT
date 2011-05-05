	var currentRequest = null;


function navigate(url) {
  chrome.tabs.getSelected(null, function(tab) {
    chrome.tabs.update(tab.id, {url: url});
  });
}

chrome.omnibox.onInputEntered.addListener(function(text) {
  alert(text);
 
    chrome.management.getAll(function(info) {
    var appCount = 0;
    for (var i = 0; i < info.length; i++) {
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
    var filter = "CineShowTime for Chrome";
    var appCst = null;
    for (var i = 0; i < completeList.length; i++){
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
  
});