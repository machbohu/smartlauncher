package cz.cvut.fel.filters;

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
	 * Remove all applications not having active Wi-Fi
	 * or no information (null)
	 */
	@Override
	public List<App> filter(List<App> apps) {
		if(apps == null) return apps;
		
		WifiInfo wifiInfo = NetworkManager.getConnectedWifi();
		WifiNetworkP wifi = null;
		
		if(wifiInfo.getSSID() != null){
			wifi = WifiNetworkP.get("ssid", wifiInfo.getSSID());
			
			for(App app : apps){
				if(app.wifi != wifi && app.wifi != null){
					apps.remove(app);
				}
			}
		}
		
		return apps;
	}

}
