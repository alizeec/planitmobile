package com.example.alizeecamarasa.planit.budget;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.alizeecamarasa.planit.R;
import com.example.alizeecamarasa.planit.budget.Item.Item;
import com.example.alizeecamarasa.planit.budget.TypeBudget.TypeBudget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
                                // mettre le toast
                            }
                            return false;
                        }
                    });


                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }




    private void createCollection() {
        // preparing laptops collection(child)


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




    @Override
    public void onResume(){
        super.onResume();
        createGroupList();
    }
}

