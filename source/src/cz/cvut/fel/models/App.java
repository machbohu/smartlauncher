package cz.cvut.fel.models;

import java.sql.Time;

import com.roscopeco.ormdroid.Entity;

public class App extends Entity {
	public int id;
	public BTS_p bts;
	public WifiNetwork_p wifi;
	public Period period;
	public Day day;
	public DayPart dayPart;
	public Location_p loc;
	
	public Time from;
	public Time to;
}
