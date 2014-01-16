package mobi.intuitit.android.p.launcher.test;

import cz.cvut.fel.models.periodic.App;
import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.test.ApplicationTestCase;

public class AppTest extends ApplicationTestCase<LauncherApplication> {
	
	public AppTest(){
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
		App app = new App();
		app.name = "test1";
		app.time_from = 10;
		app.time_to = 50;
		
		App app2 = new App();
		app2.name = "test1";
		app2.time_from = 100;
		app2.time_to = 500;
		
		App app3 = new App();
		app3.name = "test1";
		app3.time_from = 100;
		app3.time_to = 500;
		
		App app4 = new App();
		app4.name = "test1";
		
		App app5 = new App();
		app5.name = "test1";
		app5.time_from = 0;
		app5.time_to = Long.MAX_VALUE-1;
		
		assertEquals("App should be less than App2", -1, app.compareTo(app2));
		assertEquals("App2 should be same as App3", 0, app2.compareTo(app3));
		assertEquals("App2 should be greater than App", 1, app2.compareTo(app));
		assertEquals("App4 should be greater than App5", 1, app4.compareTo(app5));
	}
}
