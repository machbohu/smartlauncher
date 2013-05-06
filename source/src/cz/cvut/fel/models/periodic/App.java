package cz.cvut.fel.models.periodic;

import java.util.List;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

import cz.cvut.fel.globals.Constants;


public class App extends Entity implements Comparable<App> {
	public int id;
	public String name;
	public BTS bts;
	public WifiNetworkP wifi;
//	public Period period;
//	public Day day;
//	public DayPart dayPart;
	public LocationP loc;
	
	public long time_from;
	public long time_to;
	
	public static List<App> getAll(){
		return Query.query(App.class).executeMulti();
	}
	
	// TODO Getters and filters over DB should be done inside DB library,
	// for example Query.query(App.class).filter(....); because this is ugly.
	public static List<App> filter(String col, Object val){
		return Query.query(App.class).where(Query.eql(col, val)).executeMulti();
	}
	
	public static List<App> filter(String col, Object val, String col2, Object val2){
		return Query.query(App.class).where(Query.and(
				Query.eql(col, val), Query.eql(col2, val2))).executeMulti();
	}
	
	public boolean hasPeriods(){
		return !Query.query(AppPeriod.class).where(Query.eql("app", this)).executeMulti().isEmpty();
	}
	
	public boolean checkPeriod(String period){
		Period p = Period.get(period);
		return p != null && Query.query(AppPeriod.class).where(
				Query.and(Query.eql("app", this), Query.eql("period", p))).execute() != null;
	}
	
	public boolean hasDays(){
		return !Query.query(AppDay.class).where(Query.eql("app", this)).executeMulti().isEmpty();
	}
	
	public boolean checkDay(String day){
		Day d = Day.get(day);
		return d != null && Query.query(AppDay.class).where(
				Query.and(Query.eql("app", this), Query.eql("day", d))).execute() != null;
	}
	
	public boolean hasTime(){
		return time_from != 0 && time_to != 0;
	}
	
	public boolean checkTime(long time){
		return time >= (time_from - Constants.HALF_AN_HOUR) && time <= time_to;
	}

	@Override
	public int compareTo(App another) {
		if(getPriority() < another.getPriority()){
			return -1; 
		}else if(getPriority() == another.getPriority()){
			return 0; 
		}else{
			return 1;
		}
	}
	
// ---------- PRIVATE --------------------------------------
	
	private long getPriority(){
		if(time_from + time_to == 0){
			return Long.MAX_VALUE;
		}else{
			return time_to - time_from;
		}
	}
}
