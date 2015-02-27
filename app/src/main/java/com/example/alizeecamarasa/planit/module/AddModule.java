package com.example.alizeecamarasa.planit.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.BudgetModuleAPI;
import com.example.alizeecamarasa.planit.budget.BudgetModuleService;
import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.guest.GuestModuleAPI;
import com.example.alizeecamarasa.planit.guest.GuestModuleService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 22/02/15.
 */
public class AddModule extends Activity {
    Context context;
    Event event;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_module);
        context = this;

        event = (Event)getIntent().getSerializableExtra("event");

        // Map<position,isAlreadyUsed>
        Map<Integer,Boolean> isAlreadyUsed = new HashMap<Integer, Boolean>();
        // Images
        List<Integer> mThumbIds = new ArrayList<Integer>();

        for (int i=0;i<=4; i++){
            isAlreadyUsed.put(i,isInList(i));
            // is the module is available
            if (isInList(i)== false){
                switch (i){
                    case 0:
                        mThumbIds.add(R.drawable.guest);
                    break;
                    case 1:
                        mThumbIds.add(R.drawable.budget);
                        break;
                    case 2:
                        mThumbIds.add(R.drawable.place);
                        break;
                    case 3:
                        mThumbIds.add(R.drawable.transport);
                        break;
                    case 4:
                        mThumbIds.add(R.drawable.todo);
                        break;
                }
            }
            // if the module is already used
            else {
                switch (i){
                    case 0:
                        mThumbIds.add(R.drawable.guest_disable);
                        break;
                    case 1:
                        mThumbIds.add(R.drawable.budget_disable);
                        break;
                    case 2:
                        mThumbIds.add(R.drawable.place_disable);
                        break;
                    case 3:
                        mThumbIds.add(R.drawable.transport_disable);
                        break;
                    case 4:
                        mThumbIds.add(R.drawable.todo_disable);
                        break;
                }

            }

        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new GridAdapter(this, isAlreadyUsed, mThumbIds));

        // on click -> open the dialog and add the right module
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position){
                    case 0:
                        addModuleGuest();
                        break;
                    case 1:
                        addModuleBudget();
                        break;
                }
            }
        });
    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    // check if a position correspond to a used module
    private boolean isInList(int id){
        for (Module module :event.getModules()){
            if(module.getInt_type() == id+1){
                return true;
            }
        }
        return false;
    }





    /* --------------------------------- ADD A GUEST MODULE -------------------------------------*/
    public void addModuleGuest(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_guestmodule);
        dialog.setTitle(R.string.name_module_guest);

        final EditText nb_max_guest = (EditText) dialog.findViewById(R.id.nbmaxguest);
        final RadioGroup radiogroup = (RadioGroup) dialog.findViewById(R.id.type_moduleguest);
        final CheckBox paying = (CheckBox) dialog.findViewById(R.id.paying);

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button validate = (Button) dialog.findViewById(R.id.validatenewmodule);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if one of the field is empty, do nothing
                if (isEmptyEditText(nb_max_guest)) {
                    Toast.makeText(AddModule.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    RadioButton radioChosen = (RadioButton) dialog.findViewById(radiogroup.getCheckedRadioButtonId());
                    Boolean choice;
                    if (radioChosen.getText().equals("Sur invitation"))
                        choice = true;
                    else
                        choice = false;

                    JSONObject json = new JSONObject();
                    JSONObject moduleJson = new JSONObject();
                    try {
                        moduleJson.put("moduletype",choice);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        moduleJson.put("maxguests",Float.parseFloat(nb_max_guest.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        moduleJson.put("payable",paying.isChecked());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("guestsmodule_form",moduleJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    GuestModuleService service = GuestModuleAPI.getInstance();
                    service.addGuestModule(event.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response s, Response response) {
                            Toast.makeText(AddModule.this, "Le module a bien été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                            finish();
                        }
                    });
                }
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    /* --------------------------------- ADD A BUDGET MODULE -------------------------------------*/
    public void addModuleBudget(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_budgetmodule);
        dialog.setTitle(R.string.name_module_budget);

        final EditText budget_max = (EditText) dialog.findViewById(R.id.maxbudget);
        final EditText base = (EditText) dialog.findViewById(R.id.base);


        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button validate = (Button) dialog.findViewById(R.id.validatenewmodule);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if one of the field is empty, do nothing
                if (isEmptyEditText(budget_max) || isEmptyEditText(base)) {
                    Toast.makeText(AddModule.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    JSONObject json = new JSONObject();
                    JSONObject moduleJson = new JSONObject();
                    try {
                        moduleJson.put("max_budget",Float.parseFloat(budget_max.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        moduleJson.put("base",Float.parseFloat(base.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("budgetmodule_form",moduleJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    BudgetModuleService service = BudgetModuleAPI.getInstance();
                    service.addBudgetModule(event.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response s, Response response) {
                            Toast.makeText(AddModule.this, "Le module a bien été ajouté!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                            finish();
                        }
                    });
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
