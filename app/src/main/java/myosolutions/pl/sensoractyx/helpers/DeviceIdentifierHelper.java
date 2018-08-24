package myosolutions.pl.sensoractyx.helpers;

import java.util.Random;

import myosolutions.pl.sensoractyx.constants.IHexFormat;
import myosolutions.pl.sensoractyx.utils.StringUtils;

/**
 * Created by Jacek on 2017-03-22.
 */

public class DeviceIdentifierHelper {

    public static final String DEFAULT_IDENTIFIER = "00000000";
    private static final String TAG = DeviceIdentifierHelper.class.getSimpleName();

    public static String generateDeviceIdentifier(){
        Random r = new Random();
        int randomInt = r.nextInt();
        return String.format(IHexFormat.HEX_FORMAT, randomInt).toUpperCase();

    }

    public static boolean checkIfDeviceIdentifierIsNeeded(){
        boolean isNeeded = StringUtils.containsIgnoreCase(PreferencesHelper.retrieveDeviceIdentifier(),DeviceIdentifierHelper.DEFAULT_IDENTIFIER);
        return isNeeded;
    }

}
