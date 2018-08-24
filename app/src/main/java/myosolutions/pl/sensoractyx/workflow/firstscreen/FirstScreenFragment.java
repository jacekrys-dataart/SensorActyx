package myosolutions.pl.sensoractyx.workflow.firstscreen;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.parceler.Parcels;

import myosolutions.pl.sensoractyx.R;
import myosolutions.pl.sensoractyx.common.BaseStateManager;
import myosolutions.pl.sensoractyx.databinding.FragmentFirstScreenBinding;
import myosolutions.pl.sensoractyx.interfaces.IDataHolder;
import myosolutions.pl.sensoractyx.interfaces.ISensors;
import myosolutions.pl.sensoractyx.workflow.FlowMemory;

public class FirstScreenFragment extends Fragment implements View.OnClickListener, IFirstScreenCallback, CompoundButton.OnCheckedChangeListener{

    private FragmentFirstScreenBinding binding;

    private FirstScreenPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_screen, container, false);

        initPresenter();

        presenter.generateDeviceIdentifier();

        initViews();

        View root = binding.getRoot();
        return root;
    }


    private void initViews(){
        binding.setClickListener(this);
        binding.accelerometrLayout.tbAccelerometr.setOnCheckedChangeListener(this);
        binding.gyroscopeLayout.tbGyroscope.setOnCheckedChangeListener(this);
        binding.gravityLayout.tbGravity.setOnCheckedChangeListener(this);
        binding.rotationLayout.tbRotation.setOnCheckedChangeListener(this);
        binding.lightLayout.tbLight.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setCachedSensorMap();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_next:
                if(presenter.validateNextStep()){
                    presenter.saveSensorsConfiguration();
                    ((BaseStateManager.IStateChangeListener) getActivity()).processState(BaseStateManager.EVENT_NEXT);
                }else{
                    onNoSensorsSelected(R.string.text_choose_one_sensor);
                }
                break;
        }
    }


    private void initPresenter(){
        Activity ac = getActivity();
        if(ac!=null){
            FlowMemory flow = ((IDataHolder) ac).getFlowMemory();
            presenter = new FirstScreenPresenter(this, flow);
        }

    }


    @Override
    public void onDeviceIdGenerated(String identifier) {
        binding.tvDeviceIdentifier.setText(String.format(getString(R.string.text_device_identifier), identifier));
    }

    @Override
    public void onNoSensorsSelected(int messageId) {
        Activity ac = getActivity();
        if(ac != null) {
            Toast.makeText(ac, messageId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValueCached(int sensorKey, boolean value) {
        switch (sensorKey){
            case ISensors.ISensorKeys.ACCELEROMETER:
                binding.accelerometrLayout.tbAccelerometr.setChecked(value);
                break;
            case ISensors.ISensorKeys.GYROSCOPE:
                binding.gyroscopeLayout.tbGyroscope.setChecked(value);
                break;
            case ISensors.ISensorKeys.GRAVITY:
                binding.gravityLayout.tbGravity.setChecked(value);
                break;
            case ISensors.ISensorKeys.ROTATION:
                binding.rotationLayout.tbRotation.setChecked(value);
                break;
            case ISensors.ISensorKeys.LIGHT:
                binding.lightLayout.tbLight.setChecked(value);
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();

        switch (id){
            case R.id.tb_accelerometr:
                    presenter.updateSensorPickup(ISensors.ISensorKeys.ACCELEROMETER, isChecked);
                break;

            case R.id.tb_gyroscope:
                    presenter.updateSensorPickup(ISensors.ISensorKeys.GYROSCOPE, isChecked);
                break;
            case R.id.tb_gravity:
                    presenter.updateSensorPickup(ISensors.ISensorKeys.GRAVITY, isChecked);
                break;
            case R.id.tb_rotation:
                    presenter.updateSensorPickup(ISensors.ISensorKeys.ROTATION, isChecked);
                break;
            case R.id.tb_light:
                    presenter.updateSensorPickup(ISensors.ISensorKeys.LIGHT, isChecked);
                break;
        }

        presenter.saveSensorsConfiguration();
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
            presenter = new FirstScreenPresenter(this, flow);
        }
        super.onActivityCreated(savedInstanceState);
    }



}
