package com.example.alizeecamarasa.planit;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TwoLineListItem;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Yoann on 03/10/2014.
 */
public class HomeFragment extends ListFragment {
    private Context mContext;
    HomeTask mTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the application context
        mContext = (HomeActivity)getActivity();
        // get the job list
        //List<Job> jobs = JobsContent.JOBS;

        // display the job list
        mTask = new HomeTask();
        mTask.execute();
        //setListAdapter(new ArrayAdapter<Job>(mContext, android.R.layout.simple_list_item_1, jobs.doInBackground()));
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the selected job
        Event selectedEvent = (Event)getListView().getItemAtPosition(position);
        Intent intent= new Intent(mContext,EventActivity.class);
        intent.putExtra("event_id",selectedEvent.getId());
        startActivity(intent);
        // delegate click to the activity
        //mContext.onJobSelected(selectedJob.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTask.cancel(true);

    }

    class HomeTask extends AsyncTask<Void, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(Void... params) {
            List<Event> events= null;
            try {
                return events = EventAPI.getEvents(getActivity());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
        if(events!=null)
            setListAdapter(new CustomArrayAdapter(mContext,events));
        }
    }
}