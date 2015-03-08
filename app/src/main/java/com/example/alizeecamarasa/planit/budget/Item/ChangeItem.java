package com.example.alizeecamarasa.planit.budget.Item;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.BudgetModuleAPI;
import com.example.alizeecamarasa.planit.budget.BudgetModuleService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 22/02/15.
 */
public class ChangeItem extends Activity {

    CheckBox cbBought ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemBudget itemBudget = (ItemBudget)getIntent().getSerializableExtra("item");
        String type = getIntent().getStringExtra("type");
        if(type.equals("expense")){
        setContentView(R.layout.change_expense);
        changeExpense(itemBudget);
        }
        else if (type.equals("inflow")){
            setContentView(R.layout.change_inflow);
            changeInflow(itemBudget);
        }
    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isEmptyTextView(TextView textview) {
        return textview.getText().toString().trim().length() == 0;
    }

    // IF THE ITEM IS AN INFLOW
    public void changeInflow(ItemBudget itemBudget){
        final ItemBudget the_itemBudget = itemBudget;

        final TextView txtName = (TextView) findViewById(R.id.labelRecap);
        final TextView txtAmount = (TextView) findViewById(R.id.amountRecap);
        final EditText EditName = (EditText) findViewById(R.id.inputLabel);
        final EditText EditAmount = (EditText) findViewById(R.id.inputAmount);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button validate = (Button) findViewById(R.id.modify);

        String name = itemBudget.getName();
        float amount = itemBudget.getAmount();

        // on mets les valeurs actuelles
        txtName.setText(name);
        EditName.setText(name);
        txtAmount.setText(String.valueOf(amount));
        EditAmount.setText(String.valueOf(amount));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyTextView(txtName)  || isEmptyEditText(EditName) || isEmptyTextView(txtAmount) || isEmptyEditText(EditAmount) ) {
                    Toast.makeText(ChangeItem.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    // modif de l'apport

                    JSONObject json = new JSONObject();
                    JSONObject inflowJson = new JSONObject();
                    try {
                        inflowJson.put("name", EditName.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        inflowJson.put("amount",Float.parseFloat(EditAmount.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("inflow_form",inflowJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());
                    BudgetModuleService service = BudgetModuleAPI.getInstance();
                    service.changeInflow(the_itemBudget.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(ChangeItem.this, "L'apport " + txtName.getText().toString() +" a été modifiée!", Toast.LENGTH_SHORT).show();
                            setResult(3);
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }

                    });

                }
            }
        });





    }

    //  IF THE ITEM IS AN EXPENSE
    public void changeExpense(ItemBudget itemBudget){
        final ItemBudget the_itemBudget = itemBudget;

        final EditText txtName = (EditText) findViewById(R.id.inputExpenseLabel);
        final EditText txtPrice = (EditText) findViewById(R.id.inputExpensePrice);
        final EditText txtStock = (EditText) findViewById(R.id.inputExpenseStock);
        final EditText txtQuantity = (EditText) findViewById(R.id.inputExpenseQuantity);
        final EditText txtConsummate = (EditText) findViewById(R.id.inputExpenseConsummate);
        cbBought = (CheckBox) findViewById(R.id.bought);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button validate = (Button) findViewById(R.id.modify);

        final String name = itemBudget.getName();
        float quantity = itemBudget.getQuantity();
        float stock = itemBudget.getStock();
        float unit_price = itemBudget.getPrice();
        float consummate = itemBudget.getConsummate();
        final int bought = itemBudget.isBought();

        // on met les valeurs actuelles
        txtName.setText(name);
        txtQuantity.setText(String.valueOf(quantity));
        txtStock.setText(String.valueOf(stock));
        txtPrice.setText(String.valueOf(unit_price));
        txtConsummate.setText(String.valueOf(consummate));
        if (bought==1)
            cbBought.setChecked(true);
        else
            cbBought.setChecked(false);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(txtName)  || isEmptyEditText(txtPrice) || isEmptyEditText(txtStock) || isEmptyEditText(txtQuantity) || isEmptyEditText(txtConsummate)) {
                    Toast.makeText(ChangeItem.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    // modif de la dépense
                    int isBought;
                    if (cbBought.isChecked())
                        isBought =1;
                    else
                        isBought = 0;

                    JSONObject json = new JSONObject();
                    JSONObject expenseJson = new JSONObject();
                    try {
                        expenseJson.put("name", txtName.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("quantity",Float.parseFloat(txtQuantity.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("stock",Float.parseFloat(txtStock.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("price",Float.parseFloat(txtPrice.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("consummate",Float.parseFloat(txtConsummate.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("bought",isBought);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        json.put("expenseupdate_form",expenseJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    BudgetModuleService service = BudgetModuleAPI.getInstance();
                    service.changeExpense(the_itemBudget.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(ChangeItem.this, "La dépense " + name +" a été modifiée!", Toast.LENGTH_SHORT).show();
                            setResult(3);
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }

                    });

                }
            }
        });
    }
}
