package cz.cvut.fel.models.log;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class WifiNetwork extends Entity {
	public int id;
	public String ssid;
	public String bssid;
	public Boolean connected;
	
	public static WifiNetwork get(String ssid, String bssid, Boolean connected){
		return Entity.query(WifiNetwork.class).where(
				Query.and(Query.and(Query.eql("ssid", ssid), Query.eql("bssid", bssid)),
				Query.eql("connected", connected))).execute();
	}
}
