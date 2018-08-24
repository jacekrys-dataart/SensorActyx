package myosolutions.pl.sensoractyx.workflow.firstscreen;


public interface IFirstScreenCallback {

    void onDeviceIdGenerated(String identifier);
    void onNoSensorsSelected(int messageId);
    void onValueCached(int sensorKey, boolean value);
}
