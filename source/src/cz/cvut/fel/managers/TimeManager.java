package cz.cvut.fel.managers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cz.cvut.fel.listeners.TimeListener;

public class TimeManager{
	public static final String EVEN = "even";
	public static final String ODD = "odd";
	
	// !Shouldn't be modified (methods like set(...))!
	private static Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
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
		return c.getTime();
	}
	
	public static long getDateSinceEpoch(){
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTimeInMillis()/1000L;
	}
	
	public static String getShortDayName(){
		return new SimpleDateFormat("EE").format(getDateTime());
	}
	
	public static String getPeriodName(){
        int week = c.get(Calendar.WEEK_OF_YEAR);
        
        if(week % 2 == 0){
        	return EVEN;
        }else{
        	return ODD;
        }
	}
	
	public static long getTimeSinceMidnight(){
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long now = c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (now - c.getTimeInMillis())/1000L;
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
