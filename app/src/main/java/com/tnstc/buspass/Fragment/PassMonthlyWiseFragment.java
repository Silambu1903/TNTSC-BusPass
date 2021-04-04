package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.PassEntryAdapter;
import com.tnstc.buspass.Adapter.PassMonthWiseListAdapter;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.Others.MultiTextWatcher;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.TextWatcherWithInstance;
import com.tnstc.buspass.databinding.PassMonthlyWiseFragmentBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public class PassMonthlyWiseFragment extends Fragment implements AdapterView.OnItemClickListener {
    PassMonthlyWiseFragmentBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    BaseActivity mActivity;
    TnstcBusPassDB db;
    PassDao dao;
    List<String> getYear;
    List<String> getMonth;
    List<PassEntity> passEntityList;
    PassMonthWiseListAdapter mAdapter;
    String month;
    String year;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.pass_monthly_wise_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        mActivity.getSupportActionBar().hide();
        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.passDao();
        previousMonth();
        AdapterForMonthYear();
        mBinding.monthList.setOnItemClickListener(this);
        mBinding.yearList.setOnItemClickListener(this);

    }

    private void previousMonth() {
        mBinding.yearList.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        Calendar cal =  Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String previousMonthYear  = new SimpleDateFormat("MMMM").format(cal.getTime());
        mBinding.monthList.setText(previousMonthYear);
        passEntityList = new ArrayList<>();
        year = mBinding.yearList.getText().toString();
        month = mBinding.monthList.getText().toString();
        passEntityList = dao.getMonthWise(month, year);
        int monthWiseTotal = dao.monthWiseTotal(month,year);
        int monthWiseTotalScales = dao.getMonthWiseTotalSales(month,year);
        mBinding.txtMonthWiseTotAmount.setText(monthWiseTotal+"");
        mBinding.txtMonthWiseToalScalesAm.setText(monthWiseTotalScales+"");
        mAdapter = new PassMonthWiseListAdapter(passEntityList, getContext());
        mBinding.entryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.entryList.setAdapter(mAdapter);
    }

    private void AdapterForMonthYear() {
        getMonth = new ArrayList<>();
        getYear = new ArrayList<>();
        getYear = dao.getYear();
        getMonth = dao.getMonth();
        LinkedHashSet<String> monthSet = new LinkedHashSet<>();
        monthSet.addAll(getMonth);
        getMonth.clear();
        getMonth.addAll(monthSet);
        LinkedHashSet<String> yearSet = new LinkedHashSet<>();
        yearSet.addAll(getYear);
        getYear.clear();
        getYear.addAll(yearSet);
        ArrayAdapter yearList = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getYear);
        mBinding.yearList.setAdapter(yearList);
        ArrayAdapter monthList = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getMonth);
        mBinding.monthList.setAdapter(monthList);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity.getSupportActionBar().show();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        year = mBinding.yearList.getText().toString();
        month = mBinding.monthList.getText().toString();
        if (!year.isEmpty() && !month.isEmpty()) {
            passEntityList = new ArrayList<>();
            passEntityList = dao.getMonthWise(month, year);
            mAdapter = new PassMonthWiseListAdapter(passEntityList, getContext());
            mBinding.entryList.setLayoutManager(new LinearLayoutManager(getContext()));
            mBinding.entryList.setAdapter(mAdapter);
            int monthWiseTotal = dao.monthWiseTotal(month,year);
            int monthWiseTotalScales = dao.getMonthWiseTotalSales(month,year);
            mBinding.txtMonthWiseTotAmount.setText(monthWiseTotal+"");
            mBinding.txtMonthWiseToalScalesAm.setText(monthWiseTotalScales+"");
        }
    }
}
