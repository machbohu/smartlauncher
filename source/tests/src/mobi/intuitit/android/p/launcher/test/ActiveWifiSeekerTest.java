package mobi.intuitit.android.p.launcher.test;

import java.util.LinkedList;
import java.util.List;

import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.test.ApplicationTestCase;

import com.roscopeco.ormdroid.Entity;

import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.LogRecord;
import cz.cvut.fel.models.log.LogWifi;
import cz.cvut.fel.models.log.RunningApp;
import cz.cvut.fel.models.log.WifiNetworkL;
import cz.cvut.fel.models.periodic.App;
import cz.cvut.fel.models.periodic.WifiNetworkP;
import cz.cvut.fel.periodicseekers.ActiveWifiSeeker;

public class ActiveWifiSeekerTest extends ApplicationTestCase<LauncherApplication> {
	private List<Entity> toBeDeleted = new LinkedList<Entity>();
	
	public ActiveWifiSeekerTest(){
		super(LauncherApplication.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		createApplication();
	}
	
	private void customSetUp(){
		// App and Wifi
		RunningApp app = new RunningApp();
		app.name = "Test1";
		app.launched = true;
		app.save();
		toBeDeleted.add(app);
		RunningApp app2 = new RunningApp();
		app2.name = "Test2";
		app2.launched = true;
		app2.save();
		toBeDeleted.add(app2);
		WifiNetworkL wifi = new WifiNetworkL();
		wifi.ssid = "Wifi1";
		wifi.connected = true;
		wifi.save();
		toBeDeleted.add(wifi);
		WifiNetworkL wifi2 = new WifiNetworkL();
		wifi2.ssid = "Wifi2";
		wifi2.connected = true;
		wifi2.save();
		toBeDeleted.add(wifi2);
		// Log entry app <-> wifi
		LogRecord log = new LogRecord();
		log.save();
		toBeDeleted.add(log);
		LogApp la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		LogWifi lw = new LogWifi();
		lw.wifi = wifi;
		lw.log = log;
		lw.save();
		toBeDeleted.add(lw);
		// Log entry app <-> wifi
		log = new LogRecord();
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		lw = new LogWifi();
		lw.wifi = wifi;
		lw.log = log;
		lw.save();
		toBeDeleted.add(lw);
		// Log entry app <-> wifi
		log = new LogRecord();
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		lw = new LogWifi();
		lw.wifi = wifi;
		lw.log = log;
		lw.save();
		toBeDeleted.add(lw);
		// Log entry app <-> wifi2
		log = new LogRecord();
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		lw = new LogWifi();
		lw.wifi = wifi2;
		lw.log = log;
		lw.save();
		toBeDeleted.add(lw);
		// Log entry app2 <-> wifi
		log = new LogRecord();
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app2;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		lw = new LogWifi();
		lw.wifi = wifi;
		lw.log = log;
		lw.save();
		toBeDeleted.add(lw);
		// Log entry app2 <-> wifi2
		log = new LogRecord();
		log.save();
		toBeDeleted.add(log);
		la = new LogApp();
		la.app = app2;
		la.log = log;
		la.save();
		toBeDeleted.add(la);
		lw = new LogWifi();
		lw.wifi = wifi2;
		lw.log = log;
		lw.save();
		toBeDeleted.add(lw);
		
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
		
		ActiveWifiSeeker aws = ActiveWifiSeeker.getInstance();
		aws.seek();
		// Check there is no duality created
		aws.seek();
		
		List<App> apps = App.filter("name", "Test1");
		List<App> apps2 = App.filter("name", "Test2");
		List<WifiNetworkP> wifis = WifiNetworkP.filter("ssid", "Wifi1");

		toBeDeleted.addAll(apps);
		toBeDeleted.addAll(apps2);
		toBeDeleted.addAll(wifis);
		
		assertFalse("No periodic Wifi found", wifis.isEmpty());
		assertEquals("Test1 app rows count", 1, apps.size());
		assertEquals("Test2 app rows count", 0, apps2.size());
		assertEquals("Wifi1 wifi rows count", 1, wifis.size());
		WifiNetworkP wifi = wifis.get(0);
		assertEquals("Wifi for Test1", apps.get(0).wifi, wifi);
	}
}
