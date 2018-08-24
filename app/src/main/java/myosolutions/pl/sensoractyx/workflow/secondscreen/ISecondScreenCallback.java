package myosolutions.pl.sensoractyx.workflow.secondscreen;

import myosolutions.pl.sensoractyx.workflow.secondscreen.data.SensorMessage;

/**
 * Created by Jacek on 2017-03-22.
 */

public interface ISecondScreenCallback {

    void onUpdateView(int sensorKey, int visibility);
    void onUpdateValue(int sensorKey, float value);
    SensorMessage onMakeSensorDataSnapshot();
    void onUpdateMonitor(String message);

}
