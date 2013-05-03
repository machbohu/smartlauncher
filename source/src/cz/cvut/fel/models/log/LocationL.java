package cz.cvut.fel.models.log;

import java.util.List;

import android.location.Location;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.managers.SmartLocationManager;

public class LocationL extends Entity implements LogListener {
	public int id;
	public double latitude;
	public double longitude;
	public float speed;
	public double elevation;
	public float accuracy;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		Location loc = SmartLocationManager.getLocation();
		
		if(loc != null){
			latitude = loc.getLatitude();
			longitude = loc.getLongitude();
			speed = loc.getSpeed();
			elevation = loc.getAltitude();
			accuracy = loc.getAccuracy();
			
			lr.loc = this;
		}
		
		return null;
	}
}
