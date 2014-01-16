package mobi.intuitit.android.p.launcher.test.mockobjects;

import java.util.LinkedList;
import java.util.List;

import cz.cvut.fel.listeners.TimeListener;
import cz.cvut.fel.managers.TimeManager;

public class MockTimerListener implements TimeListener {
	private List<Long> times = new LinkedList<Long>();
	
	public MockTimerListener(){
		times.add(TimeManager.getTimeSinceMidnight());
	}
	
	@Override
	public void onTimeExpired() {
		times.add(TimeManager.getTimeSinceMidnight());
	}
	
	public List<Long> getTimes(){
		return times;
	}

}
