package cz.cvut.fel.filters;

import java.util.Iterator;
import java.util.List;

import android.net.wifi.WifiInfo;
import cz.cvut.fel.managers.NetworkManager;
import cz.cvut.fel.models.periodic.App;
import cz.cvut.fel.models.periodic.WifiNetworkP;

public class ActiveWifiFilter implements Filter {
	private static final ActiveWifiFilter instance = new ActiveWifiFilter();
	
	private ActiveWifiFilter(){}
	
	public static ActiveWifiFilter getInstance(){
		return instance;
	}
	
	/**
	 * Filter list of applications according to connected Wi-Fi.
	 * 
	 * Remove all applications not having actual Wi-Fi.
	 * Don't remove those having Wi-Fi reference set to null. 
	 */
	@Override
	public List<App> filter(List<App> apps) {
		if(apps == null) return apps;
		
		WifiInfo wifiInfo = NetworkManager.getConnectedWifi();
		WifiNetworkP wifi = null;
		
		if(wifiInfo.getSSID() != null){
			// TODO actual SSID is not present in DB?
			wifi = WifiNetworkP.get("ssid", wifiInfo.getSSID());
			Iterator<App> i = apps.iterator();
			
			while(i.hasNext()){
				App app = i.next();
				// TODO can we use != between objects?
				if(app.wifi != wifi && app.wifi != null){
					i.remove();
				}
			}
		}
		
		return apps;
	}

}
