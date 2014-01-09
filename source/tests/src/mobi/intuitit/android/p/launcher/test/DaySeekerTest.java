package mobi.intuitit.android.p.launcher.test;

import java.util.LinkedList;
import java.util.List;

import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.test.ApplicationTestCase;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.LogRecord;
import cz.cvut.fel.models.log.RunningApp;
import cz.cvut.fel.models.periodic.App;
import cz.cvut.fel.models.periodic.AppDay;
import cz.cvut.fel.models.periodic.Day;
import cz.cvut.fel.periodicseekers.DaySeeker;

public class DaySeekerTest extends ApplicationTestCase<LauncherApplication> {
	private List<Entity> toBeDeleted = new LinkedList<Entity>();
	
	public DaySeekerTest(){
		super(LauncherApplication.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		createApplication();
	}
	
	private void customSetUp(){
		// App settings
		RunningApp app = new RunningApp();
		app.name = "Test1";
		app.launched = true;
		app.save();
		toBeDeleted.add(app);
//		RunningApp app2 = new RunningApp();
//		app2.name = "Test2";
//		app2.launched = true;
//		app2.save();
//		toBeDeleted.add(app2);
		// Log entry app <-> log
		LogRecord log = new LogRecord();
		log.date = 1385856000;
		log.day = "Monday";
		log.save();
		toBeDeleted.add(log);
		LogApp la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		// Log entry app <-> log
		log = new LogRecord();
		log.date = 1386201600;
		log.day = "Monday";
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		// Log entry app <-> log
		log = new LogRecord();
		log.date = 1386201600;
		log.day = "Tuesday";
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		// Log entry app <-> log
		log = new LogRecord();
		log.date = 1388361600;
		log.day = "Monday";
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);

	}
	
    protected void tearDown() throws Exception {
        for(Entity e : toBeDeleted){
        	if(e != null){
        		e.delete();
        	}
        }
        
        super.tearDown();
    }
	
	public void testSeeking(){
		customSetUp();
		
		DaySeeker ds = DaySeeker.getInstance();
		ds.seek();
		// Check there is no duality created
//		ds.seek();
		
		List<App> apps = App.filter("name", "Test1");
		toBeDeleted.addAll(apps);
//		List<App> apps2 = App.filter("name", "Test2");
//		toBeDeleted.addAll(apps2);
		List<Day> days = Day.filter("Monday");
		toBeDeleted.addAll(days);
		List<AppDay> ads = AppDay.filter("app", apps.get(0), "day", days.get(0));
		toBeDeleted.addAll(ads);
		
		assertFalse("No periodic Day found", days.isEmpty());
		assertEquals("Test1 app rows count", 1, apps.size());
//		assertEquals("Test2 app rows count", 0, apps2.size());
		assertEquals("Monday day rows count", 1, days.size());
		assertEquals("App <-> Day link count", 1, ads.size());
	}
}
