package com.example.alizeecamarasa.planit.budget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.Item.AddItem;
import com.example.alizeecamarasa.planit.budget.Item.Item;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by alizeecamarasa on 18/02/15.
 */
public class BudgetActivity extends Activity {

    List<TypeBudget> groupList;
    List<Item> childList;
    Map<TypeBudget, List<Item>> itemCollection;
    ExpandableListView expListView;
    Activity context;
    BudgetModuleService service;
    BudgetModule mModule;
    String id_module;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        context = this;
        id_module = getIntent().getStringExtra("module_id");


        service = BudgetModuleAPI.getInstance();
        createGroupList();

    }


    private void createGroupList() {

        // appel au service, retourne le module
        service.getModule(id_module, new Callback<BudgetModule>() {
            @Override
            public void success(BudgetModule module, Response response) {
                if (module != null) {
                    mModule = module;

                    groupList = mModule.getTypeBudgetList();

                    // ajout des apports en fin de liste
                    TypeBudget apports = new TypeBudget();
                    apports.setName("Apports");
                    apports.setItems(mModule.getInflows());
                    groupList.add(apports);

                    createCollection();

                    expListView = (ExpandableListView) findViewById(R.id.budget_list);
                    expListView.setGroupIndicator (null);
                    final BudgetArrayAdapter expListAdapter = new BudgetArrayAdapter(context, groupList, itemCollection, module);
                    expListView.setAdapter(expListAdapter);

                    // si le groupe est vide, on affiche un message
                    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            if (expListAdapter.getChildrenCount(groupPosition) == 0){
                                Toast.makeText(BudgetActivity.this, R.string.empty_list_budget, Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });

                    // ajout d'un apport
                    Button inflow = (Button) findViewById(R.id.add_inflow);
                    inflow.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //adding new event : starting new Event activity
                            Intent myIntent = new Intent(context, AddItem.class);
                            myIntent.putExtra("module",mModule);
                            myIntent.putExtra("id",id_module);
                            myIntent.putExtra("type","inflow");
                            startActivityForResult(myIntent,1);

                        };
                    });

                    // ajout d'une dépense
                    Button expense = (Button) findViewById(R.id.add_expense);
                    expense.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //adding new expense
                            Intent myIntent = new Intent(context, AddItem.class);
                            myIntent.putExtra("module",mModule);
                            myIntent.putExtra("type","expense");
                            startActivityForResult(myIntent,2);
                        };
                    });

                    // ajout d'un type de dépense
                    Button type_expense = (Button) findViewById(R.id.add_type_expense);
                    type_expense.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle(R.string.add_type_expense);
                            alert.setMessage("Nom");
                            final EditText input = new EditText(context);
                            alert.setView(input);

                            alert.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String value = input.getText().toString();
                                    addTypeExpense(value);

                                }
                            });

                            alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            });

                            alert.show();
                        }

                    });
                };

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }


    private void addTypeExpense ( String v){
        final String value = v;
        JSONObject json = new JSONObject();
        JSONObject typeExpenseJson = new JSONObject();
        try {
            typeExpenseJson.put("name",value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("typeexpense_form",typeExpenseJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());
        service.addTypeExpense(id_module, in, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject jsonObject, Response response) {
                Toast.makeText(BudgetActivity.this, "Le type de dépense " + value +" a été ajouté!", Toast.LENGTH_SHORT).show();
                createGroupList();
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }


    private void createCollection() {
        itemCollection = new LinkedHashMap<TypeBudget, List<Item>>();

        for (TypeBudget type : groupList) {
            loadChild(type.getItems());
            itemCollection.put(type, childList);
        }
    }


    private void loadChild(List<Item> laptopModels) {
        childList = new ArrayList<Item>();
        for (Item model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        //* Get the screen width *//*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }




 /*   @Override
    public void onResume(){
        super.onResume();
        createGroupList();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1 || requestCode==2 || requestCode==3)
        {
            createGroupList();
        }
    }


}

