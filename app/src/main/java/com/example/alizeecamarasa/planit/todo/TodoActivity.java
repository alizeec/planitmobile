package com.example.alizeecamarasa.planit.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;
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
 * Created by alizeecamarasa on 26/02/15.
 */
public class TodoActivity extends Activity {

   Activity context;
   String id_module;
   TodoModuleService service;
   TodoModule mModule;
    List<CategoryTask> groupList;
    List<Task> childList;
    Map<CategoryTask, List<Task>> taskCollection;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        context = this;
        id_module = getIntent().getStringExtra("module_id");
        service = TodoModuleAPI.getInstance();
        createGroupList();
    }


    private void createGroupList() {

        // get module from API
        service.getModule(id_module, new Callback<TodoModule>() {
            @Override
            public void success(TodoModule module, Response response) {
                if (module != null) {
                    mModule = module;

                    groupList = mModule.getListCategories();

                    createCollection();

                    expListView = (ExpandableListView) findViewById(R.id.todo_list);
                    final TodoArrayAdapter expListAdapter = new TodoArrayAdapter(context, groupList, taskCollection, module);
                    expListView.setAdapter(expListAdapter);
                    int width = getResources().getDisplayMetrics().widthPixels;
                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        expListView.setIndicatorBounds(width - Utils.getPixelValue(40, context), width - Utils.getPixelValue(10, context));
                    } else {
                        expListView.setIndicatorBoundsRelative(width - Utils.getPixelValue(40, context), width - Utils.getPixelValue(10, context));
                    }

                    // if group is empty, we print a message
                    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            if (expListAdapter.getChildrenCount(groupPosition) == 0) {
                                Toast.makeText(TodoActivity.this, R.string.empty_list_budget, Toast.LENGTH_SHORT).show();
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
                    // END DELETE MODULE

                    // ADD A CATEGORY TASK (dialog)
                    Button add_cat_task = (Button) findViewById(R.id.add_cat_task);
                    add_cat_task.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            addCategoryTask();
                        }

                    });

                    // ADD A TASK (dialog)
                    Button add_task = (Button) findViewById(R.id.add_task);
                    add_task.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v) {
                            addtask();
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
        taskCollection = new LinkedHashMap<CategoryTask, List<Task>>();
        for (CategoryTask cat : groupList) {
            loadChild(cat.getItems());
            taskCollection.put(cat, childList);
        }
    }


    private void loadChild(List<Task> laptopModels) {
        childList = new ArrayList<Task>();
        for (Task model : laptopModels) {
            //childList.add(model);
            if (model.isChecked())
                childList.add(childList.size(),model);
            else
                childList.add(0,model);
        }
    }

    /* ------------------------------ ADD CATEGORY TASK --------------------------------------- */
    public void addCategoryTask(){
        final Dialog dialog = new Dialog(this,R.style.cust_dialog_todo);
        dialog.setTitle(R.string.add_cat_task);
        dialog.setContentView(R.layout.add_cat_task);
        final EditText category = (EditText) dialog.findViewById(R.id.category);

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button validate = (Button) dialog.findViewById(R.id.validatenewcat);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if one of the field is empty, do nothing
                if (isEmptyEditText(category)) {
                    Toast.makeText(TodoActivity.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    JSONObject json = new JSONObject();
                    JSONObject catJson = new JSONObject();
                    try {
                        catJson.put("name",category.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("tasklist_form",catJson);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    service.addCategoryTask(mModule.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response s, Response response) {
                            Toast.makeText(TodoActivity.this, "La catégorie a bien été ajouté!", Toast.LENGTH_SHORT).show();
                            createGroupList();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    /* ------------------------------ ADD TASK --------------------------------------- */
    public void addtask(){
        final Dialog dialog = new Dialog(this,R.style.cust_dialog_todo);
        dialog.setTitle(R.string.add_task);
        dialog.setContentView(R.layout.add_task);
        final EditText task = (EditText) dialog.findViewById(R.id.task);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);

        ArrayAdapter<CategoryTask> dataAdapter = new ArrayAdapter<CategoryTask>(this,android.R.layout.simple_spinner_item, groupList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button validate = (Button) dialog.findViewById(R.id.validatenewtask);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if one of the field is empty, do nothing
                if (isEmptyEditText(task)) {
                    Toast.makeText(TodoActivity.this, R.string.create_event_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                    CategoryTask category = (CategoryTask) spinner.getSelectedItem();

                    JSONObject json = new JSONObject();
                    JSONObject taskJson = new JSONObject();
                    try {
                        taskJson.put("content",task.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json.put("item_form",taskJson);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    TypedInput in = new TypedByteArray("application/json", json.toString().getBytes());

                    service.addTask(category.getId(), in, new Callback<Response>() {
                        @Override
                        public void success(Response s, Response response) {
                            Toast.makeText(TodoActivity.this, "La tâche a bien été ajouté!", Toast.LENGTH_SHORT).show();
                            createGroupList();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
                }
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
