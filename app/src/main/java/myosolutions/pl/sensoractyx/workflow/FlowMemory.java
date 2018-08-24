package myosolutions.pl.sensoractyx.workflow;

import org.parceler.Parcel;

import java.util.HashMap;


@Parcel(Parcel.Serialization.BEAN)
public class FlowMemory {

    public static final String TAG = FlowMemory.class.getSimpleName() + "_key";

    private HashMap<Integer, Boolean> sensorList = new HashMap<>();

    private int sequence;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public HashMap<Integer, Boolean> getSensorList() {
        return sensorList;
    }

    public void setSensorList(HashMap<Integer, Boolean> sensorList) {
        this.sensorList = sensorList;
    }

    @Override
    public String toString() {
        return "FlowMemory{" +
                "sensorList=" + sensorList.toString() +
                ", sequence=" + sequence +
                '}';
    }
}
