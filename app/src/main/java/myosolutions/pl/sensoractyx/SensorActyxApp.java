package myosolutions.pl.sensoractyx;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jacek on 2017-03-22.
 */

public class SensorActyxApp extends Application {
    
    private static SensorActyxApp instance;

    private SharedPreferences sharedPreferences;


    public static synchronized SensorActyxApp getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }

    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        }
        return sharedPreferences;
    }



}
