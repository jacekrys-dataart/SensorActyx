package myosolutions.pl.sensoractyx.workflow.secondscreen.data;

import com.google.gson.annotations.SerializedName;

import myosolutions.pl.sensoractyx.helpers.PreferencesHelper;
import myosolutions.pl.sensoractyx.interfaces.ISensors;

/**
 * Created by Jacek on 2017-03-24.
 */

public class SensorMessage {

    @SerializedName(ISensors.ISensorMessage.DEVICE)
    private String device;

    @SerializedName(ISensors.ISensorMessage.SEQUENCE)
    private int sequence;

    @SerializedName(ISensors.ISensorMessage.DATA)
    private Data data;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SensorMessage{" +
                "device='" + device + '\'' +
                ", sequence=" + sequence +
                ", data=" + data +
                '}';
    }

    public static SensorMessage makeSensorMessage(float gravity, float accelerometer, float rotation, float gyroscope, float light){
        SensorMessage message = new SensorMessage();
        message.setDevice(PreferencesHelper.retrieveDeviceIdentifier());
        message.setData(Data.makeData(gravity, accelerometer, rotation, gyroscope, light));
        return message;
    }

}
