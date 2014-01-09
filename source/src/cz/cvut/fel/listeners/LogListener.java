package cz.cvut.fel.listeners;

import java.util.List;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.managers.LogManager;
import cz.cvut.fel.models.log.LogRecord;

/**
 * Interface must be implemented by every class that needs
 * to listen for logging event and registered by
 * {@link LogManager}.
 * 
 * @author machbohu
 *
 */
public interface LogListener {
	public List<Entity> onLogSave(LogRecord lr);
}
