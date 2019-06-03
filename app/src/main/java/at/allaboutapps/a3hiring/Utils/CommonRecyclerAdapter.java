package at.allaboutapps.a3hiring.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Muthukumar Neelamegam on 4/13/2019.
 * android-hiring
 */
public class CommonRecyclerAdapter<Data, SampleViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<SampleViewHolder> {

    private List<Data> values;
    private SampleViewHolder viewHolder;
    private int layoutId;
    private AdapterView adapterView;

    public CommonRecyclerAdapter(List<Data> values, int layoutId) {
        this.values = values;
        this.layoutId = layoutId;
    }

    public CommonRecyclerAdapter setRowItemView(AdapterView adapterView) {
        this.adapterView = adapterView;
        return this;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Object object = adapterView.setAdapterView(parent, viewType, this.layoutId);
        this.viewHolder = (SampleViewHolder) object;
        return this.viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        Data data = values.get(position);
        adapterView.onBindView(holder, position, data, (List<Object>) this.values);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public interface AdapterView {

        Object setAdapterView(ViewGroup parent, int viewType, int layoutId);

        void onBindView(Object holder, int position, Object data, List<Object> dataList);

    }



    public void delete(int position){
        values.remove(position);
        notifyItemRemoved(position);
    }

    public int getSize(){
        return values.size();
    }


}//END
