package cz.cvut.fel.models.periodic;

import java.sql.Time;

import com.roscopeco.ormdroid.Entity;


public class App extends Entity {
	public int id;
	public BTS bts;
	public WifiNetwork wifi;
	public Period period;
	public Day day;
	public DayPart dayPart;
	public LocationP loc;
	
	public Time from;
	public Time to;
}
