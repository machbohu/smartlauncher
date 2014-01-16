package cz.cvut.fel.managers;

import java.util.LinkedList;
import java.util.List;

import mobi.intuitit.android.p.launcher.Launcher;
import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import cz.cvut.fel.listeners.LaunchListener;

/**
 * Manage launch application info and register launch listeners that
 * should be informed about fired event.
 * 
 * Listen for application launch in {@link Launcher#startActivitySafely}.
 * 
 * Initialized in {@link LauncherApplication#initManagers}
 * 
 * @author machbohu
 */
public class LaunchManager{
	private static List<LaunchListener> listeners = new LinkedList<LaunchListener>();
	private static Intent lastIntent;
	private static ActivityManager activityManager;
	
	public static void init(Context c){
		activityManager = (ActivityManager) c.getSystemService(Activity.ACTIVITY_SERVICE);
	}
	
	public static void onAppLaunch(Intent i) {
		lastIntent = i;
		
		for(LaunchListener l : listeners){
			l.onAppLaunch(i);
		}
		
		lastIntent = null;
	}
	
	public static void addListener(LaunchListener l){
		listeners.add(l);
	}
	
	public static void removeListener(LaunchListener l){
		listeners.remove(l);
	}
	
	public static Intent getLastLaunchedIntent(){
		return lastIntent;
	}
	
	public static String getLastLaunchedAppName(){
		return lastIntent.getComponent().getPackageName();
	}
	
	public static List<RunningAppProcessInfo> getRunningApps(){
		return activityManager.getRunningAppProcesses();
	}
}
