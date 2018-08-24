package myosolutions.pl.sensoractyx.utils;

/**
 * Created by Jacek on 2017-03-22.
 */

public class StringUtils {

    private StringUtils() {}

    public static String Blank = "";
    public static String percent = "%";
    public static String comma = ",";
    public static String dot = ".";

    public static boolean containsIgnoreCase(CharSequence searchIn, CharSequence searchFor) {
        if (searchIn == null || searchFor == null) {
            return false;
        }
        return searchIn.toString().toLowerCase().contains(searchFor.toString().toLowerCase());
    }

}