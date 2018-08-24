package myosolutions.pl.sensoractyx.workflow.secondscreen;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Map;

import myosolutions.pl.sensoractyx.R;
import myosolutions.pl.sensoractyx.helpers.PreferencesHelper;
import myosolutions.pl.sensoractyx.interfaces.ISensors;
import myosolutions.pl.sensoractyx.workflow.DataManager;
import myosolutions.pl.sensoractyx.workflow.FlowMemory;
import myosolutions.pl.sensoractyx.workflow.firstscreen.FirstScreenPresenter;
import myosolutions.pl.sensoractyx.workflow.secondscreen.data.SensorMessage;

/**
 * Created by Jacek on 2017-03-22.
 */

public class SecondScreenPresenter implements SensorEventListener, IWebSocketCallback, DataManager.IPrepareSensorMessageCallback {

    private static String TAG = FirstScreenPresenter.class.getSimpleName();

    private ISecondScreenCallback callback;
    private FlowMemory flowMemory;
    private DataManager dataManager;

    private Activity activity;

    private SensorManager sensorManager;

    public SecondScreenPresenter(ISecondScreenCallback callback, FlowMemory flowMemory, DataManager dataManager, Activity ac) {
        this.callback = callback;
        this.flowMemory = flowMemory;
        this.dataManager = dataManager;
        this.activity = ac;
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    public void createWebsocketConnection() {
        dataManager.createWebsocketConnection(this, this);
    }


    /**
     * This method generates sensor views and registers sensor listeners for chosen sensors
     */
    public void generateSensorViews() {
        for (Map.Entry<Integer, Boolean> object : flowMemory.getSensorList().entrySet()) {
            int key = object.getKey();
            boolean value = object.getValue();
            if (value) {
                callback.onUpdateView(key, View.VISIBLE);
                registerSensorListener(key);
            }
        }
    }


    public void unregisterSenrorListeners() {
        sensorManager.unregisterListener(this);
    }

    private void registerSensorListener(int sensorKey) {
        boolean result;

        switch (sensorKey) {
            case ISensors.ISensorKeys.ACCELEROMETER:

                result = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
                if (!result) {
                    Toast.makeText(activity, R.string.text_accelerometr_is_not_available, Toast.LENGTH_SHORT).show();
                    callback.onUpdateView(sensorKey, View.GONE);
                }

                break;
            case ISensors.ISensorKeys.GYROSCOPE:

                result = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
                if (!result) {
                    Toast.makeText(activity, R.string.text_gyroscope_is_not_available, Toast.LENGTH_SHORT).show();
                    callback.onUpdateView(sensorKey, View.GONE);
                }

                break;
            case ISensors.ISensorKeys.GRAVITY:

                result = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_GAME);
                if (!result) {
                    Toast.makeText(activity, R.string.text_gravity_is_not_available, Toast.LENGTH_SHORT).show();
                    callback.onUpdateView(sensorKey, View.GONE);
                }

                break;
            case ISensors.ISensorKeys.ROTATION:

                result = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_GAME);
                if (!result) {
                    Toast.makeText(activity, R.string.text_rotation_is_not_available, Toast.LENGTH_SHORT).show();
                    callback.onUpdateView(sensorKey, View.GONE);
                }

                break;
            case ISensors.ISensorKeys.LIGHT:

                result = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_GAME);
                if (!result) {
                    Toast.makeText(activity, R.string.text_light_is_not_available, Toast.LENGTH_SHORT).show();
                    callback.onUpdateView(sensorKey, View.GONE);
                }

                break;
        }
    }


    @Override
    public void onMessageReceived(String message) {
        Log.i(TAG, "onMessageReceived: " + message);
        callback.onUpdateMonitor(message);
    }


    public void initializeWebsocket() {
        createWebsocketConnection();
        dataManager.openWebsocketConnection();
        dataManager.startSendingSensorValues();
    }

    public void deinitializeWebsocket() {
        dataManager.stopSendingSensorValues();
        dataManager.closeWebsocketConnection();
    }

    private SensorMessage prepareSensorMessage() {
        SensorMessage message = callback.onMakeSensorDataSnapshot();
        if (message != null) message.setSequence(flowMemory.getSequence());

        incrementSequence();

        return message;
    }


    public void initializeSequence() {
        flowMemory.setSequence(PreferencesHelper.retrieveSequence());
    }

    private void incrementSequence() {
        int currentSequence = flowMemory.getSequence();
        currentSequence++;
        flowMemory.setSequence(currentSequence);
    }

    public void cacheSequence() {
        PreferencesHelper.saveSequence(flowMemory.getSequence());
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                callback.onUpdateValue(ISensors.ISensorKeys.ACCELEROMETER, event.values[0]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                callback.onUpdateValue(ISensors.ISensorKeys.GYROSCOPE, event.values[0]);
                break;
            case Sensor.TYPE_GRAVITY:
                callback.onUpdateValue(ISensors.ISensorKeys.GRAVITY, event.values[0]);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                callback.onUpdateValue(ISensors.ISensorKeys.ROTATION, event.values[0]);
                break;
            case Sensor.TYPE_LIGHT:
                callback.onUpdateValue(ISensors.ISensorKeys.LIGHT, event.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public SensorMessage prepareMessage() {
        return prepareSensorMessage();
    }

}

