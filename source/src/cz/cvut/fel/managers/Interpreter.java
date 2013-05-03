package cz.cvut.fel.managers;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cz.cvut.fel.filters.ActiveWifiFilter;
import cz.cvut.fel.filters.Filter;
import cz.cvut.fel.filters.TimeFilter;
import cz.cvut.fel.models.periodic.App;

public class Interpreter {
	private static List<Filter> filters = new LinkedList<Filter>();
	
	public static void init(){
		filters.add(ActiveWifiFilter.getInstance());
		filters.add(TimeFilter.getInstance());
	}
	
	public static Set<String> getCorrespondingApps(){
		Set<String> appNames = new LinkedHashSet<String>();
		List<App> apps = App.getAll();
		
		for(Filter f : filters){
			f.filter(apps);
		}
		
//		apps = testApps();
		
		Collections.sort(apps);
		
		for(App app : apps){
			appNames.add(app.name);
		}
		
		return appNames;
	}
	
	private static List<App> testApps(){
      List<App> apps = new LinkedList<App>();
      long time = TimeManager.getTimeSinceMidnight();
      App app = new App();
      app.name = "com.android.contacts";
      app.time_from = time-10;
      app.time_to = time+50;
      apps.add(app);
      
      app = new App();
      app.name = "machbohu.soardroid";
      apps.add(app);
      
      app = new App();
      app.name = "com.android.email";
      app.time_from = time-10;
      app.time_to = time+10;
      apps.add(app);
      
      app = new App();
      app.name = "com.android.mms";
      app.time_from = time-10;
      app.time_to = time+40;
      apps.add(app);
      
      app = new App();
      app.name = "com.android.settings";
      app.time_from = time-10;
      app.time_to = time+40;
      apps.add(app);
      
      app = new App();
      app.name = "com.android.contacts";
      app.time_from = time-10;
      app.time_to = time+20;
      apps.add(app);
      
      app = new App();
      app.name = "com.android.gallery";
      app.time_from = time-10;
      app.time_to = time+30;
      apps.add(app);
      
      return apps;
	}
}
