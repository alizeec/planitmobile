package com.example.alizeecamarasa.planit.guest;

import java.util.List;
import java.util.Map;

import com.example.alizeecamarasa.planit.CustomArrayAdapter;
import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.guest.Guest.GuestAPI;
import com.example.alizeecamarasa.planit.guest.Guest.GuestService;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TypeGuestArrayAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<TypeGuest, List<Guest>> laptopCollections;
    private List<TypeGuest> laptops;

    public TypeGuestArrayAdapter(Activity context, List<TypeGuest> laptops,
                                 Map<TypeGuest, List<Guest>> laptopCollections) {
        this.context = context;
        this.laptopCollections = laptopCollections;
        this.laptops = laptops;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition).getId();
    }

    public Object getChildFirstname(int groupPosition, int childPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition).getFirstname();
    }
    public Object getChildLastname(int groupPosition, int childPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition).getLastname();
    }
    public Object getChildEmail(int groupPosition, int childPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition).getEmail();
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String firstname = (String) getChildFirstname(groupPosition, childPosition);
        final String lastname = (String) getChildLastname(groupPosition, childPosition);
        final String email = (String) getChildEmail(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.guest_row_view, null);
        }

        TextView txtFirstname = (TextView) convertView.findViewById(R.id.firstname);
        TextView txtLastname = (TextView) convertView.findViewById(R.id.lastname);
        TextView txtEmail = (TextView) convertView.findViewById(R.id.email);

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Guest> child =
                                        laptopCollections.get(laptops.get(groupPosition));
                                child.remove(childPosition);
                                // supression en BDD de l'invité
                                GuestService service = GuestAPI.getInstance();
                                service.deleteGuest((String) getChild(groupPosition, childPosition),new  Callback<Guest>(){
                                    @Override
                                    public void success(Guest o, Response response) {
                                        System.out.println("success");
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        System.out.println("erreur");
                                        error.printStackTrace();
                                    }
                                });
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        txtFirstname.setText(firstname);
        txtLastname.setText(lastname);
        txtEmail.setText(email);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return laptops.get(groupPosition).getLabel();
    }

    public Object getGroupPrice(int groupPosition) {
        return laptops.get(groupPosition).getPrice();
    }



    public int getGroupCount() {
        return laptops.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        String price = String.valueOf(getGroupPrice(groupPosition));
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.typeguest_row_view,
                    null);
        }
        TextView label = (TextView) convertView.findViewById(R.id.laptop);
        TextView txtprice = (TextView) convertView.findViewById(R.id.price);
        label.setText(laptopName);
        txtprice.setText(price+" €/pers");
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}