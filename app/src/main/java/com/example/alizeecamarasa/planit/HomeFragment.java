package com.example.alizeecamarasa.planit;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class HomeFragment extends Fragment {
    /**
     * TODO : 1. override onCreateView method and load layout file
     * TODO : 2. implement the view in the layout file
     * TODO : 3. add private instance members for views
     * TODO : 2. override onActivityCreated method, get views and push job data in views
     */

    private LinearLayout event;
    private TextView title_event;
    private ImageView photo_event;
    private TextView descr_event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get arguments send by the activity
        Bundle args = getArguments();
        // get application context
        Context context=getActivity();

        View parent = getView();
        event = (LinearLayout)parent.findViewById(R.id.event);
        title_event = (TextView)parent.findViewById(R.id.title_event);
        photo_event = (ImageView)parent.findViewById(R.id.photo_event);
        descr_event = (TextView)parent.findViewById(R.id.descr_event);


        //job = JobsContent.findJob(args.getString("job_id"));



        title_event.setText("Titre");
        descr_event.setText("description");
        //detail_2.setText(mJob.getLocation());
        //detail_3.setText(mJob.getDuration());
        //detail_4.setText(mJob.getStart());
        //description_content.setText(mJob.getDescription());

        /*

        setHasOptionsMenu(true);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showJobApplyDialog();
            }
        });

        JobDetailTask task = new JobDetailTask();
        task.execute(args.getString("job_id"));*/
    }
}
