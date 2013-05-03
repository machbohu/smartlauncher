package cz.cvut.fel.models.log;

import java.util.List;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.managers.TimeManager;


public class LogRecord extends Entity implements LogListener {
	public int id;
	public BTS bts;
//	public LogWifi logwifi;
//	public LogApp logapp;
	public PhoneSettings phoneSettings;
	public LocationL loc;
	
	public long date;
	public long time;
	public String day;
	public String period;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		date = TimeManager.getDateSinceEpoch();
		time = TimeManager.getTimeSinceMidnight();
		day = TimeManager.getShortDayName();
		period = TimeManager.getPeriodName();
		
		return null;
	}
	
	public WifiNetworkL getActiveWifi(){
		for(LogWifi logWifi : LogWifi.filter("log", this)){
			if(logWifi.wifi.connected == true) return logWifi.wifi; 
		}
		
		return null;
	}
}
