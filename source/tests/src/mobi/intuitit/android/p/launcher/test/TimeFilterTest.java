package mobi.intuitit.android.p.launcher.test;

import java.util.LinkedList;
import java.util.List;

import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.filters.TimeFilter;
import cz.cvut.fel.globals.Constants;
import cz.cvut.fel.managers.TimeManager;
import cz.cvut.fel.models.periodic.App;
import cz.cvut.fel.models.periodic.AppDay;
import cz.cvut.fel.models.periodic.AppPeriod;
import cz.cvut.fel.models.periodic.Day;
import cz.cvut.fel.models.periodic.Period;

public class TimeFilterTest extends ApplicationTestCase<LauncherApplication> {
	private List<Entity> toBeDeleted = new LinkedList<Entity>();
	private List<App> apps = new LinkedList<App>();
	private List<String> shouldRemain = new LinkedList<String>();
	
	public TimeFilterTest(){
		super(LauncherApplication.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		createApplication();
		
		long time = TimeManager.getTimeSinceMidnight();
		App app = new App();
		app.name = "test1";
		app.time_from = time - 10;
		app.time_to = time + 50;
		app.save();
		apps.add(app);
		shouldRemain.add(app.name);

		app = new App();
		app.name = "test2";
		app.save();
		apps.add(app);
		shouldRemain.add(app.name);

		app = new App();
		app.name = "test3";
		app.time_from = time - 50;
		app.time_to = time - 20;
		app.save();
		apps.add(app);

		app = new App();
		app.name = "test4";
		app.time_from = time + Constants.HALF_AN_HOUR;
		app.time_to = time + Constants.HALF_AN_HOUR + 40;
		app.save();
		apps.add(app);
		shouldRemain.add(app.name);
		
		app = new App();
		app.name = "test5";
		app.time_from = time + Constants.HALF_AN_HOUR + 20;
		app.time_to = time + Constants.HALF_AN_HOUR + 40;
		app.save();
		apps.add(app);

		app = new App();
		app.name = "test6";
		app.time_from = time - 40;
		app.time_to = time + 10;
		app.save();
		apps.add(app);
		Period p = new Period();
		p.name = TimeManager.getPeriodName();
		p.save();
		toBeDeleted.add(p);
		AppPeriod ap = new AppPeriod();
		ap.app = app;
		ap.period = p;
		ap.save();
		toBeDeleted.add(ap);
		Day d = new Day();
		d.name = TimeManager.getShortDayName();
		d.save();
		toBeDeleted.add(d);
		AppDay ad = new AppDay();
		ad.app = app;
		ad.day = d;
		ad.save();
		toBeDeleted.add(ad);
		shouldRemain.add(app.name);

		app = new App();
		app.name = "test7";
		app.save();
		apps.add(app);
		p = new Period();
		p.name = "Other";
		p.save();
		toBeDeleted.add(p);
		ap = new AppPeriod();
		ap.app = app;
		ap.period = p;
		ap.save();
		toBeDeleted.add(ap);
		
		app = new App();
		app.name = "test8";
		app.save();
		apps.add(app);
		p = new Period();
		p.name = TimeManager.getPeriodName();
		p.save();
		toBeDeleted.add(p);
		ap = new AppPeriod();
		ap.app = app;
		ap.period = p;
		ap.save();
		toBeDeleted.add(ap);
		d = new Day();
		d.name = "Other";
		d.save();
		toBeDeleted.add(d);
		ad = new AppDay();
		ad.app = app;
		ad.day = d;
		ad.save();
		toBeDeleted.add(ad);
		
		toBeDeleted.addAll(apps);
	}
	
    protected void tearDown() throws Exception {
        for(Entity e : toBeDeleted){
        	e.delete();
        }
        
        super.tearDown();
    }
	
	public void testFiltering(){
		TimeFilter tf = TimeFilter.getInstance();
		List<App> fapps = tf.filter(apps);
		
		Log.d("timefiltertest", fapps.toString());
		assertEquals("Count of filtered apps", shouldRemain.size(), fapps.size());
		
		for(int ii=0;ii<fapps.size();ii++){
			assertEquals("App name", shouldRemain.get(ii), fapps.get(ii).name);
		}
	}
}
