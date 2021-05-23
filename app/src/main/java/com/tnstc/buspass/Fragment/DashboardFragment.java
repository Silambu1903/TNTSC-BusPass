package com.tnstc.buspass.Fragment;

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.DateAndDayAdapter;
import com.tnstc.buspass.Database.DAOs.DutyDao;
import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.DashboardFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    DashboardFragmentBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    BaseActivity activity;
    List<Calendar> beforeMin;
    List<String> currentDate;
    List<String> currentDatetoPrevious;
    DateAndDayAdapter mAdapter;
    String Duty;
    List<String> dutySaved;
    TnstcBusPassDB db;
    DutyDao dutyDao;
    List<DutyEntity> dutyEntityList;
    boolean pass = false;


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
        changedState(new AttendanceDesignFragment(), mBinding.textView3, mBinding.textView4, mBinding.textView5, mBinding.textView6);
        connectWifi();




        mBinding.textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_duty_list);
            }
        });

        mBinding.textView3.setOnClickListener(this);
        mBinding.textView4.setOnClickListener(this);
        mBinding.textView5.setOnClickListener(this);
        mBinding.textView6.setOnClickListener(this);


    }

    private void connectWifi() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifiManager.setWifiEnabled(false);
            String ssid = wifiInfo.getSSID();
            Log.e("TAG", "connectWifi: " + ssid);
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        activity.getSupportActionBar().show();
        Log.e("TAG", "onStop: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.getSupportActionBar().hide();
        Log.e("TAG", "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.getSupportActionBar().hide();
        if (pass) {
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG", "onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG", "onDestroyView: ");
    }








    public void changedState(Fragment fragment, View view, View view1, View view2, View view3) {
        getParentFragmentManager().beginTransaction().replace(mBinding.mainHost.getId(), fragment).commit();
        view.setBackground(getResources().getDrawable(R.drawable.yellow_boder));
        view1.setBackground(getResources().getDrawable(R.drawable.boder_dashboard_icon));
        view2.setBackground(getResources().getDrawable(R.drawable.boder_dashboard_icon));
        view3.setBackground(getResources().getDrawable(R.drawable.boder_dashboard_icon));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView3:
                changedState(new AttendanceDesignFragment(), mBinding.textView3, mBinding.textView4, mBinding.textView5, mBinding.textView6);
                break;
            case R.id.textView4:
                changedState(new PassDesignFragment(), mBinding.textView4, mBinding.textView3, mBinding.textView5, mBinding.textView6);
                break;
            case R.id.textView5:
                changedState(new MstDesignFragment(), mBinding.textView5, mBinding.textView3, mBinding.textView4, mBinding.textView6);
                break;
            case R.id.textView6:
                changedState(new SctDesignFragment(), mBinding.textView6, mBinding.textView5, mBinding.textView3, mBinding.textView4);
                break;
        }
    }

}


