package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tnstc.buspass.Adapter.MstDailyWiseAdapter;
import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.MstListFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MstListFragment extends Fragment implements ItemClickListener {
    MstListFragmentBinding mBinding;
    TnstcBusPassDB db;
    MstDao mstDao;
    Context mContext;
    List<MstEntity> mstEntityList;
    MstDailyWiseAdapter mstDailyWiseAdapter;
    ApplicationClass mAppClass;
    int value = 0;
    View viewMinus;
    View viewPlus;
    TextView currentDateMst;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.mst_list_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mst_menu, menu);
        MenuItem actionViewItem = menu.findItem(R.id.mst_menu);
        actionViewItem.setActionView(R.layout.date_plus_minus);
        View v = MenuItemCompat.getActionView(actionViewItem);
        viewMinus = v.findViewById(R.id.minus);
        viewPlus = v.findViewById(R.id.plus);
        currentDateMst = v.findViewById(R.id.currentdate);
        currentDateMst.setText(mAppClass.getCurrentDateTime());
        viewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value--;
                previousNextDay(value);
            }
        });
        viewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                previousNextDay(value);
            }
        });

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContext = getContext();
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mstEntityList = new ArrayList<>();
        db = TnstcBusPassDB.getDatabase(mContext);
        mstDao = db.mstDao();
        mstEntityList = mstDao.currentDate(mAppClass.getCurrentDateTime());
        mBinding.TotalSalesValue.setText(mstDao.totalSalesCard(mAppClass.getCurrentDateTime(), mAppClass.getCurrentDateTime()) + "");
        mBinding.totalMstAmount.setText(mstDao.mstTotalAmount(mAppClass.getCurrentDateTime(), mAppClass.getCurrentDateTime()) + "");
        mstDailyWiseAdapter = new MstDailyWiseAdapter(mstEntityList, getContext(), this);
        mBinding.mstListRev.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.mstListRev.setAdapter(mstDailyWiseAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }


    private void previousNextDay(int number) {
        String currentDate = mAppClass.getCurrentDateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, number);  // number of days to add
        currentDate = sdf.format(c.getTime());  // dt is now the new date
        currentDateMst.setText(currentDate);
        mstEntityList = new ArrayList<>();
        mstEntityList = mstDao.currentDate(currentDate);
        mstDailyWiseAdapter = new MstDailyWiseAdapter(mstEntityList, getContext(), this);
        mBinding.mstListRev.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.mstListRev.setAdapter(mstDailyWiseAdapter);
        mBinding.TotalSalesValue.setText(mstDao.totalSalesCard(currentDate, currentDate) + "");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos) {

    }

    @Override
    public void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout) {

    }

    @Override
    public void OnItemDate(int adapterPosition, List<DutyEntity> dutyEntities) {

    }
}
