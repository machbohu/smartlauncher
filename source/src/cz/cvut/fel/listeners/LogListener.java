package cz.cvut.fel.listeners;

import cz.cvut.fel.models.LogRecord;

public interface LogListener {
	public void onLogSave(LogRecord l);
}
