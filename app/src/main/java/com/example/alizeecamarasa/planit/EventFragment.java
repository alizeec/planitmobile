package com.example.alizeecamarasa.planit;


import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.ListFragment;


import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventResponse;
import com.example.alizeecamarasa.planit.events.EventService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yoann on 05/10/2014.
 */
public class EventFragment extends ListFragment {

    private Context mContext;

    private TextView name;
    private TextView description;
    private Event mEvent;
    private TextView titleActionBar;
    private String id_event;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Bundle args = this.getArguments();


        return inflater.inflate(R.layout.fragment_event, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Bundle args = getArguments();

        //id_event = args.getString("event_id");
        id_event = args.getString("event_id");

        //get arguments send by the activity

        // get application context
        mContext =(EventActivity)getActivity();
        ActionBar actionbar=getActivity().getActionBar();
        //actionbar.setCustomView(R.layout.custom_bar);
        actionbar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM, android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionbar.setDisplayHomeAsUpEnabled(false); // Remove '<' next to home icon.


        View parent = getView();
        description = (TextView)parent.findViewById(R.id.descr_event);
        //titleActionBar = (TextView)actionbar.getCustomView().findViewById(R.id.titleActionBar);

        setHasOptionsMenu(true);

        EventService service = EventAPI.getInstance();
        service.getEvent(id_event, new Callback<EventResponse>() {
            @Override
            public void success(EventResponse event, Response response) {
                if (event.getEvent() != null)
                    updateView(event.getEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }




    private void updateView (Event event){
            description.setText(event.getDescription());
           // titleActionBar.setText(event.getName());
            if(event.getModules()!=null){
                setListAdapter(new ModulesArrayAdapter(mContext,event.getModules()));

        }
    }


}

