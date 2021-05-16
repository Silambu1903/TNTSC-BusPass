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
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.DashboardFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardFragment extends Fragment implements ItemClickListener {

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
        connectWifi();
        beforeMin = new ArrayList<>();
        dutySaved = new ArrayList<>();

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
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_passentry);
            }
        });
        mBinding.passListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_passentrylist);
            }
        });
        mBinding.passMonthlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_passmonthwiselist);
            }
        });

        mBinding.mstEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_mstsctentry);
            }
        });

        mBinding.mstListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_mst_list);
            }
        });
        mBinding.mstMonthlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_mst_month);
            }
        });
        mBinding.sctEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_sctentry);
            }
        });
        mBinding.sctListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_sct_list);
            }
        });
        mBinding.sctMonthlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_sct_month);
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
        String[] date = currentDates.split("\\.");
        mBinding.textViewDate.setText(date[0]);
        mBinding.txtDay.setText(date[3]);
        String monthYear = date[1] + " " + date[2];
        mBinding.txtMonthYear.setText(monthYear);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.getSupportActionBar().hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.getSupportActionBar().hide();
    }


    @Override
    public void OnItemClick(View v, int pos) {


    }

    @Override
    public void OnItemLongClick(View v, int pos) {

    }

    @Override
    public void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout) {

        if (Validation()) {
            String position = String.valueOf(adapterPosition);
            String Date = currentDateAndDay.get(adapterPosition);

            if (mBinding.conductorCheckbox.isChecked() && mBinding.busPassCheckbox.isChecked()
                    && mBinding.otherdutyCheckbox.isChecked() && mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "Conductor,BusPass,CashSection,ControlSection";
            } else if (mBinding.conductorCheckbox.isChecked() && mBinding.busPassCheckbox.isChecked()
                    && mBinding.otherdutyCheckbox.isChecked()) {
                Duty = "Conductor,BusPass,CashSection";
            } else if (mBinding.busPassCheckbox.isChecked() && mBinding.otherdutyCheckbox.isChecked()
                    && mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "BusPass,CashSection,ControlSection";
            } else if (mBinding.conductorCheckbox.isChecked() && mBinding.busPassCheckbox.isChecked()
                    && mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "Conductor,BusPass,ControlSection";
            } else if (mBinding.conductorCheckbox.isChecked() && mBinding.otherdutyCheckbox.isChecked()
                    && mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "Conductor,CashSection,ControlSection";
            } else if (mBinding.conductorCheckbox.isChecked() && mBinding.busPassCheckbox.isChecked()) {
                Duty = "Conductor,BusPass";
            } else if (mBinding.controlsectionCheckbox.isChecked() && mBinding.otherdutyCheckbox.isChecked()) {
                Duty = "ControlSection,CashSection";
            } else if (mBinding.conductorCheckbox.isChecked() && mBinding.otherdutyCheckbox.isChecked()) {
                Duty = "Conductor,CashSection";
            } else if (mBinding.busPassCheckbox.isChecked() && mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "BusPass,ControlSection";
            } else if (mBinding.conductorCheckbox.isChecked() && mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "Conductor,ControlSection";
            } else if (mBinding.busPassCheckbox.isChecked() && mBinding.otherdutyCheckbox.isChecked()) {
                Duty = "BusPass,CashSection";
            } else if (mBinding.conductorCheckbox.isChecked()) {
                Duty = "Conductor";
            } else if (mBinding.busPassCheckbox.isChecked()) {
                Duty = "BusPass";
            } else if (mBinding.otherdutyCheckbox.isChecked()) {
                Duty = "CashSection";
            } else if (mBinding.controlsectionCheckbox.isChecked()) {
                Duty = "ControlSection";
            }
            if (!dutySaved.equals("")) {
                if (dutySaved.contains("position" + position + "," + Date + "," + Duty)) {
                    int Index = dutySaved.indexOf("position" + position + "," + Date + "," + Duty);
                    dutySaved.remove(Index);
                    constraintLayout.setBackground(getResources().getDrawable(R.drawable.whiteboder));
                    Toast.makeText(mContext, "" + dutySaved.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    dutySaved.add("position" + position + "," + Date + "," + Duty);
                    Toast.makeText(mContext, "" + dutySaved.toString(), Toast.LENGTH_SHORT).show();
                    constraintLayout.setBackground(getResources().getDrawable(R.drawable.yellow_boder));
                }
            }
        }
    }

    public boolean Validation() {
        if (!mBinding.conductorCheckbox.isChecked() && !mBinding.busPassCheckbox.isChecked()
                && !mBinding.otherdutyCheckbox.isChecked() && !mBinding.controlsectionCheckbox.isChecked()) {
            mAppClass.showSnackBar(getContext(), "Select one of the duty");
            return false;
        }
        return true;
    }
}


