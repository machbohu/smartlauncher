package cz.cvut.fel.models;

import java.sql.Date;
import java.sql.Time;

import com.roscopeco.ormdroid.Entity;

public class LogRecord extends Entity {
	public int id;
	public BTS bts;
	public LogWifi logwifi;
	public LogApp logapp;
	public PhoneSettings phoneSettings;
	public Location loc;
	
	public Date date;
	public Time time;
	public String day;
	public String period;
}
