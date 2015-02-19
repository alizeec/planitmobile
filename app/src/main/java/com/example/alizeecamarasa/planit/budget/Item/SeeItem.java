package com.example.alizeecamarasa.planit.budget.Item;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.R;

/**
 * Created by alizeecamarasa on 19/02/15.
 */
public class SeeItem extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Item item = (Item)getIntent().getSerializableExtra("item");
        String type = getIntent().getStringExtra("type");
        System.out.println(type);
        if(type.equals("expense")){
            System.out.println("coucou");
            setContentView(R.layout.see_item);

            chargeExpenseView(item);
        }
    }

    public void chargeExpenseView(Item item){
        System.out.println("coucou");

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtQuantity = (TextView) findViewById(R.id.quantity);
        TextView txtStock = (TextView) findViewById(R.id.stock);
        TextView txtToBuy = (TextView) findViewById(R.id.tobuy);
        TextView txtUnit_price = (TextView) findViewById(R.id.unit_price);
        TextView txtTotal_price = (TextView) findViewById(R.id.total_price);
        TextView txtConsummate = (TextView) findViewById(R.id.consummate);
        CheckBox cbBought = (CheckBox) findViewById(R.id.bought);

        String name = item.getName();
        float quantity = item.getQuantity();
        String unit = item.getUnit();
        float stock = item.getStock();
        float tobuy = quantity - stock;
        float unit_price = item.getPrice();
        float total_price = tobuy*unit_price;
        float consummate = item.getConsummate();
        boolean bought = item.isBought();

        txtName.setText(name);
        txtQuantity.setText(String.valueOf(quantity)+" "+unit);
        txtStock.setText(String.valueOf(stock)+" "+unit);
        txtToBuy.setText(String.valueOf(tobuy)+" "+unit);
        txtUnit_price.setText(String.valueOf(unit_price)+" €");
        txtTotal_price.setText(String.valueOf(total_price)+" €");
        txtConsummate.setText(String.valueOf(consummate)+" "+unit);
        if (bought)
            cbBought.setChecked(true);
        else
            cbBought.setChecked(false);

        System.out.println("ici");
    }
}
