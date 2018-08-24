package myosolutions.pl.sensoractyx.interfaces;

import myosolutions.pl.sensoractyx.workflow.DataManager;
import myosolutions.pl.sensoractyx.workflow.FlowMemory;

/**
 * Created by Jacek on 2017-03-22.
 */

public interface IDataHolder {

    FlowMemory getFlowMemory();
    void setFlowMemory(FlowMemory flowMemory);

    DataManager getDataManager();

}
