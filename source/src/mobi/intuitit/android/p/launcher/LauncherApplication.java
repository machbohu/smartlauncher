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
 * TODO change notice!
 */

package mobi.intuitit.android.p.launcher;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.roscopeco.ormdroid.ORMDroidApplication;

import cz.cvut.fel.managers.Interpreter;
import cz.cvut.fel.managers.LaunchManager;
import cz.cvut.fel.managers.LogManager;
import cz.cvut.fel.managers.NetworkManager;
import cz.cvut.fel.managers.PeriodicActivityManager;
import cz.cvut.fel.managers.PhoneStateManager;
import cz.cvut.fel.managers.SmartLocationManager;
import dalvik.system.VMRuntime;

public class LauncherApplication extends Application {
    static final String LOG_TAG = "LauncherApplication";

    @Override
    public void onCreate() {
        VMRuntime.getRuntime().setMinimumHeapSize(4 * 1024 * 1024);
        
        // [SmartLauncher] initializing
        initDB();
        initManagers();
        
        super.onCreate();
    }
    
    /**
     * Initialize ORMDroid library
     */
    private void initDB(){
        ORMDroidApplication.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
        	Log.d(LOG_TAG, "DB init");
        }
    }
    
    /**
     * Initialize Managers and add Listeners
     */
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
        // Initiate LogManager
//        LogManager logm = LogManager.getInstance();
//        logm.init();
        // Initialize Interpreter
        Interpreter.init();
        // Initialize PeriodicActivityManager
        PeriodicActivityManager.init();
    }
}
