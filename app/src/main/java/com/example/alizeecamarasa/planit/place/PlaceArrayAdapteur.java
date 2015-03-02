package com.example.alizeecamarasa.planit.place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.events.Event;

import java.util.List;

/**
 * Created by alizeecamarasa on 01/03/15.
 */
public class PlaceArrayAdapteur extends BaseAdapter {
    private static List<Place> placeList;

    private LayoutInflater mInflater;

    public PlaceArrayAdapteur(Context context, List<Place> results) {
        placeList = results;
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.place_row, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set data
        holder.txtName.setText(placeList.get(position).getName());
        holder.txtPrice.setText(String.valueOf(placeList.get(position).getPrice())+" €");

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
    }
}
