package com.example.alizeecamarasa.planit.place;

import android.app.Activity;
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
import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.todo.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class PlaceArrayAdapteur extends BaseAdapter {
    private static List<Place> placeList;
    private Context mContext;
    private LayoutInflater mInflater;

    public PlaceArrayAdapteur(Context context, List<Place> results) {
        placeList = results;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return placeList.size();
    }

    public Object getItem(int position) {
        return placeList.get(position);
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
        holder.txtName.setText(placeList.get(position).getName());
        holder.txtPrice.setText(String.valueOf(placeList.get(position).getPrice()) + " €");

        holder.cbChosen.setOnCheckedChangeListener(null);
        if(placeList.get(position).getState() == 1){
            holder.cbChosen.setChecked(true);
            holder.layout_checkbox.setBackgroundColor(mContext.getResources().getColor(R.color.place));
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
                final int id =placeList.get(mPosition).getId();

                PlaceModuleService service = PlaceModuleAPI.getInstance();
                service.chosePlace(id,checked,new Callback<Response>(){
                    @Override
                    public void success(Response response, Response response2) {
                        if(checked==1){
                            // put the place at the top if it's checked
                            placeList.add(0,placeList.remove(mPosition));
                            // change state to confirmed for the selected place
                            placeList.get(0).setState(1);
                            // and to "stand-by" for others
                            for (int i=1; i< placeList.size(); i++){
                                if (placeList.get(i).getState()==1){
                                    placeList.get(i).setState(3);
                                }
                            }
                            notifyDataSetChanged();
                        }
                        else {
                            placeList.get(mPosition).setState(placeList.get(mPosition).getOldstate());
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
