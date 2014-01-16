package mobi.intuitit.android.p.launcher.test;

import java.util.List;

import cz.cvut.fel.managers.TimeManager;
import mobi.intuitit.android.p.launcher.LauncherApplication;
import mobi.intuitit.android.p.launcher.test.mockobjects.MockTimerListener;
import android.test.ApplicationTestCase;

public class TimeManagerTest extends ApplicationTestCase<LauncherApplication> {
	
	public TimeManagerTest(){
		super(LauncherApplication.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		createApplication();
	}
	
    protected void tearDown() throws Exception {
        super.tearDown();
    }
	
	public void testTimer(){
		MockTimerListener mtl = new MockTimerListener();
		TimeManager.addListener(mtl, 3);
		
		try {
		    Thread.sleep(4000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		TimeManager.resetTimer(mtl);
		
		try {
		    Thread.sleep(3000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		TimeManager.removeListener(mtl);
		
		List<Long> times = mtl.getTimes();
		
		assertEquals("Normal timer should be", 3, times.get(1) - times.get(0));
		assertEquals("Reseted timer after one second should be", 4, times.get(2) - times.get(1));
	}
}
