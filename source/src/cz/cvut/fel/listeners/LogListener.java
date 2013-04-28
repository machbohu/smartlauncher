package cz.cvut.fel.listeners;

import java.util.List;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.models.log.LogRecord;

public interface LogListener {
	public List<Entity> onLogSave(LogRecord lr);
}
