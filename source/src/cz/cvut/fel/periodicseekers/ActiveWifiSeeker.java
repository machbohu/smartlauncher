package cz.cvut.fel.periodicseekers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.globals.Constants;
import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.LogRecord;
import cz.cvut.fel.models.log.RunningApp;
import cz.cvut.fel.models.log.WifiNetworkL;
import cz.cvut.fel.models.periodic.App;
import cz.cvut.fel.models.periodic.WifiNetworkP;

/**
 * Seek for periodic activities connected with Wi-Fi
 * (application <-> Wi-Fi network).
 * 
 * @author machbohu
 */
public class ActiveWifiSeeker implements PeriodicSeeker {
	private static final ActiveWifiSeeker instance = new ActiveWifiSeeker();
	
	private ActiveWifiSeeker() {}
	
	public static ActiveWifiSeeker getInstance(){
		return instance;
	}
	
	@Override
	public void seek() {
		// Get all launched applications
		List<RunningApp> launchedApps = RunningApp.filter("launched", true);

		for(RunningApp app : launchedApps){
			// Counts of every connected Wi-Fi
			Map<String, Integer> wifisCnt = new HashMap<String, Integer>();
			// Get all Logs linked with launched application
			List<LogApp> logApps = LogApp.filter("app", app);
			
			for(LogApp logApp : logApps){
				// For every Log find active Wi-Fi connection, if available
				LogRecord log = logApp.log;
				WifiNetworkL activeWifi = log.getWifi();
				
				if(activeWifi != null){
					if(wifisCnt.containsKey(activeWifi.ssid)){
						wifisCnt.put(activeWifi.ssid, wifisCnt.get(activeWifi.ssid) + 1);
					}else{
						wifisCnt.put(activeWifi.ssid, 1);
					}
				}
			}
			
			double logCnt = logApps.size();
			
			// TODO maybe we should consider periodic activity as
			// "launched when connected to Wi-Fis A, B and C" or "in a date range"
			// not only for "connected to only one Wi-Fi A"
			for(String key : wifisCnt.keySet()){
				// Check if this could be taken as periodic activity
				// and if so - save it
				if(wifisCnt.get(key)/logCnt >= Constants.PERIODIC_RATE){
					WifiNetworkP wifi = WifiNetworkP.get("ssid", key);
					
					if(wifi == null){
						wifi = new WifiNetworkP();
						wifi.ssid = key;
					}else{
						if(!App.filter("name", app.name, "wifi", wifi).isEmpty()){
							break;
						}
					}
					
					App a = new App();
					a.name = app.name;
					a.wifi = wifi;
					
					// TODO find out if exists in DB instead of filter above
//					if(App.exists(a)){
//						break;
//					}
					
					a.save();
					
					break;
				}
			}
		}
	}

}
