package cz.cvut.fel.listeners;

import cz.cvut.fel.managers.LaunchManager;
import android.content.Intent;

/**
 * Interface must be implemented by every class that needs
 * to listen for application launch event and registered by
 * {@link LaunchManager}.
 * 
 * @author machbohu
 *
 */
public interface LaunchListener {
	public void onAppLaunch(Intent i);
}
