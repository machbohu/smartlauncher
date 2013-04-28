package cz.cvut.fel.managers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.util.Log;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.listeners.LaunchListener;
import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.listeners.TimeListener;
import cz.cvut.fel.models.log.LogRecord;

// TODO Singleton?
public class LogManager implements LaunchListener, TimeListener{
    private static final String LOG_TAG = "LogManager";
	private List<Class<? extends LogListener>> listeners = new LinkedList<Class<? extends LogListener>>();

	@Override
	public void onAppLaunch(Intent i) {
		// reset timer countdown for this listener
		TimeManager.resetTimer(this);
		
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
