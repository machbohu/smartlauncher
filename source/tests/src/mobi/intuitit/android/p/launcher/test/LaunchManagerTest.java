package mobi.intuitit.android.p.launcher.test;

import java.util.List;

import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.test.ApplicationTestCase;
import cz.cvut.fel.managers.LaunchManager;
import cz.cvut.fel.managers.LogManager;
import cz.cvut.fel.managers.TimeManager;
import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.LogRecord;

public class LaunchManagerTest extends ApplicationTestCase<LauncherApplication> {
	
	public LaunchManagerTest(){
		super(LauncherApplication.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
	}
	
    protected void tearDown() throws Exception {
        super.tearDown();
    }
	
	public void testLauncherAndLogger(){
		createApplication();
		// Android testing environment behaves in a very(!) strange way (decent words)
		// and add identical LogManager to listen twice (despite registered only once),
		// thus we must remove one listener.
		// This does not happen when running application in a standard way (tested).
		LaunchManager.removeListener(LogManager.getInstance());
		
		PackageManager manager = getContext().getPackageManager();
		Intent i = manager.getLaunchIntentForPackage("com.android.contacts");
	    i.addCategory(Intent.CATEGORY_LAUNCHER);
		
	    long date = TimeManager.getDateSinceEpoch();
	    long time = TimeManager.getTimeSinceMidnight();
	    
		LaunchManager.onAppLaunch(i);
		
		List<LogRecord> lrs = LogRecord.getAll();
		LogRecord lr = lrs.get(lrs.size()-1);
		
		assertEquals("LogRecord date ", date, lr.date);
		assertTrue("LogRecord time should be greater or equal ", lr.time >= time);
		
		List<LogApp> las = LogApp.filter("log", lr);
		
		assertTrue("LogApps should not be empty ", !las.isEmpty());
		
		for(LogApp la : las){
			la.delete();
		}
		lr.delete();
	}
}
