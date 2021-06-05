package com.tnstc.buspass.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tnstc.buspass.Adapter.DutyAdapter;
import com.tnstc.buspass.Database.DAOs.DutyDao;
import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.DutyListFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;

public class DutyListFragment extends Fragment implements ItemClickListener, AdapterView.OnItemClickListener {

    DutyListFragmentBinding mBinding;
    DutyAdapter mAdapter;
    ApplicationClass mAppClass;
    List<DutyEntity> dutyEntityList;
    List<String> getYear;
    List<String> getMonth;
    DutyDao dao;
    TnstcBusPassDB db;
    String month;
    String year;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.duty_list_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        db = TnstcBusPassDB.getDatabase(getContext());
        dao = db.dutyDao();
        previousMonth();
        AdapterForMonthYear();
        mBinding.monthList.setOnItemClickListener(this);
        mBinding.yearList.setOnItemClickListener(this);
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

    private void previousMonth() {
        mBinding.yearList.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String previousMonthYear = new SimpleDateFormat("MMMM").format(cal.getTime());
        mBinding.monthList.setText(previousMonthYear);
        dutyEntityList = new ArrayList<>();
        year = mBinding.yearList.getText().toString();
        month = mBinding.monthList.getText().toString();
        dutyEntityList = dao.getMonthWise(month, year);
        mAdapter = new DutyAdapter(dutyEntityList, this);
        mBinding.dutyList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.dutyList.setAdapter(mAdapter);
    }

    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos, ConstraintLayout constraintLayout) {

    }

    @Override
    public void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout) {

    }

    @Override
    public void OnItemDate(int adapterPosition, List<DutyEntity> dutyEntities) {
        for (int i = 0; i < dutyEntities.size(); i++) {
            if (dutyEntities.get(i).getDutyDate().equals(dutyEntities.get(adapterPosition).getDutyDate())) {
                dao.deleteDuty(dutyEntities.get(adapterPosition).getDutyDate());
                dutyEntityList = dao.getMonthWise(month, year);
                mAdapter = new DutyAdapter(dutyEntityList, this);
                mBinding.dutyList.setLayoutManager(new LinearLayoutManager(getContext()));
                mBinding.dutyList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mAppClass.showSnackBar(getContext(), "Deleted Successfully");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        year = mBinding.yearList.getText().toString();
        month = mBinding.monthList.getText().toString();
        if (!year.isEmpty() && !month.isEmpty()) {
            dutyEntityList = new ArrayList<>();
            dutyEntityList = dao.getMonthWise(month, year);
            mAdapter = new DutyAdapter(dutyEntityList, this);
            mBinding.dutyList.setLayoutManager(new LinearLayoutManager(getContext()));
            mBinding.dutyList.setAdapter(mAdapter);

        }

    }
}
