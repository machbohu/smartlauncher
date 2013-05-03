package cz.cvut.fel.models.periodic;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class WifiNetworkP extends Entity {
	public int id;
	public String ssid;
	public String bssid;
	
	public static WifiNetworkP get(String col, Object val){
		return Query.query(WifiNetworkP.class).where(Query.eql(col, val)).execute();
	}
}
