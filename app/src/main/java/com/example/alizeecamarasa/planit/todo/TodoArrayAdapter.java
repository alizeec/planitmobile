package com.example.alizeecamarasa.planit.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.R;


import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 26/02/15.
 */
public class TodoArrayAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<CategoryTask, List<Task>> taskCollections;
    private List<CategoryTask> listCategories;
    private TodoModule module;
    private TodoModuleService service;

    private ImageView delete;


    public TodoArrayAdapter(Activity context, List<CategoryTask> listCategories,
                              Map<CategoryTask, List<Task>> taskCollections, TodoModule module) {
        this.context = context;
        this.taskCollections = taskCollections;
        this.listCategories = listCategories;
        this.module = module;
        this.service = TodoModuleAPI.getInstance();


    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
    }

    @Override
    public Task getChild(int groupPosition, int childPosition) {
        return taskCollections.get(listCategories.get(groupPosition)).get(childPosition);
    }

    public String getChildContent(int groupPosition, int childPosition) {
        return taskCollections.get(listCategories.get(groupPosition)).get(childPosition).getContent();
    }

    public boolean isChildChecked(int groupPosition, int childPosition) {
        return taskCollections.get(listCategories.get(groupPosition)).get(childPosition).isChecked();
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


        // put data in the list
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final View view = convertView;
        final ViewGroup viewgroup = parent;
        final String content = (String) getChildContent(groupPosition, childPosition);
        final boolean checked = (Boolean) isChildChecked(groupPosition, childPosition);

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task_row_view, null);
        }

        TextView txtContent = (TextView) convertView.findViewById(R.id.task);
        final CheckBox cbChecked = (CheckBox) convertView.findViewById(R.id.checkbox);
        final LinearLayout layout_checkbox = (LinearLayout) convertView.findViewById((R.id.layout_checkbox));
        txtContent.setText(content);

        // unbind the setOnCheckedChangeListener on scroll
        cbChecked.setOnCheckedChangeListener(null);
        cbChecked.setChecked(checked);
        // if checked, we pass the border in orange
        if (checked == true){
            layout_checkbox.setBackgroundColor(context.getResources().getColor(R.color.todo));
        }
        else {
            layout_checkbox.setBackgroundColor(context.getResources().getColor(R.color.bg_list));
        }
        // ON CHECKBOX CHANGE
        cbChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   final boolean checked= isChecked;
                   final int id =getChild(groupPosition,childPosition).getId();
                   int value=0;
                   if (isChecked){
                       value = 1;
                   }
                   service.changeChecked(id,value,new Callback<Response>(){
                       @Override
                       public void success(Response response, Response response2) {
                           getChild(groupPosition,childPosition).setChecked(checked);
                           // put the task at the end if it's checked and at the beginning if it's unchecked
                           // call the function to reload colors
                           List<Task> childs = taskCollections.get(listCategories.get(groupPosition));
                           if (checked) {
                               childs.add(childs.size()-1,childs.remove(childPosition));
                               getChildView(groupPosition,childs.size()-1,false,view,viewgroup);
                           }
                           else {
                               childs.add(0,childs.remove(childPosition));
                               getChildView(groupPosition,0,false,view,viewgroup);
                           }
                           notifyDataSetChanged();
                       }

                       @Override
                       public void failure(RetrofitError error) {
                           error.printStackTrace();
                       }
                   });

               }
           }
        );


        // DELETE ITEM
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voulez vous supprimer cette tâche ?");
                builder.setCancelable(false);
                builder.setPositiveButton("OUI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                List<Task> child =
                                        taskCollections.get(listCategories.get(groupPosition));
                                    service.deleteTask(getChild(groupPosition, childPosition).getId(), new Callback<Response>() {
                                        @Override
                                        public void success(Response o, Response response) {
                                            Toast.makeText(context, "Tâche supprimée", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            System.out.println("erreur");
                                            error.printStackTrace();
                                        }
                                    });
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("NON",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return taskCollections.get(listCategories.get(groupPosition)).size();
    }

    public Object getGroupLabel(int groupPosition) {
        return listCategories.get(groupPosition).getName();
    }

    public int getGroupCount() {
        return listCategories.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public CategoryTask getGroup(int groupPosition) { return listCategories.get(groupPosition); }

    //put data in lists headers
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String label = (String) getGroupLabel(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_row_view,
                    null);
        }

        TextView txtlabel = (TextView) convertView.findViewById(R.id.label);
        txtlabel.setText(label);

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
