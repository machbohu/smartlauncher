package cz.cvut.fel.models.log;

import java.util.List;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.managers.PhoneStateManager;

public class PhoneSettings extends Entity implements LogListener {
	public int id;
	public int profile;
	public Boolean gpsOn;
	public Boolean wifiOn;
	public Boolean airplaneModeOn;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		profile = PhoneStateManager.getProfile();
		gpsOn = PhoneStateManager.isGpsOn();
		wifiOn = PhoneStateManager.isWifiOn();
		airplaneModeOn = PhoneStateManager.isAirplaneModeOn();
		
		PhoneSettings old = PhoneSettings.get(profile, gpsOn, wifiOn, airplaneModeOn);
		
		if(old == null){
			lr.phoneSettings = this;
		}else{
			lr.phoneSettings = old;
		}
		
		return null;
	}
	
	public static PhoneSettings get(int profile, Boolean gpsOn, Boolean wifiOn, Boolean airplaneModeOn){
		return Entity.query(PhoneSettings.class).where(
				Query.and(Query.and(Query.eql("profile", profile), Query.eql("gpsOn", gpsOn)),
				Query.and(Query.eql("wifiOn", wifiOn), Query.eql("airplaneModeOn", airplaneModeOn)))).execute();
	}
}
