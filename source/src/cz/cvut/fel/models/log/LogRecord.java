package cz.cvut.fel.models.log;

import java.sql.Time;
import java.util.Date;
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
	
	public Date date;
	public Time time;
	public String day;
	public String period;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		Date datetime = TimeManager.getDateTime();
		return null;
	}
}
