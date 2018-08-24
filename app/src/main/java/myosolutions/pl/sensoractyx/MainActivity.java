package myosolutions.pl.sensoractyx;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.parceler.Parcels;

import myosolutions.pl.sensoractyx.common.BaseStateManager;
import myosolutions.pl.sensoractyx.interfaces.IDataHolder;
import myosolutions.pl.sensoractyx.workflow.DataManager;
import myosolutions.pl.sensoractyx.workflow.FlowMemory;
import myosolutions.pl.sensoractyx.workflow.StateManager;

public class MainActivity extends AppCompatActivity implements BaseStateManager.IStateChangeListener, IDataHolder {

    private BaseStateManager.BaseState mState;

    private FlowMemory flowMemory;
    private DataManager dataManager;

    private static final int SECOND_SCREEN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

        flowMemory = new FlowMemory();
        dataManager = new DataManager();

        mState = StateManager.initState(getSupportFragmentManager(), this);

    }

    @Override
    public BaseStateManager.BaseState processState(int event) {
        mState = mState.processState(event);
        return mState;
    }

    @Override
    public void onBackPressed() {
        processState(BaseStateManager.EVENT_PREVIOUS);
    }

    @Override
    public FlowMemory getFlowMemory() {
        return flowMemory;
    }

    @Override
    public void setFlowMemory(FlowMemory flow) {
        flowMemory = flow;
    }

    @Override
    public DataManager getDataManager() {
        return dataManager;
    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(FlowMemory.TAG, Parcels.wrap(flowMemory));
        if(mState instanceof StateManager.SecondScreenState){
            outState.putInt(StateManager.TAG, SECOND_SCREEN);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null) {
            flowMemory = Parcels.unwrap(savedInstanceState.getParcelable(FlowMemory.TAG));
            int previousScreen = savedInstanceState.getInt(StateManager.TAG);
            if(previousScreen == SECOND_SCREEN){
                processState(BaseStateManager.EVENT_NEXT);
            }
        }
    }


}
