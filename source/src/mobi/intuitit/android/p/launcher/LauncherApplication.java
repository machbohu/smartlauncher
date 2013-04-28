/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * TODO modified
 */

package mobi.intuitit.android.p.launcher;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.roscopeco.ormdroid.ORMDroidApplication;

import cz.cvut.fel.managers.LaunchManager;
import cz.cvut.fel.managers.LogManager;
import cz.cvut.fel.managers.NetworkManager;
import cz.cvut.fel.managers.PhoneStateManager;
import cz.cvut.fel.managers.SmartLocationManager;
import cz.cvut.fel.managers.TimeManager;
import cz.cvut.fel.models.log.BTS;
import cz.cvut.fel.models.log.LocationL;
import cz.cvut.fel.models.log.LogApp;
import cz.cvut.fel.models.log.LogWifi;
import cz.cvut.fel.models.log.PhoneSettings;
import dalvik.system.VMRuntime;

public class LauncherApplication extends Application {
    static final String LOG_TAG = "LauncherApplication";

    @Override
    public void onCreate() {
        VMRuntime.getRuntime().setMinimumHeapSize(4 * 1024 * 1024);
        
        initDB();
        initManagers();
        
        super.onCreate();
    }
    
    private void initDB(){
        // Initialize ORMDroid library
        ORMDroidApplication.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
        	Log.d(LOG_TAG, "DB init");
        }
    }
    
    private void initManagers(){
    	Context c = getApplicationContext();
        // Initiate NetworkManager
        NetworkManager.init(c);
        // Initiate PhoneStateManager
        PhoneStateManager.init(c);
        // Initiate our LocationManager
        SmartLocationManager.init(c);
        // Initiate LaunchManager
        LaunchManager.init(c);
        // Initiate LogManager for events listening
        LogManager logm = new LogManager();
        LaunchManager.addListener(logm);
//        TimeManager.addListener(logm, 1);
        
        // Add LogListener classes for future instantiating 
        // and listening on LogManager's events
        logm.addListener(BTS.class);
        logm.addListener(LocationL.class);
        logm.addListener(LogApp.class);
        logm.addListener(LogWifi.class);
        logm.addListener(PhoneSettings.class);
        
    }
}
