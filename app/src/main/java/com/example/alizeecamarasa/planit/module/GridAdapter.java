package com.example.alizeecamarasa.planit.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;

import java.util.Map;

/**
 * Created by alizeecamarasa on 16/03/15.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private final String[] modulesValues;
    private Map<Integer,Boolean> isAlreadyUsed;
    private ImageButton image;

    public GridAdapter(Context context, String[] modulesValues, Map<Integer, Boolean> isAlreadyUsed) {
        this.context = context;
        this.modulesValues = modulesValues;
        this.isAlreadyUsed = isAlreadyUsed;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            String feature = modulesValues[position];
            Boolean isUsed = isAlreadyUsed.get(position);

            if (feature.equals("Guest")) {
                if (!isUsed)
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.guest));
                else
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.guest_disable));
            } else if (feature.equals("Budget")) {
                if (!isUsed)
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.budget));
                else
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.budget_disable));
            } else if (feature.equals("Place")) {
                if (!isUsed)
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.place));
                else
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.place_disable));
            } else if (feature.equals("Transport")) {
                if (!isUsed)
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.transport));
                else
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.transport_disable));
            } else {
                if (!isUsed)
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.todo));
                else
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.todo_disable));
            }
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }

    @Override
    public int getCount() {
        return modulesValues.length;
    }

    @Override
    public String getItem(int position) {
        return modulesValues[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}

