package com.example.alizeecamarasa.planit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.app.ListFragment;

import java.util.List;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventService;
import com.example.alizeecamarasa.planit.events.AddEvent;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HomeFragment extends ListFragment {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the application context
        mContext = (HomeActivity)getActivity();

        // display the event list
        updateView();

        //adding new event : starting new Event activity
        Button addEvent = (Button) getView().findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(), AddEvent.class);
                HomeFragment.this.startActivity(myIntent);
            }
        });
    }

    public void updateView(){
        // use API to get the event
        EventService service = EventAPI.getInstance();
        service.listEvents(((PlanItApplication) mContext.getApplicationContext()).USER_ID , new Callback<List<Event>>(){
            @Override
            public void success(List<Event> events, Response response){
                if(events!=null)
                    setListAdapter(new EventsArrayAdapter(mContext,events));
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
        Event selectedEvent = (Event)getListView().getItemAtPosition(position);
        Intent intent= new Intent(mContext,EventActivity.class);
        intent.putExtra("event_id",selectedEvent.getId());
        startActivity(intent);
    }

    @Override
    // refresh activity after we add a module
    public void onResume(){
        super.onResume();
        updateView();
    }

}