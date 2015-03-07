package com.example.alizeecamarasa.planit;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alizeecamarasa.planit.budget.BudgetActivity;
import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventResponse;
import com.example.alizeecamarasa.planit.events.EventService;
import com.example.alizeecamarasa.planit.guest.GuestActivity;
import com.example.alizeecamarasa.planit.module.AddModule;
import com.example.alizeecamarasa.planit.module.Module;
import com.example.alizeecamarasa.planit.place.PlaceActivity;
import com.example.alizeecamarasa.planit.todo.TodoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alizeecamarasa on 07/03/15.
 */
public class EventFragment extends ListFragment {
    private TextView title;
    private TextView description;
    private ImageView imageview;
    private String id_event;
    private TextView nbdodos;
    private TextView nbguests;
    private TextView inflows;
    private TextView expenses;
    private Event mEvent;
    private Activity mContext;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_module_event, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = (EventActivity)getActivity();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.header_event, null, false);
        lv = (ListView) getView().findViewById(android.R.id.list);
        lv.addHeaderView(header);
        title = (TextView) getView().findViewById(R.id.title);
        description = (TextView) getView().findViewById(R.id.descr_event);
        imageview = (ImageView) getView().findViewById(R.id.image);
        nbdodos= (TextView) getView().findViewById(R.id.dodos);
        nbguests= (TextView) getView().findViewById(R.id.guests);
        inflows= (TextView) getView().findViewById(R.id.inflow);
        expenses= (TextView) getView().findViewById(R.id.expense);


        id_event = getActivity().getIntent().getStringExtra("event_id");

        EventService service = EventAPI.getInstance();
        service.getEvent(id_event, new Callback<EventResponse>() {
            @Override
            public void success(EventResponse event, Response response) {
                if (event.getEvent() != null){
                    mEvent = event.getEvent();
                    updateView(event);
                }

            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    // call the EventActivity and pass the id
    public void onListItemClick(ListView l, View v, int position, long id) {
        Module selectedModule = (Module)lv.getItemAtPosition(position);
        Intent intent;
        switch (selectedModule.getInt_type()){
            // module invitation/inscription
            case 1:
                intent= new Intent(mContext,GuestActivity.class);
                intent.putExtra("module_id",String.valueOf(selectedModule.getId()));
                startActivity(intent);
                break;
            // module budget
            case 2:
                intent= new Intent(mContext,BudgetActivity.class);
                intent.putExtra("module_id",String.valueOf(selectedModule.getId()));
                startActivity(intent);
                break;
            // module Lieu
            case 3:
                intent= new Intent(mContext,PlaceActivity.class);
                intent.putExtra("module_id",String.valueOf(selectedModule.getId()));
                startActivity(intent);
                break;
            // module Transport
            case 4:
                Toast.makeText(mContext, "Module Transport à venir", Toast.LENGTH_SHORT).show();
                break;
            // module liste de tâches
            case 5:
                intent= new Intent(mContext,TodoActivity.class);
                intent.putExtra("module_id",String.valueOf(selectedModule.getId()));
                startActivity(intent);
                break;
            // ajouter un module
            case 6:
                intent= new Intent(mContext,AddModule.class);
                intent.putExtra("event",mEvent);
                startActivity(intent);
                break;

        }
    }

    // update the event
    private void updateView (EventResponse event){
        title.setText(event.getEvent().getName());
        description.setText(event.getEvent().getDescription());
        Picasso.with(mContext).load("http://planit.marion-lecorre.com/images/event/events_pictures/"+event.getEvent().getImage())
                .error(R.drawable.no_image)
                .into(imageview);
        nbdodos.setText(event.getEvent().getTimeDiff());
        nbguests.setText(event.getNb_guest() + " " + getString(R.string.guests));
        expenses.setText(getString(R.string.expenses)+"\n"+event.getBalance());
        inflows.setText(getString(R.string.inflow)+"\n"+event.getBalance());

        // add a module in order to add a row "Ajouter un module" in the list
        Module addModule = new Module();
        addModule.setName("Ajouter un module");
        addModule.setInt_type(6);
        if(event.getEvent().getModules()!=null){
            event.getEvent().getModules().add(addModule);
        }
        else {
            ArrayList<Module> newlist = new ArrayList<Module>();
            newlist.add(addModule);
            event.getEvent().setModules(newlist);
        }
        lv.setAdapter(new ModulesArrayAdapter(mContext, event.getEvent().getModules()));
    }
}
