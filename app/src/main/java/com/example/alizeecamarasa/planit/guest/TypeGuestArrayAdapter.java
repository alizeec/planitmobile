package com.example.alizeecamarasa.planit.guest;

import java.util.List;
import java.util.Map;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.guest.Guest.ChangeGuest;
import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.guest.Guest.GuestAPI;
import com.example.alizeecamarasa.planit.guest.Guest.GuestService;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TypeGuestArrayAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<TypeGuest, List<Guest>> guestCollections;
    private List<TypeGuest> listTypeGuest;
    private GuestModule module;

    private ImageView delete;
    private ImageView modify;
    private ImageView send;
    private Drawable img_send_disable;

    public TypeGuestArrayAdapter(Activity context, List<TypeGuest> listTypeGuest,
                                 Map<TypeGuest, List<Guest>> guestCollections, GuestModule module) {
        this.context = context;
        this.guestCollections = guestCollections;
        this.listTypeGuest = listTypeGuest;
        this.module = module;

    }

    @Override
    public Guest getChild(int groupPosition, int childPosition) {
        return guestCollections.get(listTypeGuest.get(groupPosition)).get(childPosition);
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
        delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voulez vous supprimer cet invité?");
                builder.setCancelable(false);
                builder.setPositiveButton("OUI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Guest> child =
                                        guestCollections.get(listTypeGuest.get(groupPosition));

                                // supression en BDD de l'invité
                                GuestService service = GuestAPI.getInstance();
                                service.deleteGuest((String) getChild(groupPosition, childPosition).getId(),new  Callback<Guest>(){
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
                builder.setNegativeButton("NON",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });




        // modification d'un invité
        modify = (ImageView) convertView.findViewById(R.id.modify);
        modify.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //adding new event : starting new Event activity
                Intent myIntent = new Intent(context, ChangeGuest.class);
                myIntent.putExtra("guest",getChild(groupPosition,childPosition));
                myIntent.putExtra("type_guest",(String) getGroup(groupPosition).getId());
                context.startActivity(myIntent);
            };


        });


        // envoi du mail
        send = (ImageView) convertView.findViewById(R.id.send);
        System.out.println(module.isModuletype());
        if(module.isModuletype()== true){
            send.setVisibility(View.GONE);
        }
        img_send_disable = context.getResources().getDrawable( R.drawable.send_disable);
        if (getChild(groupPosition,childPosition).getSent()==1) {
            send.setImageDrawable(img_send_disable);
        }
        send.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if(getChild(groupPosition,childPosition).getSent()==0)
                    builder.setMessage(R.string.question_mail1);
                else
                    builder.setMessage(R.string.question_mail2);
                builder.setCancelable(false);
                builder.setPositiveButton("OUI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Guest> child =
                                        guestCollections.get(listTypeGuest.get(groupPosition));

                                // envoi du mail
                                GuestService service = GuestAPI.getInstance();
                                service.sendInvitGuest((String) getChild(groupPosition, childPosition).getId(), new Callback<JSONObject>() {
                                    @Override
                                    public void success(JSONObject o, Response response) {
                                        Toast.makeText(context, R.string.mail_sent, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        error.printStackTrace();
                                    }
                                });

                            }
                        });
                builder.setNegativeButton("NON",
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
    public Object getGroupLabel(int groupPosition) {
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

    public TypeGuest getGroup(int groupPosition) { return listTypeGuest.get(groupPosition); }

    //renseigne les têtes de listes
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroupLabel(groupPosition);
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
        if(module.isPayable() == true){
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