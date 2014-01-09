package cz.cvut.fel.managers;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import cz.cvut.fel.listeners.TimeListener;
import cz.cvut.fel.periodicseekers.ActiveWifiSeeker;
import cz.cvut.fel.periodicseekers.DaySeeker;
import cz.cvut.fel.periodicseekers.PeriodicSeeker;

/**
 * Manage seekers that are finding periodic occurrences in DB and save those
 * as periodic actions.
 * 
 * It is sort of standalone, periodically launched object.
 * 
 * @author machbohu
 */
public class PeriodicActivityManager implements TimeListener {
	private List<PeriodicSeeker> seekers = new LinkedList<PeriodicSeeker>();
    private static final PeriodicActivityManager instance = new PeriodicActivityManager();

	private PeriodicActivityManager(){}
	
	public static void init(){
		// Launch seekers once a day "onTimeExpired"
		TimeManager.addListener(instance, 86400);
		
		instance.seekers.add(ActiveWifiSeeker.getInstance());
		instance.seekers.add(DaySeeker.getInstance());
	}
	
	public static PeriodicActivityManager getInstance(){
		return instance;
	}

	@Override
	public void onTimeExpired() {
		// TODO new thread for seekers?
		Log.d("PeriodicActivityManager", "Finding...");
		
		for(PeriodicSeeker ps : seekers){
			ps.seek();
		}
	}
}
