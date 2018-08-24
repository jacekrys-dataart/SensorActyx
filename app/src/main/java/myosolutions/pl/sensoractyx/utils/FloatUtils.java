package myosolutions.pl.sensoractyx.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jacek on 2017-03-24.
 */

public class FloatUtils {

    public static float AMBIGUOUS_FLOAT = -100f;

    private static String REGEX_PATTERN = "(\\d+(?:[\\.\\,]\\d+)?)";

    private static final String TAG = FloatUtils.class.getSimpleName();

    public static float getFloatFromString(String text){
        if(text.contains(StringUtils.percent)){
            return AMBIGUOUS_FLOAT;
        }


        Pattern regex = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = regex.matcher(text);
        String floatString = StringUtils.Blank;
        if(matcher.find()){
            floatString = matcher.group(1);
        }

        Log.d(TAG, "input: " + text + " floatString: " + floatString);

        if(floatString.contains(StringUtils.comma)){
            floatString = floatString.replaceAll(StringUtils.comma, StringUtils.dot);
        }

        float result = Float.parseFloat(floatString);
        Log.d(TAG, "input: " + text + " floatString: " + floatString + " result: " + result);
        return result;
    }

}
