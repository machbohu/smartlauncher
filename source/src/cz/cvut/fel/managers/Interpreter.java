package cz.cvut.fel.managers;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mobi.intuitit.android.p.launcher.Launcher;
import cz.cvut.fel.filters.ActiveWifiFilter;
import cz.cvut.fel.filters.Filter;
import cz.cvut.fel.filters.TimeFilter;
import cz.cvut.fel.models.periodic.App;

/**
 * Class used by modified {@link Launcher#rewriteDefaultScreen} to get corresponding applications
 * that should be displayed on the screen.
 * 
 * @author machbohu
 */
public class Interpreter {
	private static List<Filter> filters = new LinkedList<Filter>();
	
	public static void init(){
		filters.add(ActiveWifiFilter.getInstance());
		filters.add(TimeFilter.getInstance());
	}
	
	/**
	 * Get applications that should be displayed on the screen
	 * 
	 * @return list of applications strings (e.g. com.android.contacts)
	 */
	public static Set<String> getCorrespondingApps(){
		Set<String> appNames = new LinkedHashSet<String>();
		List<App> apps = App.getAll();
		
		for(Filter f : filters){
			f.filter(apps);
		}
		
		Collections.sort(apps);
		
		for(App app : apps){
			appNames.add(app.name);
		}
		
		return appNames;
	}
}
