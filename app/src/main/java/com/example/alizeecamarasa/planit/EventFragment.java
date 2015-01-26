package com.example.alizeecamarasa.planit;


import android.app.ActionBar;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Yoann on 05/10/2014.
 */
public class EventFragment extends Fragment {

    /**
     * TODO : 1. override onCreateView method and load layout file
     * TODO : 2. implement the view in the layout file
     * TODO : 3. add private instance members for views
     * TODO : 2. override onActivityCreated method, get views and push job data in views
     */

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
        // get application context
        Context context=getActivity();
        ActionBar actionbar=getActivity().getActionBar();
        actionbar.setCustomView(R.layout.custom_bar);
        actionbar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM, android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayHomeAsUpEnabled(false); // Remove '<' next to home icon.

        View parent = getView();
        description = (TextView)parent.findViewById(R.id.descr_event);
        titleActionBar = (TextView)actionbar.getCustomView().findViewById(R.id.titleActionBar);

        setHasOptionsMenu(true);


        EventTask task = new EventTask();
        task.execute(args.getString("event_id"));
    }




    private void updateView (Event event){
        if(mEvent!=null) {
            description.setText(mEvent.getDescription());
            titleActionBar.setText(mEvent.getTitle());

        }
    }



    class EventTask extends AsyncTask<String, Void, Event> {
        public boolean error;
        @Override
        protected Event doInBackground(String... ids) {
            Event event= null;
            try {
                return event = EventAPI.getEvent(getActivity(),ids[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Event event) {
            mEvent = event;
            updateView(event);

        }
    }


}

