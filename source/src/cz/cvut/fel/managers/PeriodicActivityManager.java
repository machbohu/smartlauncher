package cz.cvut.fel.managers;

import java.util.LinkedList;
import java.util.List;

import cz.cvut.fel.periodicseekers.PeriodicSeeker;
import cz.cvut.fel.periodicseekers.ActiveWifiSeeker;

public class PeriodicActivityManager {
	private static List<PeriodicSeeker> seekers = new LinkedList<PeriodicSeeker>();
	
	public static void init(){
		seekers.add(ActiveWifiSeeker.getInstance());
	}
}
