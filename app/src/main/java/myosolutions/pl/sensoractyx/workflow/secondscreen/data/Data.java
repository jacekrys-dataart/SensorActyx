package myosolutions.pl.sensoractyx.workflow.secondscreen.data;

import com.google.gson.annotations.SerializedName;

import myosolutions.pl.sensoractyx.interfaces.ISensors;
import myosolutions.pl.sensoractyx.utils.FloatUtils;

/**
 * Created by Jacek on 2017-03-24.
 */

public class Data {

    @SerializedName(ISensors.ISensorNames.GRAVITY)
    private float gravity;

    @SerializedName(ISensors.ISensorNames.ACCELEROMETER)
    private float accelerometer;

    @SerializedName(ISensors.ISensorNames.ROTATION)
    private float rotation;

    @SerializedName(ISensors.ISensorNames.GYROSCOPE)
    private float gyroscope;

    @SerializedName(ISensors.ISensorNames.LIGHT)
    private float light;

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(float accelerometer) {
        this.accelerometer = accelerometer;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(float gyroscope) {
        this.gyroscope = gyroscope;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    @Override
    public String toString() {
        return "Data{" +
                "gravity=" + gravity +
                ", accelerometer=" + accelerometer +
                ", rotation=" + rotation +
                ", gyroscope=" + gyroscope +
                ", light=" + light +
                '}';
    }

    public static Data makeData(float gravity, float accelerometer, float rotation, float gyroscope, float light){
        Data data = new Data();
        if(Float.compare(gravity, FloatUtils.AMBIGUOUS_FLOAT) != 0){
            data.gravity = gravity;
        }

        if(Float.compare(accelerometer, FloatUtils.AMBIGUOUS_FLOAT) != 0){
            data.accelerometer = accelerometer;
        }

        if(Float.compare(gyroscope, FloatUtils.AMBIGUOUS_FLOAT) != 0){
            data.gyroscope = gyroscope;
        }

        if(Float.compare(rotation, FloatUtils.AMBIGUOUS_FLOAT) != 0){
            data.rotation = rotation;
        }

        if(Float.compare(light, FloatUtils.AMBIGUOUS_FLOAT) != 0){
            data.light = light;
        }


        return data;
    }
}
