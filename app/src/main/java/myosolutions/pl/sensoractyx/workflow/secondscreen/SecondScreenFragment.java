package myosolutions.pl.sensoractyx.workflow.secondscreen;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;

import myosolutions.pl.sensoractyx.R;
import myosolutions.pl.sensoractyx.databinding.FragmentSecondScreenBinding;
import myosolutions.pl.sensoractyx.helpers.PreferencesHelper;
import myosolutions.pl.sensoractyx.interfaces.IDataHolder;
import myosolutions.pl.sensoractyx.interfaces.ISensors;
import myosolutions.pl.sensoractyx.utils.FloatUtils;
import myosolutions.pl.sensoractyx.workflow.DataManager;
import myosolutions.pl.sensoractyx.workflow.FlowMemory;
import myosolutions.pl.sensoractyx.workflow.secondscreen.data.SensorMessage;
import myosolutions.pl.sensoractyx.workflow.secondscreen.views.MonitorAdapter;

public class SecondScreenFragment extends Fragment implements ISecondScreenCallback{

    private static final String TAG = SecondScreenFragment.class.getSimpleName();
    private FragmentSecondScreenBinding binding;

    private SecondScreenPresenter presenter;

    private MonitorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_screen, container, false);

        initPresenter();
        initViews();

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.initializeSequence();
        presenter.generateSensorViews();
        presenter.initializeWebsocket();

    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.deinitializeWebsocket();
        presenter.unregisterSenrorListeners();
        presenter.cacheSequence();
    }

    @Override
    public void onUpdateMonitor(String message) {
        adapter.updateData(message);
        binding.rvMonitor.smoothScrollToPosition(0);
    }


    private void initViews() {
        binding.tvDeviceIdentifier.setText(String.format(getString(R.string.text_device_identifier),PreferencesHelper.retrieveDeviceIdentifier()));
        binding.rvMonitor.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvMonitor.setAdapter(createAdapter());
    }

    private MonitorAdapter createAdapter(){
        adapter = new MonitorAdapter(new ArrayList<String>());
        return adapter;
    }

    private void initPresenter() {
        Activity ac = getActivity();
        if (ac != null) {
            FlowMemory flow = ((IDataHolder) ac).getFlowMemory();
            DataManager manager = ((IDataHolder) ac).getDataManager();
            presenter = new SecondScreenPresenter(this, flow, manager, ac);
        }
    }


    @Override
    public void onUpdateView(int sensorKey, int visibility) {
        updateView(sensorKey, visibility);
    }

    @Override
    public void onUpdateValue(int sensorKey, float value) {
        switch (sensorKey) {
            case ISensors.ISensorKeys.ACCELEROMETER:
                binding.tvPreviewSensorAccelerometr.setText(String.format(getString(R.string.text_accelerometer_sensor_value), value));
                break;
            case ISensors.ISensorKeys.GYROSCOPE:
                binding.tvPreviewSensorGyroscope.setText(String.format(getString(R.string.text_gyroscope_sensor_value), value));
                break;
            case ISensors.ISensorKeys.GRAVITY:
                binding.tvPreviewSensorGravity.setText(String.format(getString(R.string.text_gravity_sensor_value), value));
                break;
            case ISensors.ISensorKeys.ROTATION:
                binding.tvPreviewSensorRotation.setText(String.format(getString(R.string.text_rotation_sensor_value), value));
                break;
            case ISensors.ISensorKeys.LIGHT:
                binding.tvPreviewSensorLight.setText(String.format(getString(R.string.text_light_sensor_value), value));
                break;
        }
    }

    @Override
    public SensorMessage onMakeSensorDataSnapshot() {

        SensorMessage sensorMessage = null;

        Activity ac = getActivity();
        if(ac!=null){
            sensorMessage = SensorMessage.makeSensorMessage(FloatUtils.getFloatFromString(binding.tvPreviewSensorGravity.getText().toString()),
                                FloatUtils.getFloatFromString(binding.tvPreviewSensorAccelerometr.getText().toString()),
                                FloatUtils.getFloatFromString(binding.tvPreviewSensorRotation.getText().toString()),
                                FloatUtils.getFloatFromString(binding.tvPreviewSensorGyroscope.getText().toString()),
                                FloatUtils.getFloatFromString(binding.tvPreviewSensorLight.getText().toString()));
        }
        return sensorMessage;

    }


    private void updateView(int sensorKey, int visibility) {
        switch (sensorKey) {
            case ISensors.ISensorKeys.ACCELEROMETER:
                binding.tvPreviewSensorAccelerometr.setVisibility(visibility);
                break;
            case ISensors.ISensorKeys.GYROSCOPE:
                binding.tvPreviewSensorGyroscope.setVisibility(visibility);
                break;
            case ISensors.ISensorKeys.GRAVITY:
                binding.tvPreviewSensorGravity.setVisibility(visibility);
                break;
            case ISensors.ISensorKeys.ROTATION:
                binding.tvPreviewSensorRotation.setVisibility(visibility);
                break;
            case ISensors.ISensorKeys.LIGHT:
                binding.tvPreviewSensorLight.setVisibility(visibility);
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Activity ac = getActivity();
        if(ac!=null){
            outState.putParcelable(FlowMemory.TAG, Parcels.wrap(((IDataHolder) ac).getFlowMemory()));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Activity ac = getActivity();
        if(ac!=null && savedInstanceState!=null){
            FlowMemory flow = Parcels.unwrap(savedInstanceState.getParcelable(FlowMemory.TAG));
            ((IDataHolder) ac).setFlowMemory(flow);

        }
        super.onActivityCreated(savedInstanceState);
    }




}
