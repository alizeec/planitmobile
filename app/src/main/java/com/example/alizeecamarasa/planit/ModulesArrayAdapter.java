package com.example.alizeecamarasa.planit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            holder.image = (ImageView) convertView.findViewById(R.id.imageModule);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(moduleList.get(position).getName());
        Context context= parent.getContext();
        Drawable img;

        // put the good image and color for each module
        switch (moduleList.get(position).getInt_type()){
            case 1:
                img = context.getResources().getDrawable( R.drawable.guest_picto);
                holder.txtName.setBackgroundColor(context.getResources().getColor(R.color.guest));
                holder.txtName.setTextColor(Color.rgb(255,255,255));
            break;
            case 2:
                img = context.getResources().getDrawable( R.drawable.budget_picto);
                holder.txtName.setBackgroundColor(context.getResources().getColor(R.color.budget));
            break;
            case 3:
                img = context.getResources().getDrawable( R.drawable.place_picto);
                holder.txtName.setBackgroundColor(context.getResources().getColor(R.color.place));
            break;
            case 4:
               img = context.getResources().getDrawable( R.drawable.transport_picto);
                holder.txtName.setBackgroundColor(context.getResources().getColor(R.color.transport));
            break;
            case 5:
                img = context.getResources().getDrawable( R.drawable.toto_picto);
                holder.txtName.setBackgroundColor(context.getResources().getColor(R.color.todo));
            break;
            default:
                img= context.getResources().getDrawable( R.drawable.add_module_picto);
                holder.txtName.setBackgroundColor(Color.rgb(226,225,225));
                holder.txtName.setTextColor(Color.rgb(97,97,97));
            break;
        }
            img.setBounds( -20, 0, 120, holder.txtName.getMeasuredHeight() );  // set the image size
            holder.image.setImageDrawable(img);

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        ImageView image;
    }
}
