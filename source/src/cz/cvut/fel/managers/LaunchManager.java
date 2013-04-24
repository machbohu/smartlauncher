package cz.cvut.fel.managers;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;

import cz.cvut.fel.listeners.LaunchListener;

public class LaunchManager{
	private static List<LaunchListener> listeners = new LinkedList<LaunchListener>();

	public static void onAppLaunch(Intent i) {
		for(LaunchListener l : listeners){
			l.onAppLaunch(i);
		}
	}
	
	public static void addListener(LaunchListener l){
		listeners.add(l);
	}
	
	public static void removeListener(LaunchListener l){
		listeners.remove(l);
	}
}
