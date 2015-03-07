package com.example.alizeecamarasa.planit.budget.Item;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        ItemBudget itemBudget = (ItemBudget)getIntent().getSerializableExtra("item");

        setContentView(R.layout.see_expense);
        chargeExpenseView(itemBudget);

    }

    public void chargeExpenseView(ItemBudget itemBudget){

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtQuantity = (TextView) findViewById(R.id.quantity);
        TextView txtStock = (TextView) findViewById(R.id.stock);
        TextView txtToBuy = (TextView) findViewById(R.id.tobuy);
        TextView txtUnit_price = (TextView) findViewById(R.id.unit_price);
        TextView txtTotal_price = (TextView) findViewById(R.id.total_price);
        TextView txtConsummate = (TextView) findViewById(R.id.consummate);
        RadioButton cbBought = (RadioButton) findViewById(R.id.bought);
        Button back = (Button) findViewById(R.id.back);

        String name = itemBudget.getName();
        float quantity = itemBudget.getQuantity();
        String unit = itemBudget.getUnit();
        float stock = itemBudget.getStock();
        float tobuy = quantity - stock;
        float unit_price = itemBudget.getPrice();
        float total_price = tobuy*unit_price;
        float consummate = itemBudget.getConsummate();
        int bought = itemBudget.isBought();

        txtName.setText(name);
        txtQuantity.setText(String.valueOf(quantity)+" "+unit);
        txtStock.setText(String.valueOf(stock)+" "+unit);
        txtToBuy.setText(String.valueOf(tobuy)+" "+unit);
        txtUnit_price.setText(String.valueOf(unit_price)+" €");
        txtTotal_price.setText(String.valueOf(total_price)+" €");
        txtConsummate.setText(String.valueOf(consummate)+" "+unit);
        if (bought ==1)
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
}
