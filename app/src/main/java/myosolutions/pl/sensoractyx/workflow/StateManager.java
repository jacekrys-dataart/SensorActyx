package myosolutions.pl.sensoractyx.workflow;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import myosolutions.pl.sensoractyx.common.BaseStateManager;
import myosolutions.pl.sensoractyx.workflow.firstscreen.FirstScreenFragment;
import myosolutions.pl.sensoractyx.workflow.secondscreen.SecondScreenFragment;

/**
 * Created by Jacek on 2017-03-22.
 */
public class StateManager extends BaseStateManager {


    public static final String TAG = StateManager.class.getSimpleName();

    private FirstScreenState mFirstScreenState;
    private SecondScreenState mSecondScreenState;

    private IntermediateState mIntermediateState;


    private static StateManager mInstance;

    @Override
    protected BaseState getInitialState() {
        return mFirstScreenState;
    }

    protected StateManager(FragmentManager fragmentManager, Activity activity) {
        super(fragmentManager, activity);

        if(mFirstScreenState == null){
            mFirstScreenState = new FirstScreenState();
            mSecondScreenState = new SecondScreenState();
            mIntermediateState = new IntermediateState();
        }
    }


    public static BaseState initState(FragmentManager fragmentManager, Activity activity) {

        mInstance = new StateManager(fragmentManager, activity);

        BaseState initialState = mInstance.getInitialState();
        initialState.enter();
        return initialState;
    }


    public class FirstScreenState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new FirstScreenFragment());

        }

        @Override
        public void exit(int event) {
        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_NEXT:
                    return mSecondScreenState;
                case EVENT_PREVIOUS:
                    return mFinishProcessState;
                case EVENT_FINISH:
                    return mFinishProcessState;
                default:
                    return this;
            }
        }
    }

    public class SecondScreenState extends BaseState {

        @Override
        public void enter() {
            addNewFragment(new SecondScreenFragment());
        }

        @Override
        public void exit(int event) {
        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                case EVENT_PREVIOUS:
                    return mIntermediateState;
                default:
                    return this;
            }
        }
    }


    public class IntermediateState extends BaseState {

        @Override
        public void enter() {
            mActivity.recreate();
        }

        @Override
        public void exit(int event) {
        }

        @Override
        public BaseState nextState(int event) {
            switch (event) {
                default:
                    return this;
            }
        }
    }


}
