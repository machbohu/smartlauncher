package cz.cvut.fel.models;

import com.roscopeco.ormdroid.Entity;

public class WifiNetwork extends Entity {
	public int id;
	public String ssid;
	public String bssid;
	public Boolean connected;
}
