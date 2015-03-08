package com.example.alizeecamarasa.planit.budget.TypeBudget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by alizeecamarasa on 27/02/15.
 */
public class AddTypeBudget extends Activity {

    private EditText name;
    private Button validate;
    private String module_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get type (inflow or expense)
        module_id = getIntent().getStringExtra("id");


        setContentView(R.layout.add_type_expense);
        name = (EditText) findViewById(R.id.inputTypeExpenseLabel);
        validate = (Button) findViewById(R.id.validatenewtypeexpense);

        addTypeExpense();
    }


    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void addTypeExpense() {

        validate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //if one of the field is empty, do nothing
                if (isEmptyEditText(name)) {
                    Toast.makeText(AddTypeBudget.this, R.string.error_msg_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    JSONObject json = new JSONObject();
                    JSONObject typeexpenseJson = new JSONObject();
                    try {
                        typeexpenseJson.put("name",name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("typeexpense_form",typeexpenseJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    BudgetModuleService service = BudgetModuleAPI.getInstance();
                    service.addTypeExpense(module_id, in, new Callback<Response>() {
                        @Override
                        public void success(Response event, Response response) {
                            Toast.makeText(AddTypeBudget.this, "Le type de dépense " + name.getText().toString() +" a été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                            finish();
                        }

                    });
                    setResult(4);
                }
            }
        });
    }




}

