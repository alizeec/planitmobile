package com.example.alizeecamarasa.planit.budget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.Item.AddItem;
import com.example.alizeecamarasa.planit.budget.Item.ItemBudget;
import com.example.alizeecamarasa.planit.budget.TypeBudget.AddTypeBudget;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;
import com.example.alizeecamarasa.planit.guest.GuestModuleAPI;
import com.example.alizeecamarasa.planit.guest.GuestModuleService;
import com.example.alizeecamarasa.planit.module.ModuleAPI;
import com.example.alizeecamarasa.planit.module.ModuleService;
import com.example.alizeecamarasa.planit.utils.Utils;


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
    List<ItemBudget> childList;
    Map<TypeBudget, List<ItemBudget>> itemCollection;
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

        // get module from API
        service.getModule(id_module, new Callback<BudgetModule>() {
            @Override
            public void success(BudgetModule module, Response response) {
                if (module != null) {
                    mModule = module;

                    groupList = mModule.getTypeBudgetList();

                    // add inflows at the end of the list
                    TypeBudget apports = new TypeBudget();
                    apports.setName("Apports");
                    apports.setItems(mModule.getInflows());
                    groupList.add(apports);

                    createCollection();

                    expListView = (ExpandableListView) findViewById(R.id.budget_list);
                    final BudgetArrayAdapter expListAdapter = new BudgetArrayAdapter(context, groupList, itemCollection, module);
                    expListView.setAdapter(expListAdapter);
                    int width = getResources().getDisplayMetrics().widthPixels;
                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        expListView.setIndicatorBounds(width - Utils.getPixelValue(40, context), width - Utils.getPixelValue(10,context));
                    } else {
                        expListView.setIndicatorBoundsRelative(width - Utils.getPixelValue(40,context), width - Utils.getPixelValue(10,context));
                    }

                    // if group is empty, we print a message
                    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            if (expListAdapter.getChildrenCount(groupPosition) == 0){
                                Toast.makeText(BudgetActivity.this, R.string.empty_list_budget, Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });

                    // DELETE MODULE
                    final ModuleService moduleService = ModuleAPI.getInstance();
                    ImageView delete = (ImageView) findViewById(R.id.delete_module);
                    delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            moduleService.deleteModule(id_module, new Callback<Response>() {
                                @Override
                                public void success(Response budgetModule, Response response) {
                                    finish();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    finish();
                                    error.printStackTrace();
                                }
                            });

                        };
                    });

                    // ADD AN INFLOW (other activity)
                    Button inflow = (Button) findViewById(R.id.add_inflow);
                    inflow.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent myIntent = new Intent(context, AddItem.class);
                            myIntent.putExtra("module",mModule);
                            myIntent.putExtra("id",id_module);
                            myIntent.putExtra("type","inflow");
                            startActivityForResult(myIntent,1);

                        };
                    });

                    // ADD AN EXPENSE (other activity)
                    Button expense = (Button) findViewById(R.id.add_expense);
                    expense.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent myIntent = new Intent(context, AddItem.class);
                            myIntent.putExtra("module",mModule);
                            myIntent.putExtra("type","expense");
                            startActivityForResult(myIntent,2);
                        };
                    });

                    // ADD A TYPE EXPENSE (dialog)
                    Button type_expense = (Button) findViewById(R.id.add_type_expense);
                    type_expense.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Intent myIntent = new Intent(context, AddTypeBudget.class);
                            myIntent.putExtra("id",id_module);
                            startActivityForResult(myIntent, 4);
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



    private void createCollection() {
        itemCollection = new LinkedHashMap<TypeBudget, List<ItemBudget>>();

        for (TypeBudget type : groupList) {
            loadChild(type.getItems());
            itemCollection.put(type, childList);
        }
    }


    private void loadChild(List<ItemBudget> laptopModels) {
        childList = new ArrayList<ItemBudget>();
        for (ItemBudget model : laptopModels)
            childList.add(model);
    }

    // refresh the view only if we come back from an add or a modify (not see)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 || requestCode==2 || requestCode==3 || requestCode==4)
        {
            createGroupList();
        }
    }


}

