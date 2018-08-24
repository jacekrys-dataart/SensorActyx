package myosolutions.pl.sensoractyx.helpers;

import android.content.SharedPreferences;

import myosolutions.pl.sensoractyx.SensorActyxApp;
import myosolutions.pl.sensoractyx.interfaces.IPreferenceKeys;

/**
 * Created by Jacek on 2017-03-22.
 */

public class PreferencesHelper {

    private static SharedPreferences getSharedPrefrences(){
        return SensorActyxApp.getApplication().getSharedPreferences();
    }

    public static void saveDeviceIdentifier(String identifier){
        getSharedPrefrences().edit().putString(IPreferenceKeys.PREFS_DEVICE_IDENTIFIER_KEY, identifier).commit();
    }

    public static String retrieveDeviceIdentifier(){
        return getSharedPrefrences().getString(IPreferenceKeys.PREFS_DEVICE_IDENTIFIER_KEY, DeviceIdentifierHelper.DEFAULT_IDENTIFIER);
    }

    public static void saveSequence(int sequence){
        getSharedPrefrences().edit().putInt(IPreferenceKeys.PREFS_SEQUENCE_KEY, sequence).commit();
    }

    public static int retrieveSequence(){
        return getSharedPrefrences().getInt(IPreferenceKeys.PREFS_SEQUENCE_KEY, 0);
    }

}
