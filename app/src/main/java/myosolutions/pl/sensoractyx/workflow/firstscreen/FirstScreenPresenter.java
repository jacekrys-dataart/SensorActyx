package myosolutions.pl.sensoractyx.workflow.firstscreen;

import java.util.HashMap;
import java.util.Map;

import myosolutions.pl.sensoractyx.helpers.DeviceIdentifierHelper;
import myosolutions.pl.sensoractyx.helpers.PreferencesHelper;
import myosolutions.pl.sensoractyx.workflow.FlowMemory;

public class FirstScreenPresenter {

    private static String TAG = FirstScreenPresenter.class.getSimpleName();

    private IFirstScreenCallback callback;
    private FlowMemory flowMemory;

    private HashMap<Integer, Boolean> sensorsMap;

    public FirstScreenPresenter(IFirstScreenCallback callback, FlowMemory flowMemory) {
        this.callback = callback;
        this.flowMemory = flowMemory;
        sensorsMap = new HashMap<>();
    }

    public void generateDeviceIdentifier() {
        callback.onDeviceIdGenerated(generateIdentifier());
    }

    private String generateIdentifier() {

        String identifier;

        if (DeviceIdentifierHelper.checkIfDeviceIdentifierIsNeeded()) {

            identifier = DeviceIdentifierHelper.generateDeviceIdentifier();
            PreferencesHelper.saveDeviceIdentifier(identifier);
        } else {
            identifier = PreferencesHelper.retrieveDeviceIdentifier();
        }

        return identifier;

    }

    public void updateSensorPickup(int sensorKey, boolean isChecked) {
        sensorsMap.put(sensorKey, isChecked);
    }

    public void saveSensorsConfiguration() {
        flowMemory.setSensorList(sensorsMap);
    }


    /**
     * This method checks if at least one sensor was choosen
     */
    public boolean validateNextStep() {
        return sensorsMap.containsValue(true);
    }

    public void setCachedSensorMap() {
        if (!flowMemory.getSensorList().isEmpty()) {
            checkCurrentSensorValues();
        }

    }

    private void checkCurrentSensorValues() {
        for (Map.Entry<Integer, Boolean> object : flowMemory.getSensorList().entrySet()) {
            int key = object.getKey();
            boolean value = object.getValue();

            callback.onValueCached(key, value);
        }
    }



}
