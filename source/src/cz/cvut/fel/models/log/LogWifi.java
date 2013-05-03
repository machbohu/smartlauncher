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
	public WifiNetworkL wifi;
	public LogRecord log;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		List<ScanResult> wifis = NetworkManager.getAvailableWifis();
		List<Entity> logWifis = new LinkedList<Entity>();
		
		if(wifis != null){
			for(ScanResult sr : wifis){
				LogWifi lw = new LogWifi();
				boolean connected = NetworkManager.isWifiConnected(sr);
				WifiNetworkL wn = WifiNetworkL.get(sr.SSID, sr.BSSID, connected); 
				
				if(wn == null){
					wn = new WifiNetworkL();
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
	
	public static List<LogWifi> filter(String col, Object val){
		return Query.query(LogWifi.class).where(Query.eql(col, val)).executeMulti();
	}
}
