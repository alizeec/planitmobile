package com.example.alizeecamarasa.planit.budget.Item;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
       // String type = getIntent().getStringExtra("type");
        //if(type.equals("expense")){
            setContentView(R.layout.see_expense);
            chargeExpenseView(item);
        /*}
        else if (type.equals("inflow")){
            setContentView(R.layout.see_inflow);
            chargeInflowView(item);
        }*/
    }

    public void chargeExpenseView(Item item){

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtQuantity = (TextView) findViewById(R.id.quantity);
        TextView txtStock = (TextView) findViewById(R.id.stock);
        TextView txtToBuy = (TextView) findViewById(R.id.tobuy);
        TextView txtUnit_price = (TextView) findViewById(R.id.unit_price);
        TextView txtTotal_price = (TextView) findViewById(R.id.total_price);
        TextView txtConsummate = (TextView) findViewById(R.id.consummate);
        RadioButton cbBought = (RadioButton) findViewById(R.id.bought);
        Button back = (Button) findViewById(R.id.back);

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


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


   /* public void chargeInflowView(Item item){

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtAmount= (TextView) findViewById(R.id.amount);
        Button back = (Button) findViewById(R.id.back);

        String name = item.getName();
        float amount = item.getAmount();


        txtName.setText(name);
        txtAmount.setText(String.valueOf(amount)+" €");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/

}
