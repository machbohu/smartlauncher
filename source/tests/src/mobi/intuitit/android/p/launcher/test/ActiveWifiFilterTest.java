package mobi.intuitit.android.p.launcher.test;

import mobi.intuitit.android.p.launcher.LauncherApplication;
import android.test.ApplicationTestCase;

public class ActiveWifiFilterTest extends ApplicationTestCase<LauncherApplication> {
	
	public ActiveWifiFilterTest(){
		super(LauncherApplication.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		createApplication();
		
		
	}
	
    protected void tearDown() throws Exception {

        
        super.tearDown();
    }
	
	public void testFiltering(){
		// TODO methods
	}
}
