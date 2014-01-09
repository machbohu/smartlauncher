package cz.cvut.fel.managers;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Providing informations about current location.
 * 
 * @author machbohu
 */
public class SmartLocationManager {
	private static Context context;
	private static LocationManager locationManager; 
	
	public static void init(Context c){
		context = c;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public static Location getLocation(){
		// TODO better solution - what about old values? Maybe we should listen to loc changes?
		// http://stackoverflow.com/questions/6181704/good-way-of-getting-the-users-location-in-android
		List<String> providers = locationManager.getProviders(true);

		// Loop over the providers backwards, and if you get an accurate location, then break out the loop
		Location loc = null;

		for (int ii=providers.size()-1; ii>=0; ii--) {
			loc = locationManager.getLastKnownLocation(providers.get(ii));
			if (loc != null) break;
		}

		return loc;
	}
}
