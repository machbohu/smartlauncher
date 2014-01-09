package cz.cvut.fel.listeners;

import cz.cvut.fel.managers.TimeManager;

/**
 * Interface must be implemented by every class that needs
 * to listen for timer event and registered by
 * {@link TimeManager}.
 * 
 * @author machbohu
 *
 */
public interface TimeListener {
	public void onTimeExpired();
}
