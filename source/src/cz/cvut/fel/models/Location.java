package cz.cvut.fel.models;

import com.roscopeco.ormdroid.Entity;

public class Location extends Entity {
	public int id;
	public Double latitude;
	public Double longitude;
	public Double speed;
	public Double elevation;
	public Double accuracy;
}
