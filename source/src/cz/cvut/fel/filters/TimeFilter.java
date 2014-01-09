package cz.cvut.fel.filters;

import java.util.Iterator;
import java.util.List;

import cz.cvut.fel.managers.TimeManager;
import cz.cvut.fel.models.periodic.App;

public class TimeFilter implements Filter {
	private static final TimeFilter instance = new TimeFilter();
	
	private TimeFilter(){}
	
	public static TimeFilter getInstance(){
		return instance;
	}
	
	/**
	 * Filter list of applications according to actual time,
	 * period and day
	 */
	@Override
	public List<App> filter(List<App> apps) {
		if(apps == null) return apps;
		
		String period = TimeManager.getPeriodName();
		String day = TimeManager.getShortDayName();
		long time = TimeManager.getTimeSinceMidnight();
		Iterator<App> i = apps.iterator();
		
		while(i.hasNext()){
			App app = i.next();
			
			if(app.hasPeriods()){
				if(!app.checkPeriod(period)){
					i.remove();
					continue;
				}
			}
			if(app.hasDays()){
				if(!app.checkDay(day)){
					i.remove();
					continue;
				}
			}
			if(app.hasTime()){
				if(!app.checkTime(time)){
					i.remove();
					continue;
				}
			}
		}
		
		return apps;
	}

}
