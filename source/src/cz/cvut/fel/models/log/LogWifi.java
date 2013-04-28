package cz.cvut.fel.models.log;

import java.util.LinkedList;
import java.util.List;

import android.net.wifi.ScanResult;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.managers.NetworkManager;


public class LogWifi extends Entity implements LogListener {
	public int id;
	public WifiNetwork wifi;
	public LogRecord log;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		List<ScanResult> wifis = NetworkManager.getAvailableWifis();
		List<Entity> logWifis = new LinkedList<Entity>();
		
		if(wifis != null){
			for(ScanResult sr : wifis){
				LogWifi lw = new LogWifi();
				Boolean connected = NetworkManager.isWifiConnected(sr);
				WifiNetwork wn = WifiNetwork.get(sr.SSID, sr.BSSID, connected); 
				
				if(wn == null){
					wn = new WifiNetwork();
					wn.ssid = sr.SSID;
					wn.bssid = sr.BSSID;
					wn.connected = connected;
				}
				
				lw.log = lr;
				lw.wifi = wn;
				logWifis.add(lw);
			}
		}
		
		return logWifis;
	}
	
}
