package cz.cvut.fel.managers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.content.Intent;
import android.util.Log;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.listeners.LaunchListener;
import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.listeners.TimeListener;
import cz.cvut.fel.models.log.BTS;
import cz.cvut.fel.models.log.LocationL;
import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.LogRecord;
import cz.cvut.fel.models.log.LogWifi;
import cz.cvut.fel.models.log.PhoneSettings;

/**
 * LogManager listen for specific events ({@link LaunchManager}, {@link TimeManager}) 
 * and is responsible for logging phone state when those are fired.
 * 
 * Initialized in {@link LauncherApplication#initManagers}
 * 
 * @author machbohu
 */
public class LogManager implements LaunchListener, TimeListener{
    private static final String LOG_TAG = "LogManager";
    private static final LogManager instance = new LogManager();
	private List<Class<? extends LogListener>> listeners = new LinkedList<Class<? extends LogListener>>();

	private LogManager(){
		// Add LogListener classes for future instantiating 
        // and listening on LogManager's events
        addListener(BTS.class);
        addListener(LocationL.class);
        addListener(LogApp.class);
        addListener(LogWifi.class);
        addListener(PhoneSettings.class);
	}
	
	public static LogManager getInstance(){
		return instance;
	}
	
	@Override
	public void onAppLaunch(Intent i) {
		// reset timer countdown for this listener
		TimeManager.resetTimer(instance);
		
		saveLogRecord();
	}
	
	@Override
	public void onTimeExpired() {
		saveLogRecord();
	}
	
	public void addListener(Class<? extends LogListener> l){
		listeners.add(l);
	}
	
	public void removeListener(Class<? extends LogListener> l){
		listeners.remove(l);
	}
	
// ------------ PRIVATE -------------------------------
	
	private void saveLogRecord(){
		try{
			List<Entity> entities = new LinkedList<Entity>();
			LogRecord lr = new LogRecord();
			lr.onLogSave(lr);
			
			for(Class<? extends LogListener> l : listeners){
				List<Entity> es = l.newInstance().onLogSave(lr);
				
				if(es != null){
					entities.addAll((es));
				}
			}
			
			lr.save();
			
			for(Entity e : entities){
				e.save();
			}
		} catch (IllegalAccessException e) {
			Log.e(LOG_TAG, Arrays.toString(e.getStackTrace()));
		} catch (InstantiationException e) {
			Log.e(LOG_TAG, Arrays.toString(e.getStackTrace()));
		}
	}
}
