package cz.cvut.fel.filters;

import java.util.List;

import cz.cvut.fel.managers.TimeManager;
import cz.cvut.fel.models.periodic.App;

public class TimeFilter implements Filter {
	private static final TimeFilter instance = new TimeFilter();
	
	private TimeFilter(){}
	
	public static TimeFilter getInstance(){
		return instance;
	}
	
	@Override
	public List<App> filter(List<App> apps) {
		if(apps == null) return apps;
		
		String period = TimeManager.getPeriodName();
		String day = TimeManager.getShortDayName();
		long time = TimeManager.getTimeSinceMidnight();
		
		for(App app : apps){
			if(app.hasPeriods()){
				if(!app.checkPeriod(period)){
					apps.remove(app);
				}
			}else{
				continue;
			}
			if(app.hasDays()){
				if(!app.checkDay(day)){
					apps.remove(app);
				}
			}else{
				continue;
			}
			if(app.hasTime()){
				if(!app.checkTime(time)){
					apps.remove(app);
				}
			}else{
				continue;
			}
		}
		
		return apps;
	}

}
