package cz.cvut.fel.models.periodic;

import com.roscopeco.ormdroid.Entity;

public class AppDayPart extends Entity {
	public int id;
	public App app;
	public DayPart dayPart;
}
