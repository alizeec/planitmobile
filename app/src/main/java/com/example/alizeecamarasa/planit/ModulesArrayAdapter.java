package com.example.alizeecamarasa.planit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.module.Module;

import java.util.List;

/**
 * Created by alizeecamarasa on 26/01/15.
 */
public class ModulesArrayAdapter extends BaseAdapter {
    private static List<Module> moduleList;

    private LayoutInflater mInflater;

    public ModulesArrayAdapter(Context context, List<Module> results) {
        moduleList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return moduleList.size();
    }

    public Object getItem(int position) {
        return moduleList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.modules_row_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(moduleList.get(position).getName());
        Context context= parent.getContext();
        Drawable img;
        switch (moduleList.get(position).getInt_type()){
            case 1:
                img = context.getResources().getDrawable( R.drawable.guest_picto);
                holder.txtName.setBackgroundColor(Color.rgb(29,137,207));
            break;
            case 2:
                img = context.getResources().getDrawable( R.drawable.budget_picto);
                holder.txtName.setBackgroundColor(Color.rgb(249,222,47));
            break;
            case 3:
                img = context.getResources().getDrawable( R.drawable.place_picto);
                holder.txtName.setBackgroundColor(Color.rgb(133,113,152));
            break;
            case 4:
               img = context.getResources().getDrawable( R.drawable.transport_picto);
                holder.txtName.setBackgroundColor(Color.rgb(255,159,55));
            break;
            case 5:
                img = context.getResources().getDrawable( R.drawable.toto_picto);
                holder.txtName.setBackgroundColor(Color.rgb(220,72,136));
            break;
            default:
                img = context.getResources().getDrawable( R.drawable.toto_picto);
            break;

        }
        img.setBounds( -20, 0, 120, holder.txtName.getMeasuredHeight() );  // set the image size
        holder.txtName.setCompoundDrawables( img, null, null, null );

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
    }
}
