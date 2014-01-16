package cz.cvut.fel.periodicseekers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.globals.Constants;
import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.RunningApp;
import cz.cvut.fel.models.periodic.App;
import cz.cvut.fel.models.periodic.AppDay;
import cz.cvut.fel.models.periodic.Day;

/**
 * Seek for periodic activities connected with day
 * (application <-> day).
 * 
 * @author machbohu
 */
public class DaySeeker implements PeriodicSeeker {
	private static final DaySeeker instance = new DaySeeker();
	
	private DaySeeker() {}
	
	public static DaySeeker getInstance(){
		return instance;
	}
	
	@Override
	public void seek() {
		// Get all launched applications
		List<RunningApp> launchedApps = RunningApp.filter("launched", true);

		for(RunningApp app : launchedApps){
			// Counts of every day app was launched
			Map<String, Integer> daysCnt = new HashMap<String, Integer>();
			// Get all Logs linked with launched application
			List<LogApp> logApps = LogApp.filter("app", app);
			
			if(logApps.isEmpty()){
				continue;
			}
			
			long from = logApps.get(0).log.date;
			long to = logApps.get(logApps.size()-1).log.date;
			double weeks = (to - from)/60/60/24/7;
			
			// Get app launching rates of every week day
			for(LogApp logApp : logApps){
				// For every Log get day and increment his rate
				String day = logApp.log.day;
				
				if(daysCnt.containsKey(day)){
					daysCnt.put(day, daysCnt.get(day) + 1);
				}else{
					daysCnt.put(day, 1);
				}
			}
			
			// Check if this could be taken as periodic activity
			// and if so - save it (launching rate is over PERIODIC_RATE)
			for(String key : daysCnt.keySet()){
				if(daysCnt.get(key)/weeks >= Constants.PERIODIC_RATE){
					Day day = Day.get(key);
					
					if(day == null){
						day = new Day();
						day.name = key;
						day.save();
					}else{
						boolean already_exists = false;
						
						for(App a : App.filter("name", app.name)){
							if(!AppDay.filter("app", a, "day", day).isEmpty()){
								already_exists = true;
								break;
							}
						}
						
						if(already_exists){
							continue;
						}
					}
					
					App a = new App();
					a.name = app.name;
					AppDay appDay = new AppDay();
					appDay.app = a;
					appDay.day = day;
					// Should save also app and day
					appDay.save();
				}
			}
		}
	}

}
