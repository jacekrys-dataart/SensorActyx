package myosolutions.pl.sensoractyx.workflow.secondscreen.views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import myosolutions.pl.sensoractyx.BR;
import myosolutions.pl.sensoractyx.R;

/**
 * Created by Jacek on 2017-03-24.
 */

public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorItemViewHolder> {

    public static final String TAG = MonitorAdapter.class.getSimpleName();

    private List<String> mData;

    public MonitorAdapter(List<String> data) {
        this.mData = data;
    }

    public void updateData(String message){
        this.mData.add(0, message);
        notifyItemInserted(0);
    }

    public List<String> getData(){
        return mData;
    }

    public void setData(List<String> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public MonitorItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.monitor_single_item, parent, false);
        MonitorItemViewHolder holder = new MonitorItemViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MonitorItemViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.data, mData.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MonitorItemViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        MonitorItemViewHolder(View itemView){
            super (itemView);

            binding = DataBindingUtil.bind(itemView);

        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }


}
