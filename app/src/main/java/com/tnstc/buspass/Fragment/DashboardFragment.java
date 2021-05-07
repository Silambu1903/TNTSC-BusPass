package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.DateAndDayAdapter;
import com.tnstc.buspass.Model.DateAndDay;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.DashboardFragmentBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements View.OnClickListener, ItemClickListener {

    DashboardFragmentBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    BaseActivity activity;
    List<Calendar> beforeMin;
    List<String> currentDate;
    List<String> currentDatetoPrevious;
    DateAndDayAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dashboard_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mContext = getContext();
        activity = (BaseActivity) getActivity();
        activity.getSupportActionBar().hide();
        mBinding.textView4.setOnClickListener(this);
        beforeMin = new ArrayList<>();
        currentDatetoPrevious = new ArrayList();
        for (int i = 15; i >= 1; i--) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy.EE");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(mAppClass.getCurrentDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, -i);  // number of days to add
            String currentDates = sdf.format(c.getTime());
            currentDatetoPrevious.add(currentDates);

        }
        currentDate = new ArrayList();
        for (int i = 0; i <= 15; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy.EE");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(mAppClass.getCurrentDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, i);  // number of days to add
            String currentDates = sdf.format(c.getTime());
            currentDate.add(currentDates);
        }
        currentDatetoPrevious.addAll(currentDate);
        mAdapter = new DateAndDayAdapter(currentDatetoPrevious, this);
        mBinding.horiznotalviewLayout.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mBinding.horiznotalviewLayout.setAdapter(mAdapter);
        mBinding.horiznotalviewLayout.smoothScrollToPosition(17);
        mBinding.passEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_passentry);
            }
        });
        mBinding.passListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_passentrylist);
            }
        });
        mBinding.passMonthlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_passmonthwiselist);
            }
        });

        mBinding.mstEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_mstsctentry);
            }
        });

        mBinding.mstListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_mst_list);
            }
        });
        mBinding.mstMonthlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_mst_month);
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy.EE");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(mAppClass.getCurrentDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 0);  // number of days to add
        String currentDates = sdf.format(c.getTime());
        String[] date = currentDates.split("\\.") ;
        mBinding.textViewDate.setText(date[0]);
        mBinding.txtDay.setText(date[3]);
        String monthYear=date[1]+" "+date[2];
        mBinding.txtMonthYear.setText(monthYear);
    }



    @Override
    public void onStop() {
        super.onStop();
        activity.getSupportActionBar().show();
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.getSupportActionBar().hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos) {

    }
}
