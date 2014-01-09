package cz.cvut.fel.managers;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

/**
 * NetworkManager provides information about network state (BTS, Wi-Fi)
 * 
 * @author machbohu
 */
public class NetworkManager {
	private static Context context;
	private static TelephonyManager telephonyManager;
	private static WifiManager wifiManager;
	
	public static void init(Context c){
		context = c;
		telephonyManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
		wifiManager = (WifiManager)c.getSystemService(Context.WIFI_SERVICE);
	}
	
	public static int getAreaID(){
		if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
		    final GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
		    if (location != null) {
		        return location.getLac();
		    }
		}else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
		    final CdmaCellLocation location = (CdmaCellLocation) telephonyManager.getCellLocation();
		    if (location != null) {
		        return location.getNetworkId();
		    }
		}
		
		return -1;
	}
	
	public static int getCellID(){
		if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
		    final GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
		    if (location != null) {
		        return location.getCid();
		    }
		}else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
		    final CdmaCellLocation location = (CdmaCellLocation) telephonyManager.getCellLocation();
		    if (location != null) {
		        return location.getSystemId();
		    }
		}
		
		return -1;
	}
	
	public static List<ScanResult> getAvailableWifis(){
		return wifiManager.getScanResults();
	}
	
	public static WifiInfo getConnectedWifi(){
		return wifiManager.getConnectionInfo();
	}
	
	public static Boolean isWifiConnected(ScanResult sr){
		WifiInfo active = getConnectedWifi();
		
		if(active == null) return false;
		
		return (sr.SSID.equals(active.getSSID()) && sr.BSSID.equals(active.getBSSID()));
	}
}
