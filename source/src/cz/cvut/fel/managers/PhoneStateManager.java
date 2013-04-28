package cz.cvut.fel.managers;

import android.content.Context;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;

public class PhoneStateManager {
	private static Context context;
	private static LocationManager locManager;
	private static AudioManager audioManager;
	private static WifiManager wifiManager;
	
	public static void init(Context c){
		context = c;
		locManager = (LocationManager)c.getSystemService(Context.LOCATION_SERVICE );
		audioManager = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
		wifiManager = (WifiManager)c.getSystemService(Context.WIFI_SERVICE);
	}
	
	public static int getProfile(){
		return audioManager.getRingerMode();
	}
	
	public static Boolean isGpsOn(){
		return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	public static Boolean isWifiOn(){
		return wifiManager.isWifiEnabled();
	}
	
	public static Boolean isAirplaneModeOn(){
		return Settings.System.getInt(context.getContentResolver(),
		           Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}
}
