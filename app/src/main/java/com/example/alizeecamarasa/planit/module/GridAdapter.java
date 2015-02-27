package com.example.alizeecamarasa.planit.module;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

/**
 * Created by alizeecamarasa on 22/02/15.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private Map<Integer,Boolean> list;
    private List<Integer> mThumbIds;

    public GridAdapter(Context c,Map<Integer,Boolean> isEnabled,List<Integer> images) {
        mContext = c;
        list = isEnabled;
        mThumbIds = images;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean isEnabled(int position){
        if (list.get(position)== true){
            return false;
        }
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds.get(position));
        return imageView;
    }

}
