package cz.cvut.fel.models.log;

import java.util.LinkedList;
import java.util.List;

import android.app.ActivityManager.RunningAppProcessInfo;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.managers.LaunchManager;


public class LogApp extends Entity implements LogListener {
	public int id;
	public RunningApp app;
	public LogRecord log;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		List<RunningAppProcessInfo> apps = LaunchManager.getRunningApps();
		List<Entity> logApps = new LinkedList<Entity>();
		// Add launched application
		LogApp la = new LogApp();
		RunningApp ra = RunningApp.get(LaunchManager.getLastLaunchedAppName(), true);
				
		if(ra == null){
			ra = new RunningApp();
			ra.name = LaunchManager.getLastLaunchedAppName();
			ra.launched = true;
		}
		la.app = ra;
		la.log = lr;
		logApps.add(la);
		
		// TODO maybe remove launched app from running apps?
		
		// Add other applications
		for(RunningAppProcessInfo app : apps){
			la = new LogApp();
			ra = RunningApp.get(app.processName, false);
			
			if(ra == null){
				ra = new RunningApp();
				ra.name = app.processName;
				ra.launched = false;
			}
			
			la.log = lr;
			la.app = ra;
			logApps.add(la);
		}
		
		return logApps;
	}
	
}
