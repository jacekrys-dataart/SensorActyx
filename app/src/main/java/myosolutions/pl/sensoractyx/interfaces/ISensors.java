package myosolutions.pl.sensoractyx.interfaces;

/**
 * Created by Jacek on 2017-03-22.
 */

public interface ISensors {

    interface ISensorKeys {
        int GRAVITY = 1;
        int ACCELEROMETER = 2;
        int ROTATION = 3;
        int GYROSCOPE = 4;
        int LIGHT = 5;
    }

    interface ISensorNames {
        String GRAVITY = "gravity_sensor";
        String ACCELEROMETER = "accelerometer_sensor";
        String ROTATION = "rotation_vector_sensor";
        String GYROSCOPE = "gyroscope_sensor";
        String LIGHT = "light_sensor";
    }

    interface ISensorMessage {
        String DEVICE = "device";
        String SEQUENCE = "sequence";
        String DATA = "data";
    }


}
