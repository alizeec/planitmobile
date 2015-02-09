package com.example.alizeecamarasa.planit;


import android.app.ActionBar;

import android.content.Context;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventService;
import com.example.alizeecamarasa.planit.module.Module;

import android.widget.ArrayAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yoann on 05/10/2014.
 */
public class EventFragment extends Fragment  {

    private Context mContext;

    private TextView name;
    private TextView description;
    private Event mEvent;
    private TextView titleActionBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get arguments send by the activity
        Bundle args = getArguments();
        String id = args.getString("event_id");
        // get application context
        mContext =(EventActivity)getActivity();
        ActionBar actionbar=getActivity().getActionBar();
        actionbar.setCustomView(R.layout.custom_bar);
        actionbar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM, android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayHomeAsUpEnabled(false); // Remove '<' next to home icon.

        View parent = getView();
        description = (TextView)parent.findViewById(R.id.descr_event);
        titleActionBar = (TextView)actionbar.getCustomView().findViewById(R.id.titleActionBar);

        setHasOptionsMenu(true);

        EventService service = EventAPI.getInstance();
        service.getEvent(id, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                System.out.println(event);
                if (event != null)
                    updateView(event);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }




    private void updateView (Event event){
            description.setText(event.getDescription());
            titleActionBar.setText(event.getName());
            if(event.getModules()!=null){
                //setListAdapter(new ModulesArrayAdapter(mContext,event.getModules()));

        }
    }


}

