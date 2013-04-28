package cz.cvut.fel.models.periodic;

import java.util.List;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.models.log.LogRecord;

public class AppDay extends Entity implements LogListener {
	public int id;
	public App app;
	public Day day;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		// TODO Auto-generated method stub
		return null;
	}	
}
