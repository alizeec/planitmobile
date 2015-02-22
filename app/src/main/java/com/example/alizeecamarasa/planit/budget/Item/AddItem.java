package com.example.alizeecamarasa.planit.budget.Item;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.BudgetActivity;
import com.example.alizeecamarasa.planit.budget.BudgetModule;
import com.example.alizeecamarasa.planit.budget.BudgetModuleAPI;
import com.example.alizeecamarasa.planit.budget.BudgetModuleService;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;
import com.example.alizeecamarasa.planit.guest.Guest.GuestAPI;
import com.example.alizeecamarasa.planit.guest.Guest.GuestService;
import com.example.alizeecamarasa.planit.guest.GuestModule;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 21/02/15.
 */
public class AddItem extends Activity {

    private EditText name;
    private EditText amount;
    private EditText unit;
    private EditText price;
    private EditText stock;
    private EditText quantity;
    private Spinner spinnerTypeExpense;
    private Button validate;

    private BudgetModule mModule;
    private String module_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get module in order to have typeguest list
        String type = getIntent().getStringExtra("type");


        if (type.equals("inflow")){
            setContentView(R.layout.add_inflow);
            module_id = getIntent().getStringExtra("id");
            name = (EditText) findViewById(R.id.inputInflowLabel);
            amount = (EditText) findViewById(R.id.inputInflowAmount);
            validate = (Button) findViewById(R.id.validatenewitem);

            addInflow();
        }

        else if (type.equals("expense")){
            setContentView(R.layout.add_expense);
            mModule = (BudgetModule)getIntent().getExtras().getSerializable("module");
            name = (EditText) findViewById(R.id.inputExpenseLabel);
            unit = (EditText) findViewById(R.id.inputExpenseUnit);
            quantity = (EditText) findViewById(R.id.inputExpenseQuantity);
            price = (EditText) findViewById(R.id.inputExpensePrice);
            stock = (EditText) findViewById(R.id.inputExpenseStock);
            validate = (Button) findViewById(R.id.validatenewitem);

            // set data in the spinner
            addItemsOnSpinner();

            addExpense();
        }



    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinnerTypeExpense = (Spinner) findViewById(R.id.spinner);

        List<TypeBudget> list = mModule.getTypeBudgetList();


        ArrayAdapter<TypeBudget> dataAdapter = new ArrayAdapter<TypeBudget>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeExpense.setAdapter(dataAdapter);
    }


    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }




    //creation of the item :
    private void addInflow() {

        // clic sur le bouton valider
        validate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(name) || isEmptyEditText(amount) ) {
                    Toast.makeText(AddItem.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create parseObject and enter it in database
                else {

                    // ajout de l'événement

                    JSONObject json = new JSONObject();
                    JSONObject inflowJson = new JSONObject();
                    try {
                        inflowJson.put("name",name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        inflowJson.put("amount",Float.parseFloat(amount.getText().toString()));
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
                    service.addInflow(module_id, in, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject event, Response response) {
                            Toast.makeText(AddItem.this, "L'apport " + name.getText().toString() + " a été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }

                    });
                    setResult(1);
                    finish();
                }
            }
        });
    }


    //creation of the item :
    private void addExpense() {

        // clic sur le bouton valider
        validate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                TypeBudget type = (TypeBudget) spinnerTypeExpense.getSelectedItem();

                //if one of the field is empty, do nothing
                if (isEmptyEditText(name) || isEmptyEditText(unit) || isEmptyEditText(price) || isEmptyEditText(stock) || isEmptyEditText(quantity)) {
                    Toast.makeText(AddItem.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                //else create parseObject and enter it in database
                else {

                    // ajout de l'événement

                    JSONObject json = new JSONObject();
                    JSONObject expenseJson = new JSONObject();
                    try {
                        expenseJson.put("name",name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("quantity",Float.parseFloat(quantity.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("unit",unit.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("stock",Float.parseFloat(stock.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        expenseJson.put("price",Float.parseFloat(price.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("expense_form",expenseJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());



                    BudgetModuleService service = BudgetModuleAPI.getInstance();
                    service.addExpense(type.getId(), in, new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject event, Response response) {
                            Toast.makeText(AddItem.this, "La dépense " + name.getText().toString() +" a été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }

                    });
                    setResult(2);
                    finish();
                }
            }
        });
    }




}
