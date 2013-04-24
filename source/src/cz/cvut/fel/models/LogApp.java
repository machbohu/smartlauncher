package cz.cvut.fel.models;

import com.roscopeco.ormdroid.Entity;

public class LogApp extends Entity {
	public int id;
	public RunningApp app;
	public LogRecord log;
	
}
