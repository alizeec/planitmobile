package com.example.alizeecamarasa.planit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.events.Event;

import java.util.List;

/**
 * Created by alizeecamarasa on 26/01/15.
 */
public class EventsArrayAdapter extends BaseAdapter {
    private static List<Event> eventList;

    private LayoutInflater mInflater;

    public EventsArrayAdapter(Context context, List<Event> results) {
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
            convertView = mInflater.inflate(R.layout.events_row_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set data
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
