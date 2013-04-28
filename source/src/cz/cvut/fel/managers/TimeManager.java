package cz.cvut.fel.managers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cz.cvut.fel.listeners.TimeListener;

public class TimeManager{
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static Map<TimeListener, ScheduledFuture<?>> timers = 
			new HashMap<TimeListener, ScheduledFuture<?>>();
	private static Map<TimeListener, Integer> times = new HashMap<TimeListener, Integer>();

	public static void addListener(TimeListener l, int seconds){
		final Runnable timer = new Timer(l);
        final ScheduledFuture<?> timerHandle = 
        		scheduler.scheduleAtFixedRate(timer, 0, seconds, TimeUnit.SECONDS);
        timers.put(l, timerHandle);
        times.put(l, seconds);
	}
	
	public static void resetTimer(TimeListener l){
		if(timers.containsKey(l)){
			removeTimer(l);
			addListener(l, times.get(l));
		}
	}
	
	public static void removeListener(TimeListener l){
		if(timers.containsKey(l)){
			removeTimer(l);
			times.remove(l);
		}
	}
	
	public static Date getDateTime(){
		return new Date();
	}
	
// ---------- PRIVATE --------------------------------------------------

	private static void removeTimer(TimeListener l){
		timers.get(l).cancel(true);
		timers.remove(l);
	}
	
	private static class Timer implements Runnable{
		private TimeListener listener;
		
		public Timer(TimeListener l){
			listener = l;
		}
		
        public void run() {
        	listener.onTimeExpired();
    	}
    };
}
