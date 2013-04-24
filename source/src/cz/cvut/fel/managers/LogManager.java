package cz.cvut.fel.managers;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import cz.cvut.fel.listeners.LaunchListener;
import cz.cvut.fel.listeners.LogListener;

public class LogManager implements LaunchListener{
	private List<LogListener> listeners = new LinkedList<LogListener>();

	@Override
	public void onAppLaunch(Intent i) {
		
	}
	
	public void addListener(LogListener l){
		listeners.add(l);
	}
	
	public void removeListener(LogListener l){
		listeners.remove(l);
	}
	
	private void saveLogRecord(){
		for(LogListener l : listeners){
			l.onLogSave(logRecord));
		}
	}
}
