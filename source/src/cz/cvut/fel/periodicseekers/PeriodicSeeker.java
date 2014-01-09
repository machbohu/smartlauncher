package cz.cvut.fel.periodicseekers;

import cz.cvut.fel.managers.PeriodicActivityManager;

/**
 * Seeker browses subset of the DB and seeks periodic patterns (periodic activities). 
 * 
 * Should be implemented by every class that provides seeking functionality
 * and registered by {@link PeriodicActivityManager}.
 * 
 * @author machbohu
 */
public interface PeriodicSeeker {
	public void seek();
}
