package com.example.alizeecamarasa.planit.budget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.Item.ChangeItem;
import com.example.alizeecamarasa.planit.budget.Item.ItemBudget;
import com.example.alizeecamarasa.planit.budget.Item.SeeItem;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class BudgetArrayAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<TypeBudget, List<ItemBudget>> itemCollections;
    private List<TypeBudget> listTypeBudget;
    private BudgetModule module;

    private ImageView delete;
    private ImageView modify;
    private ImageView send;
    private Drawable img_send_disable;

    public BudgetArrayAdapter(Activity context, List<TypeBudget> listTypeBudget,
                                 Map<TypeBudget, List<ItemBudget>> itemCollections, BudgetModule module) {
        this.context = context;
        this.itemCollections = itemCollections;
        this.listTypeBudget = listTypeBudget;
        this.module = module;

    }

    @Override
    public ItemBudget getChild(int groupPosition, int childPosition) {
        return itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition);
    }

    // retourne le nom de l'article
    public String getChildName(int groupPosition, int childPosition) {
        return itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition).getName();
    }

    // retourne la quantité totale
    public float getChildQuantity(int groupPosition, int childPosition) {
        return itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition).getQuantity();
    }

    // retourne le prix total
    public float getChildPrice(int groupPosition, int childPosition) {
        float price = getChildQuantity(groupPosition,childPosition)-itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition).getStock();
        price *= itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition).getPrice();
        return price ;
    }

    public String getChildUnit(int groupPosition, int childPosition){
        return itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition).getUnit();
    }

    public float getChildAmount(int groupPosition, int childPosition){
        return itemCollections.get(listTypeBudget.get(groupPosition)).get(childPosition).getAmount();
    }


    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    // put data in the list
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String name = (String) getChildName(groupPosition, childPosition);
        final String amount = String.valueOf(getChildAmount(groupPosition, childPosition));
        final String price = String.valueOf(getChildPrice(groupPosition, childPosition));

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_row_view, null);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.totalPrice);
        ImageView zoom = (ImageView) convertView.findViewById(R.id.zoom);

        txtName.setText(name);
        // if it's an inflow, zoom and modify are on the same activity, so we don't show the icon
        if( getGroupLabel(groupPosition) == "Apports" ){
            txtPrice.setText("+" + amount + " €");
            zoom.setVisibility(View.INVISIBLE);
        }
        else {
            txtPrice.setText("-" + price + " €");
            zoom.setVisibility(View.VISIBLE);
        }

        // SEE DETAILS
        zoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(context, SeeItem.class);
                myIntent.putExtra("item", getChild(groupPosition, childPosition));
                context.startActivity(myIntent);
            }
        });

        // MODIFY ITEM
        ImageView modify = (ImageView) convertView.findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ChangeItem.class);
                myIntent.putExtra("item", getChild(groupPosition, childPosition));
                if( getGroupLabel(groupPosition) == "Apports" ){
                    myIntent.putExtra("type", "inflow");
                }
                else {
                    myIntent.putExtra("type", "expense");
                }
                context.startActivityForResult(myIntent, 3);
            }
        });

        // DELETE ITEM
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voulez vous supprimer cet article?");
                builder.setCancelable(false);
                builder.setPositiveButton("OUI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<ItemBudget> child =
                                        itemCollections.get(listTypeBudget.get(groupPosition));

                                BudgetModuleService service = BudgetModuleAPI.getInstance();
                                if( getGroupLabel(groupPosition) == "Apports" ) {
                                    service.deleteInflow(getChild(groupPosition, childPosition).getId(), new Callback<Response>() {
                                        @Override
                                        public void success(Response o, Response response) {
                                            System.out.println("success");
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            System.out.println("erreur");
                                            error.printStackTrace();
                                        }
                                    });
                                }
                                else {
                                    service.deleteExpense(getChild(groupPosition, childPosition).getId(), new Callback<Response>() {
                                        @Override
                                        public void success(Response o, Response response) {
                                            System.out.println("success");
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            System.out.println("erreur");
                                            error.printStackTrace();
                                        }
                                    });
                                }
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

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return itemCollections.get(listTypeBudget.get(groupPosition)).size();
    }

    public Object getGroupLabel(int groupPosition) {
        return listTypeBudget.get(groupPosition).getName();
    }

    public int getGroupCount() {
        return listTypeBudget.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public TypeBudget getGroup(int groupPosition) { return listTypeBudget.get(groupPosition); }

    //put data in lists headers
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String label = (String) getGroupLabel(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.typebudget_row_view,
                    null);
        }

        TextView txtlabel = (TextView) convertView.findViewById(R.id.label);
        // if it's inflow, put the border in purple
        if(label == "Apports"){
            txtlabel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.border_purple, 0, 0, 0);
            txtlabel.setTextColor(context.getResources().getColor(R.color.budget));
        }
        txtlabel.setText(label);

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
