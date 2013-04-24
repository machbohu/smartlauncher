package cz.cvut.fel.models;

import com.roscopeco.ormdroid.Entity;

public class PhoneSettings extends Entity {
	public int id;
	public Boolean soundOn;
	public Boolean vibrationOn;
	public Boolean gpsOn;
	public Boolean wifiOn;
	public Boolean airplaneModeOn;
}
