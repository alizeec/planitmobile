package com.example.alizeecamarasa.planit.guest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;


import com.example.alizeecamarasa.planit.R;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.example.alizeecamarasa.planit.guest.Guest.AddGuest;
import com.example.alizeecamarasa.planit.guest.Guest.Guest;
import com.example.alizeecamarasa.planit.guest.TypeGuest.AddTypeGuest;
import com.example.alizeecamarasa.planit.guest.TypeGuest.ChangeTypeGuest;
import com.example.alizeecamarasa.planit.guest.TypeGuest.TypeGuest;
import com.example.alizeecamarasa.planit.module.ModuleAPI;
import com.example.alizeecamarasa.planit.module.ModuleService;
import com.example.alizeecamarasa.planit.utils.Utils;


import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class GuestActivity extends Activity {

    List<TypeGuest> groupList;
    List<Guest> childList;
    Map<TypeGuest, List<Guest>> guestCollection;
    ExpandableListView expListView;
    Activity context;
    GuestModuleService service;
    GuestModule mModule;
    String id_module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        context = this;
        id_module = getIntent().getStringExtra("module_id");
    }

    @Override
    protected void onStart(){
        super.onStart();
        service = GuestModuleAPI.getInstance();
        createGroupList();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int width = getResources().getDisplayMetrics().widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(width - Utils.getPixelValue(40, context), width - Utils.getPixelValue(10, context));
        } else {
            expListView.setIndicatorBoundsRelative(width - Utils.getPixelValue(40, context), width - Utils.getPixelValue(10, context));
        }
    }


    private void createGroupList() {

        // get module from API
        service.getModule(id_module, new Callback<GuestModule>() {
            @Override
            public void success(final GuestModule module, Response response) {
                if (module != null) {
                    mModule = module;
                    if(mModule.getType_guest().size()!=0){
                        findViewById(R.id.empty_list).setVisibility(View.INVISIBLE);
                    }

                    // DELETE MODULE
                    final ModuleService moduleService = ModuleAPI.getInstance();
                    ImageButton delete = (ImageButton) findViewById(R.id.delete_module);
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
                    // UPDATE MODULE
                    ImageButton update = (ImageButton) findViewById(R.id.modify_module);
                    update.setOnClickListener(new View.OnClickListener() {
                          public void onClick(View v) {
                              updateModuleGuest();
                          }
                      });


                    // CAS DES INSCRIPTION / INVITATION
                    // inscription
                    Button action = (Button) findViewById(R.id.action);
                    TextView subtitle = (TextView) findViewById(R.id.subtitle);
                    if (mModule.isModuletype()){
                        action.setText(context.getResources().getString(R.string.get_url));
                        subtitle.setText(context.getResources().getString(R.string.suscribe));

                        // get the URL to form subscription
                        action.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                service.getURL(id_module,new Callback<String>(){

                                    @Override
                                    public void success(String s, Response response) {
                                        printURL(s);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        error.printStackTrace();
                                    }
                                });
                            };
                        });
                    }

                    // invitation
                    else {
                        action.setText(context.getResources().getString(R.string.add_guest));
                        subtitle.setText(context.getResources().getString(R.string.invite));

                        // ajout d'un invité, click sur le bouton
                        action.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {
                                Intent myIntentGuest = new Intent(context, AddGuest.class);
                                myIntentGuest.putExtra("module", mModule);
                                context.startActivity(myIntentGuest);
                            }

                            ;


                        });
                    }

                    // BUILD THE LIST
                    groupList = mModule.getType_guest();
                    createCollection();

                    expListView = (ExpandableListView) findViewById(R.id.laptop_list);
                    final TypeGuestArrayAdapter expListAdapter = new TypeGuestArrayAdapter(context, groupList, guestCollection, module);
                    expListView.setAdapter(expListAdapter);
                    int width = getResources().getDisplayMetrics().widthPixels;
                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        expListView.setIndicatorBounds(width - Utils.getPixelValue(40,context), width - Utils.getPixelValue(10,context));
                    } else {
                        expListView.setIndicatorBoundsRelative(width - Utils.getPixelValue(40,context), width - Utils.getPixelValue(10,context));
                    }
                    // if group is empty, we print message
                    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            if (expListAdapter.getChildrenCount(groupPosition) == 0){
                                Toast.makeText(getApplicationContext(), getString(R.string.empty_cat)+" "+ expListAdapter.getGroupLabel(groupPosition), Toast.LENGTH_SHORT).show();

                            }
                                return false;
                        }
                    });

                    expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                                int positionGroup = ExpandableListView.getPackedPositionGroup(id);
                                Intent intent = new Intent(context,ChangeTypeGuest.class);
                                intent.putExtra("typeguest",expListAdapter.getGroup(positionGroup));
                                intent.putExtra("module",mModule);
                                context.startActivity(intent);
                                return true;
                            }

                            return false;
                        }
                    });

                    // add a type guest
                    Button addTypeGuest = (Button) findViewById(R.id.add_type_guest);
                    addTypeGuest.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            Intent myIntentTypeGuest = new Intent(context, AddTypeGuest.class);
                            myIntentTypeGuest.putExtra("id_module",id_module);
                            myIntentTypeGuest.putExtra("mode",mModule.isModuletype());
                            myIntentTypeGuest.putExtra("module",mModule);
                            context.startActivity(myIntentTypeGuest);
                        };
                    });
                    // if paying, checkbox is checked
                    if(module.isPayable()){
                        CheckBox paying = (CheckBox)findViewById(R.id.checkbox_paying);
                        paying.setChecked(true);
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }




   private void createCollection() {
        // preparing guest collection(child)
        guestCollection = new LinkedHashMap<TypeGuest, List<Guest>>();

        for (TypeGuest type : groupList) {
                loadChild(type.getGuests());
            guestCollection.put(type, childList);
        }
    }

    private void loadChild(List<Guest> laptopModels) {
        childList = new ArrayList<Guest>();
        for (Guest model : laptopModels)
            childList.add(model);
    }

    /* --------------------------------- UPDATE GUEST MODULE -------------------------------------*/
    public void updateModuleGuest(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_guestmodule);
        dialog.setTitle(R.string.name_module_guest);

        final EditText nb_max_guest = (EditText) dialog.findViewById(R.id.nbmaxguest);
        final RadioGroup radiogroup = (RadioGroup) dialog.findViewById(R.id.type_moduleguest);
        final CheckBox cbPaying = (CheckBox) dialog.findViewById(R.id.paying);

        // put currents data
        nb_max_guest.setText(String.valueOf(mModule.getMax_guests()));
        if (mModule.isModuletype()){
            radiogroup.check(R.id.suscribe);
        }
        else {
            radiogroup.check(R.id.invite);
        }
        if(mModule.isPayable())
            cbPaying.setChecked(true);
        else
            cbPaying.setChecked(false);

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button validate = (Button) dialog.findViewById(R.id.validatenewmodule);
        validate.setText("Modifier");
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RadioButton radioChosen = (RadioButton) dialog.findViewById(radiogroup.getCheckedRadioButtonId());
                    int choice;
                    if (radioChosen.getText().equals("Sur invitation"))
                        choice = 0;
                    else
                        choice = 1;


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
                        moduleJson.put("payable",cbPaying.isChecked());
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
                    service.updateGuestModule(id_module, in, new Callback<Response>() {
                        @Override
                        public void success(Response s, Response response) {
                            Toast.makeText(GuestActivity.this, "Le module a bien été modifié!", Toast.LENGTH_SHORT).show();
                            createGroupList();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                dialog.dismiss();
                }
        });

        dialog.show();
    }

    public void printURL(String s){
        String url = "http://planit.marion-lecorre.com/api/guestsmodules/4"+s;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label",url);
        clipboard.setPrimaryClip(clip);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(url);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Toast.makeText(getApplicationContext(), R.string.copy, Toast.LENGTH_SHORT).show();
    }



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_paying:
                if (checked) {
                    service.changePayable(mModule.getId(), 1, new Callback<Response>() {
                        @Override
                        public void success(Response module, Response response) {
                            Toast.makeText(context, "L'événement est payant!", Toast.LENGTH_SHORT).show();
                            createGroupList();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }

                else {
                    service.changePayable(mModule.getId(), 0, new Callback<Response>() {
                        @Override
                        public void success(Response module, Response response) {
                            Toast.makeText(context, "L'événement est gratuit!", Toast.LENGTH_SHORT).show();
                            createGroupList();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }
                break;

        }
    }

}
