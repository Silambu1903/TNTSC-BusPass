package com.tnstc.buspass.Fragment;

import android.content.Context;
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
import com.tnstc.buspass.databinding.AttendanceDesignFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceDesignFragment extends Fragment implements ItemClickListener {
    AttendanceDesignFragmentBinding mBinding;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.attendance_design_fragment,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mContext = getContext();
        activity = (BaseActivity) getActivity();
        db = TnstcBusPassDB.getDatabase(mContext);
        dutyDao = db.dutyDao();
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

    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos) {

    }

    @Override
    public void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout) {
        String month = (String) android.text.format.DateFormat.format("MMMM", new Date());
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        long dateMst = Long.parseLong((mAppClass.getCurrentDateDay()));

        if (Validation()) {
            String dutyDate = (currentDateAndDay.get(adapterPosition));

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

            List<DutyEntity> mEntity = new ArrayList<>();
            mEntity = dutyDao.getAllList();
            Boolean breakFor = false;
            if (mEntity.isEmpty()) {
                Log.e("Test1", "OnItemClickDate: -> EMPTY ");
                DutyEntity dutyEntity = new DutyEntity(dutyDate, Duty, month, year, dateMst);
                dutyEntityList = new ArrayList<>();
                dutyEntityList.add(dutyEntity);
                updateDutyDao(dutyEntityList);
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.yellow_boder));
            } else {
                for (int i = 0; i < mEntity.size(); i++) {
                    if (mEntity.get(i).getDutyDate().equals(dutyDate)) {
                        Log.e("Test1", "OnItemClickDate: -> IF ");
                        breakFor = true;
                        dutyDao.deleteDuty(dutyDate);
                        constraintLayout.setBackground(getResources().getDrawable(R.drawable.whiteboder));
                    } else {
                        if (!breakFor) {
                            breakFor = false;
                            Log.e("Test1", "OnItemClickDate: -> IF ELSE ");
                            DutyEntity dutyEntity = new DutyEntity(dutyDate, Duty, month, year, dateMst);
                            dutyEntityList = new ArrayList<>();
                            dutyEntityList.add(dutyEntity);
                            updateDutyDao(dutyEntityList);
                            constraintLayout.setBackground(getResources().getDrawable(R.drawable.yellow_boder));
                        }
                    }
                }
            }

        }
    }

    public void updateDutyDao(List<DutyEntity> entryList) {
        dutyDao.insertDuty(entryList.toArray(new DutyEntity[0]));
        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemDate(int adapterPosition, List<DutyEntity> dutyEntities) {

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
