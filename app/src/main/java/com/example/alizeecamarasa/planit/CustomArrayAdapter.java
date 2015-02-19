package com.example.alizeecamarasa.planit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by alizeecamarasa on 26/01/15.
 */
public class CustomArrayAdapter extends BaseAdapter {
    private static List<Event> eventList;

    private LayoutInflater mInflater;

    public CustomArrayAdapter(Context context, List<Event> results) {
        eventList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return eventList.size();
    }

    public Object getItem(int position) {
        return eventList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(eventList.get(position).getName());
        holder.txtDescription.setText(eventList.get(position).getDescription());

        holder.txtDate.setText(eventList.get(position).getTimeDiff());




        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtDescription;
        TextView txtDate;
    }
}
