package com.example.alizeecamarasa.planit.guest;

import java.util.List;
import java.util.Map;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.guest.Guest.GuestAPI;
import com.example.alizeecamarasa.planit.guest.Guest.GuestService;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private Map<TypeGuest, List<Guest>> guestCollections;
    private List<TypeGuest> listTypeGuest;
    private GuestModule module;

    public TypeGuestArrayAdapter(Activity context, List<TypeGuest> listTypeGuest,
                                 Map<TypeGuest, List<Guest>> guestCollections, GuestModule module) {
        this.context = context;
        this.guestCollections = guestCollections;
        this.listTypeGuest = listTypeGuest;
        this.module = module;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return guestCollections.get(listTypeGuest.get(groupPosition)).get(childPosition).getId();
    }

    // retourne le prénom d'un invité
    public Object getChildFirstname(int groupPosition, int childPosition) {
        return guestCollections.get(listTypeGuest.get(groupPosition)).get(childPosition).getFirstname();
    }

    // retourne le nom d'un invité
    public Object getChildLastname(int groupPosition, int childPosition) {
        return guestCollections.get(listTypeGuest.get(groupPosition)).get(childPosition).getLastname();
    }

    // retourne l'email d'un invité
    public Object getChildEmail(int groupPosition, int childPosition) {
        return guestCollections.get(listTypeGuest.get(groupPosition)).get(childPosition).getEmail();
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    // renseigne les données des invités dans la view
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

        // supression d'un invité
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voulez vous supprimer cet invité?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Guest> child =
                                        guestCollections.get(listTypeGuest.get(groupPosition));

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
                                child.remove(childPosition);
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
        return guestCollections.get(listTypeGuest.get(groupPosition)).size();
    }

    //donne le nom du type d'invité
    public Object getGroup(int groupPosition) {
        return listTypeGuest.get(groupPosition).getLabel();
    }

    // donne le prix
    public Long getGroupPrice(int groupPosition,GuestModule module) {
        if(module.isPayable()==true){
            return listTypeGuest.get(groupPosition).getPrice();
        }
        else {
            return Long.valueOf(0);
        }
    }


    public int getGroupCount() {
        return listTypeGuest.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //renseigne les têtes de listes
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        Long price = getGroupPrice(groupPosition, module);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.typeguest_row_view,
                    null);
        }
        // récupère les view
        TextView label = (TextView) convertView.findViewById(R.id.label);
        TextView txtprice = (TextView) convertView.findViewById(R.id.price);

        label.setText(laptopName);
        if(price != 0){
            txtprice.setText(String.valueOf(price)+" €/pers");
        }
        else {
            txtprice.setVisibility(View.GONE);
        }

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}