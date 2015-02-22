package com.example.alizeecamarasa.planit;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListFragment;
import android.widget.Toast;


import com.example.alizeecamarasa.planit.budget.BudgetActivity;
import com.example.alizeecamarasa.planit.events.Event;
import com.example.alizeecamarasa.planit.events.EventAPI;
import com.example.alizeecamarasa.planit.events.EventResponse;
import com.example.alizeecamarasa.planit.events.EventService;
import com.example.alizeecamarasa.planit.guest.GuestActivity;
import com.example.alizeecamarasa.planit.guest.GuestModule;
import com.example.alizeecamarasa.planit.module.Module;
import com.example.alizeecamarasa.planit.utils.PieChart;
import com.example.alizeecamarasa.planit.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Yoann on 05/10/2014.
 */
public class EventFragment extends ListFragment {

    private Context mContext;

    private TextView title;
    private TextView description;
    private ImageView imageview;
    private String id_event;
    private TextView nbdodos;
    private TextView nbguests;
    private TextView inflows;
    private TextView expenses;
    private ListView list;
    private  LinearLayout piechart;



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
        id_event = args.getString("event_id");

        // get application context
        mContext =(EventActivity)getActivity();
        ActionBar actionbar=getActivity().getActionBar();
        actionbar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM, android.app.ActionBar.DISPLAY_SHOW_CUSTOM);


        View parent = getView();
        title = (TextView)parent.findViewById(R.id.title);
        description = (TextView)parent.findViewById(R.id.descr_event);
        imageview = (ImageView)parent.findViewById(R.id.image);
        nbdodos= (TextView)parent.findViewById(R.id.dodos);
        nbguests= (TextView)parent.findViewById(R.id.guests);
        inflows= (TextView)parent.findViewById(R.id.inflow);
        expenses= (TextView)parent.findViewById(R.id.expense);
        //piechart = (LinearLayout) parent.findViewById(R.id.piechart);




        setHasOptionsMenu(true);

        EventService service = EventAPI.getInstance();
        service.getEvent(id_event, new Callback<EventResponse>() {
            @Override
            public void success(EventResponse event, Response response) {
                if (event.getEvent() != null){}
                    updateView(event);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }






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

        setListAdapter(new ModulesArrayAdapter(mContext,event.getEvent().getModules()));

        //GraphicalView chartView = PieChart.getNewInstance(getActivity(), 10, 20);
        //piechart.addView(chartView);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Module selectedModule = (Module)getListView().getItemAtPosition(position);
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
            case 3:
                Toast.makeText(mContext, "Module Lieu à venir", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(mContext, "Module Transport à venir", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(mContext, "Module TODO à venir", Toast.LENGTH_SHORT).show();
                break;
        }

    }





}

