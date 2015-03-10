package com.example.alizeecamarasa.planit.transport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.place.Place;
import com.example.alizeecamarasa.planit.place.PlaceModuleAPI;
import com.example.alizeecamarasa.planit.place.PlaceModuleService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 10/03/15.
 */
public class TransportArrayAdapteur extends BaseAdapter {
    private static List<Transport> transportList;
    private Context mContext;
    private LayoutInflater mInflater;

    public TransportArrayAdapteur(Context context, List<Transport> results) {
        transportList = results;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return transportList.size();
    }

    public Object getItem(int position) {
        return transportList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int mPosition = position;
        final View mView = convertView;
        final ViewGroup mViewGroup = parent;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.place_row, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.price);
            holder.cbChosen = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.layout_checkbox = (LinearLayout) convertView.findViewById(R.id.layout_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set data
        holder.txtName.setText(transportList.get(position).getName());
        holder.txtName.setTextColor(mContext.getResources().getColor(R.color.transport));
        holder.txtPrice.setText(String.valueOf(transportList.get(position).getPrice()) + " €");
        holder.txtPrice.setTextColor(mContext.getResources().getColor(R.color.transport));
        holder.cbChosen.setButtonDrawable(R.drawable.chechbox_transport);

        holder.cbChosen.setOnCheckedChangeListener(null);
        if(transportList.get(position).getState() == 1){
            holder.cbChosen.setChecked(true);
            holder.layout_checkbox.setBackgroundColor(mContext.getResources().getColor(R.color.transport));
        }
        else {
            holder.cbChosen.setChecked(false);
            holder.layout_checkbox.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
        }


        holder.cbChosen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int checked;
                if (isChecked)
                    checked= 1;
                else
                    checked=0;
                final int id =transportList.get(mPosition).getId();

                TransportModuleService service = TransportModuleAPI.getInstance();
                service.choseTransport(id,checked,new Callback<Response>(){
                    @Override
                    public void success(Response response, Response response2) {
                        if(checked==1){
                            // put the place at the top if it's checked
                            transportList.add(0,transportList.remove(mPosition));
                            // change state to confirmed for the selected place
                            transportList.get(0).setState(1);
                            // and to "stand-by" for others
                            for (int i=1; i< transportList.size(); i++){
                                if (transportList.get(i).getState()==1){
                                    transportList.get(i).setState(3);
                                }
                            }
                            notifyDataSetChanged();
                        }
                        else {
                            transportList.get(mPosition).setState(transportList.get(mPosition).getOldstate());
                            notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });

            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
        CheckBox cbChosen;
        LinearLayout layout_checkbox;
    }
}
