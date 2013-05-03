package cz.cvut.fel.models.log;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class WifiNetworkL extends Entity {
	public int id;
	public String ssid;
	public String bssid;
	public boolean connected;
	
	public static WifiNetworkL get(String ssid, String bssid, boolean connected){
		return Entity.query(WifiNetworkL.class).where(
				Query.and(Query.and(Query.eql("ssid", ssid), Query.eql("bssid", bssid)),
				Query.eql("connected", connected))).execute();
	}
}
