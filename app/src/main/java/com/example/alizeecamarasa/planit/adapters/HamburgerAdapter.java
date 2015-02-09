package com.example.alizeecamarasa.planit.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;

public class HamburgerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> eventsData;

    public HamburgerAdapter(Context context,ArrayList<HashMap<String, String>> data) {
        inflater = LayoutInflater.from(context);
        eventsData = data;
    }

    @Override
    public int getCount() {
        return eventsData.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.drawer_list_item, parent, false);
        }

        HashMap<String, String> event = eventsData.get(position);

        TextView titleEvent = (TextView)view.findViewById(R.id.hamburgerTitleEvent);

        titleEvent.setText(event.get("title"));
        titleEvent.setTag(event.get("id"));

        return view;
    }
}