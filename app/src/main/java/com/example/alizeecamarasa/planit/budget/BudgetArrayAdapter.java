package com.example.alizeecamarasa.planit.budget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.Item.Item;
import com.example.alizeecamarasa.planit.budget.Item.SeeItem;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;
import com.example.alizeecamarasa.planit.guest.Guest.ChangeGuest;

import java.util.List;
import java.util.Map;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class BudgetArrayAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<TypeBudget, List<Item>> itemCollections;
    private List<TypeBudget> listTypeBudget;
    private BudgetModule module;

    private ImageView delete;
    private ImageView modify;
    private ImageView send;
    private Drawable img_send_disable;

    public BudgetArrayAdapter(Activity context, List<TypeBudget> listTypeBudget,
                                 Map<TypeBudget, List<Item>> itemCollections, BudgetModule module) {
        this.context = context;
        this.itemCollections = itemCollections;
        this.listTypeBudget = listTypeBudget;
        this.module = module;

    }

    @Override
    public Item getChild(int groupPosition, int childPosition) {
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


    // renseigne les données des invités dans la view
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


        txtName.setText(name);
        if( getGroupLabel(groupPosition) == "Apports" ){
            txtPrice.setText("+"+amount+" €");
        }
        else {
            txtPrice.setText("-"+price+" €");
        }

        // voir le détail
        ImageView zoom = (ImageView) convertView.findViewById(R.id.zoom);
        zoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //adding new event : starting new Event activity
                Intent myIntent = new Intent(context, SeeItem.class);
                myIntent.putExtra("item", getChild(groupPosition, childPosition));
                if( getGroupLabel(groupPosition) == "Apports" ){
                    myIntent.putExtra("type", "inflow");
                }
                else {
                    myIntent.putExtra("type", "expense");
                }
                context.startActivity(myIntent);
            }
        });

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return itemCollections.get(listTypeBudget.get(groupPosition)).size();
    }

    //donne le nom du type d'invité
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

    //renseigne les têtes de listes
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String label = (String) getGroupLabel(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.typebudget_row_view,
                    null);
        }

        // récupère les view
        TextView txtlabel = (TextView) convertView.findViewById(R.id.label);
        // met le border en violet si c'est les apports
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
