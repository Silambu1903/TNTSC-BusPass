package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.DAOs.MstOpeningDao;
import com.tnstc.buspass.Database.DAOs.SctDao;
import com.tnstc.buspass.Database.DAOs.SctOpeningDao;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.Entity.SctEntity;
import com.tnstc.buspass.Database.Entity.SctOpeningClosing;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.SctFragmentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SctFragment extends Fragment {
    SctFragmentBinding mBinding;
    private SharedPreferences preferences;
    Context mContext;
    ApplicationClass mAppClass;
    TnstcBusPassDB db;
    SctDao dao;
    SctOpeningDao sctOpeningDao;

    public int spare100, spare120, spare140, spare160, spare180, spare200, spare220, spare240,
            spare260, spare280, spare300, spare320, spare340, spare360, spare380, spare400;

    public String key100, key120, key140, key160, key180, key200, key220, key240,
            key260, key280, key300, key320, key340, key360, key380, key400;

    int balOpen100, balClose100, TotalBalCard100, balCard100, balCurrentOpen100, maxMonthOpenCard100,
            balOpen120, balClose120, TotalBalCard120, balCard120, balCurrentOpen120, maxMonthOpenCard120,
            balOpen140, balClose140, TotalBalCard140, balCard140, balCurrentOpen140, maxMonthOpenCard140,
            balOpen160, balClose160, TotalBalCard160, balCard160, balCurrentOpen160, maxMonthOpenCard160,
            balOpen180, balClose180, TotalBalCard180, balCard180, balCurrentOpen180, maxMonthOpenCard180,
            balOpen200, balClose200, TotalBalCard200, balCard200, balCurrentOpen200, maxMonthOpenCard200,
            balOpen220, balClose220, TotalBalCard220, balCard220, balCurrentOpen220, maxMonthOpenCard220,
            balOpen240, balClose240, TotalBalCard240, balCard240, balCurrentOpen240, maxMonthOpenCard240,
            balOpen260, balClose260, TotalBalCard260, balCard260, balCurrentOpen260, maxMonthOpenCard260,
            balOpen280, balClose280, TotalBalCard280, balCard280, balCurrentOpen280, maxMonthOpenCard280,
            balOpen300, balClose300, TotalBalCard300, balCard300, balCurrentOpen300, maxMonthOpenCard300,
            balOpen320, balClose320, TotalBalCard320, balCard320, balCurrentOpen320, maxMonthOpenCard320,
            balOpen340, balClose340, TotalBalCard340, balCard340, balCurrentOpen340, maxMonthOpenCard340,
            balOpen360, balClose360, TotalBalCard360, balCard360, balCurrentOpen360, maxMonthOpenCard360,
            balOpen380, balClose380, TotalBalCard380, balCard380, balCurrentOpen380, maxMonthOpenCard380,
            balOpen400, balClose400, TotalBalCard400, balCard400, balCurrentOpen400, maxMonthOpenCard400;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.sct_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mBinding.monthMst.setText((String) android.text.format.DateFormat.format("MMMM", new Date()));
        mBinding.yearMst.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        mBinding.date.setText(mAppClass.getCurrentDateTime());
        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.sctDao();
        sctOpeningDao = db.sctOpeningDao();
        mBinding.mstSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.monthlyDataRadio.isChecked()) {
                    if (validation()) {
                        sctMonthEntry();
                    }

                } else {
                    if (validation()) {
                        sctEntry();
                    }
                }

            }
        });

    }

    private void sctMonthEntry() {
        long Date = Long.valueOf(new SimpleDateFormat("ddMMyyyy").format(new Date()));
        String monthMst = mBinding.monthMst.getText().toString();
        String yearMst = mBinding.yearMst.getText().toString();
        String dateMst = mAppClass.getCurrentDateTime();
        spare100 = preferences.getInt("sctSpare100", 0);
        spare120 = preferences.getInt("sctSpare120", 0);
        spare140 = preferences.getInt("sctSpare140", 0);
        spare160 = preferences.getInt("sctSpare160", 0);
        spare180 = preferences.getInt("sctSpare180", 0);
        spare200 = preferences.getInt("sctSpare200", 0);
        spare220 = preferences.getInt("sctSpare220", 0);
        spare240 = preferences.getInt("sctSpare240", 0);
        spare260 = preferences.getInt("sctSpare260", 0);
        spare280 = preferences.getInt("sctSpare280", 0);
        spare300 = preferences.getInt("sctSpare300", 0);
        spare320 = preferences.getInt("sctSpare320", 0);
        spare340 = preferences.getInt("sctSpare340", 0);
        spare360 = preferences.getInt("sctSpare360", 0);
        spare380 = preferences.getInt("sctSpare380", 0);
        spare400 = preferences.getInt("sctSpare400", 0);

        key100 = preferences.getString("sctKey100", "");
        key120 = preferences.getString("sctKey120", "");
        key140 = preferences.getString("sctKey140", "");
        key160 = preferences.getString("sctKey160", "");
        key180 = preferences.getString("sctKey180", "");
        key200 = preferences.getString("sctKey200", "");
        key220 = preferences.getString("sctKey220", "");
        key240 = preferences.getString("sctKey240", "");
        key260 = preferences.getString("sctKey260", "");
        key280 = preferences.getString("sctKey280", "");
        key300 = preferences.getString("sctKey300", "");
        key320 = preferences.getString("sctKey320", "");
        key340 = preferences.getString("sctKey340", "");
        key360 = preferences.getString("sctKey360", "");
        key380 = preferences.getString("sctKey380", "");
        key400 = preferences.getString("sctKey400", "");

        //card100
        int TotalCard100 = Integer.parseInt(mBinding.teiClosing100.getText().toString()) - Integer.parseInt(mBinding.teiOpening100.getText().toString());
        if (TotalCard100 == 0) {
            TotalCard100 = 1;
        } else {
            TotalCard100 = Integer.parseInt(mBinding.teiClosing100.getText().toString()) - Integer.parseInt(mBinding.teiOpening100.getText().toString()) + 1;
        }
        if (mBinding.teiClosing100.getText().toString().equals("0") && mBinding.teiOpening100.getText().toString().equals("0")) {
            TotalCard100 = 0;
        }
        int maxOpen100 = dao.sctMonthOpen200(monthMst, 100);
        int maxClose100 = dao.sctMonthClose200(monthMst, 100);
        int maxTotalCard100 = dao.sctMonthTotalCard200(monthMst, 100);
        int maxTotalSales100 = dao.sctMonthTotalAmount200(monthMst, 100);
        if (dao.sctMonthOpen200(monthMst, 100) != 0) {
            balOpen100 = dao.sctMonthCloseMax200(monthMst, 100) + 1;
            balClose100 = sctOpeningDao.sctMonthBalClose200(monthMst, 100);
            maxMonthOpenCard100 = dao.sctMonthCloseMax200(monthMst, 100);
            balCard100 = sctOpeningDao.sctMonthBalClose200(monthMst, 100) - maxMonthOpenCard100;
            TotalBalCard100 = dao.sctMonthTotalCard200(monthMst, 100) + balCard100;
        }

        //card120
        int TotalCard120 = Integer.parseInt(mBinding.teiClosing120.getText().toString()) - Integer.parseInt(mBinding.teiOpening120.getText().toString());
        if (TotalCard120 == 0) {
            TotalCard120 = 1;
        } else {
            TotalCard120 = Integer.parseInt(mBinding.teiClosing120.getText().toString()) - Integer.parseInt(mBinding.teiOpening120.getText().toString()) + 1;
        }
        if (mBinding.teiClosing120.getText().toString().equals("0") && mBinding.teiOpening120.getText().toString().equals("0")) {
            TotalCard120 = 0;
        }
        int maxOpen120 = dao.sctMonthOpen200(monthMst, 120);
        int maxClose120 = dao.sctMonthClose200(monthMst, 120);
        int maxTotalCard120 = dao.sctMonthTotalCard200(monthMst, 120);
        int maxTotalSales120 = dao.sctMonthTotalAmount200(monthMst, 120);
        if (dao.sctMonthOpen200(monthMst, 120) != 0) {
            balOpen120 = dao.sctMonthCloseMax200(monthMst, 120) + 1;
            balClose120 = sctOpeningDao.sctMonthBalClose200(monthMst, 120);
            maxMonthOpenCard120 = dao.sctMonthCloseMax200(monthMst, 120);
            balCard120 = sctOpeningDao.sctMonthBalClose200(monthMst, 120) - maxMonthOpenCard120;
            TotalBalCard120 = dao.sctMonthTotalCard200(monthMst, 120) + balCard120;
        }

        //card140
        int TotalCard140 = Integer.parseInt(mBinding.teiClosing140.getText().toString()) - Integer.parseInt(mBinding.teiOpening140.getText().toString());
        if (TotalCard140 == 0) {
            TotalCard140 = 1;
        } else {
            TotalCard140 = Integer.parseInt(mBinding.teiClosing140.getText().toString()) - Integer.parseInt(mBinding.teiOpening140.getText().toString()) + 1;
        }
        if (mBinding.teiClosing140.getText().toString().equals("0") && mBinding.teiOpening140.getText().toString().equals("0")) {
            TotalCard140 = 0;
        }
        int maxOpen140 = dao.sctMonthOpen200(monthMst, 140);
        int maxClose140 = dao.sctMonthClose200(monthMst, 140);
        int maxTotalCard140 = dao.sctMonthTotalCard200(monthMst, 140);
        int maxTotalSales140 = dao.sctMonthTotalAmount200(monthMst, 140);
        if (dao.sctMonthOpen200(monthMst, 140) != 0) {
            balOpen140 = dao.sctMonthCloseMax200(monthMst, 140) + 1;
            balClose140 = sctOpeningDao.sctMonthBalClose200(monthMst, 140);
            maxMonthOpenCard140 = dao.sctMonthCloseMax200(monthMst, 140);
            balCard140 = sctOpeningDao.sctMonthBalClose200(monthMst, 140) - maxMonthOpenCard140;
            TotalBalCard140 = dao.sctMonthTotalCard200(monthMst, 140) + balCard140;
        }
        //card160
        int TotalCard160 = Integer.parseInt(mBinding.teiClosing160.getText().toString()) - Integer.parseInt(mBinding.teiOpening160.getText().toString());
        if (TotalCard160 == 0) {
            TotalCard160 = 1;
        } else {
            TotalCard160 = Integer.parseInt(mBinding.teiClosing160.getText().toString()) - Integer.parseInt(mBinding.teiOpening160.getText().toString()) + 1;
        }
        if (mBinding.teiClosing160.getText().toString().equals("0") && mBinding.teiOpening160.getText().toString().equals("0")) {
            TotalCard160 = 0;
        }
        int maxOpen160 = dao.sctMonthOpen200(monthMst, 160);
        int maxClose160 = dao.sctMonthClose200(monthMst, 160);
        int maxTotalCard160 = dao.sctMonthTotalCard200(monthMst, 160);
        int maxTotalSales160 = dao.sctMonthTotalAmount200(monthMst, 160);
        if (dao.sctMonthOpen200(monthMst, 160) != 0) {
            balOpen160 = dao.sctMonthCloseMax200(monthMst, 160) + 1;
            balClose160 = sctOpeningDao.sctMonthBalClose200(monthMst, 160);
            maxMonthOpenCard160 = dao.sctMonthCloseMax200(monthMst, 160);
            balCard160 = sctOpeningDao.sctMonthBalClose200(monthMst, 160) - maxMonthOpenCard160;
            TotalBalCard160 = dao.sctMonthTotalCard200(monthMst, 160) + balCard160;
        }
        //card180
        int TotalCard180 = Integer.parseInt(mBinding.teiClosing180.getText().toString()) - Integer.parseInt(mBinding.teiOpening180.getText().toString());
        if (TotalCard180 == 0) {
            TotalCard180 = 1;
        } else {
            TotalCard180 = Integer.parseInt(mBinding.teiClosing180.getText().toString()) - Integer.parseInt(mBinding.teiOpening180.getText().toString()) + 1;
        }
        if (mBinding.teiClosing180.getText().toString().equals("0") && mBinding.teiOpening360.getText().toString().equals("0")) {
            TotalCard180 = 0;
        }
        int maxOpen180 = dao.sctMonthOpen200(monthMst, 180);
        int maxClose180 = dao.sctMonthClose200(monthMst, 180);
        int maxTotalCard180 = dao.sctMonthTotalCard200(monthMst, 180);
        int maxTotalSales180 = dao.sctMonthTotalAmount200(monthMst, 180);
        if (dao.sctMonthOpen200(monthMst, 180) != 0) {
            balOpen180 = dao.sctMonthCloseMax200(monthMst, 180) + 1;
            balClose180 = sctOpeningDao.sctMonthBalClose200(monthMst, 180);
            maxMonthOpenCard180 = dao.sctMonthCloseMax200(monthMst, 180);
            balCard180 = sctOpeningDao.sctMonthBalClose200(monthMst, 180) - maxMonthOpenCard180;
            TotalBalCard180 = dao.sctMonthTotalCard200(monthMst, 180) + balCard180;
        }
        //card200
        int TotalCard200 = Integer.parseInt(mBinding.teiClosing200.getText().toString()) - Integer.parseInt(mBinding.teiOpening200.getText().toString());
        if (TotalCard200 == 0) {
            TotalCard200 = 1;
        } else {
            TotalCard200 = Integer.parseInt(mBinding.teiClosing200.getText().toString()) - Integer.parseInt(mBinding.teiOpening200.getText().toString()) + 1;
        }
        if (mBinding.teiClosing200.getText().toString().equals("0") && mBinding.teiOpening200.getText().toString().equals("0")) {
            TotalCard200 = 0;
        }
        int maxOpen200 = dao.sctMonthOpen200(monthMst, 200);
        int maxClose200 = dao.sctMonthClose200(monthMst, 200);
        int maxTotalCard200 = dao.sctMonthTotalCard200(monthMst, 200);
        int maxTotalSales200 = dao.sctMonthTotalAmount200(monthMst, 200);
        if (dao.sctMonthOpen200(monthMst, 200) != 0) {
            balOpen200 = dao.sctMonthCloseMax200(monthMst, 200) + 1;
            balClose200 = sctOpeningDao.sctMonthBalClose200(monthMst, 200);
            maxMonthOpenCard200 = dao.sctMonthCloseMax200(monthMst, 200);
            balCard200 = sctOpeningDao.sctMonthBalClose200(monthMst, 200) - maxMonthOpenCard200;
            TotalBalCard200 = dao.sctMonthTotalCard200(monthMst, 200) + balCard200;
        }
        //card220
        int TotalCard220 = Integer.parseInt(mBinding.teiClosing220.getText().toString()) - Integer.parseInt(mBinding.teiOpening220.getText().toString());
        if (TotalCard220 == 0) {
            TotalCard220 = 1;
        } else {
            TotalCard220 = Integer.parseInt(mBinding.teiClosing220.getText().toString()) - Integer.parseInt(mBinding.teiOpening220.getText().toString()) + 1;
        }
        if (mBinding.teiClosing220.getText().toString().equals("0") && mBinding.teiOpening220.getText().toString().equals("0")) {
            TotalCard220 = 0;
        }
        int maxOpen220 = dao.sctMonthOpen200(monthMst, 220);
        int maxClose220 = dao.sctMonthClose200(monthMst, 220);
        int maxTotalCard220 = dao.sctMonthTotalCard200(monthMst, 220);
        int maxTotalSales220 = dao.sctMonthTotalAmount200(monthMst, 220);
        if (dao.sctMonthOpen200(monthMst, 220) != 0) {
            balOpen220 = dao.sctMonthCloseMax200(monthMst, 220) + 1;
            balClose220 = sctOpeningDao.sctMonthBalClose200(monthMst, 220);
            maxMonthOpenCard220 = dao.sctMonthCloseMax200(monthMst, 220);
            balCard220 = sctOpeningDao.sctMonthBalClose200(monthMst, 220) - maxMonthOpenCard220;
            TotalBalCard220 = dao.sctMonthTotalCard200(monthMst, 220) + balCard220;
        }
        //card240
        int TotalCard240 = Integer.parseInt(mBinding.teiClosing240.getText().toString()) - Integer.parseInt(mBinding.teiOpening240.getText().toString());
        if (TotalCard240 == 0) {
            TotalCard240 = 1;
        } else {
            TotalCard240 = Integer.parseInt(mBinding.teiClosing240.getText().toString()) - Integer.parseInt(mBinding.teiOpening240.getText().toString()) + 1;
        }
        if (mBinding.teiClosing240.getText().toString().equals("0") && mBinding.teiOpening240.getText().toString().equals("0")) {
            TotalCard240 = 0;
        }
        int maxOpen240 = dao.sctMonthOpen200(monthMst, 240);
        int maxClose240 = dao.sctMonthClose200(monthMst, 240);
        int maxTotalCard240 = dao.sctMonthTotalCard200(monthMst, 240);
        int maxTotalSales240 = dao.sctMonthTotalAmount200(monthMst, 240);
        if (dao.sctMonthOpen200(monthMst, 240) != 0) {
            balOpen240 = dao.sctMonthCloseMax200(monthMst, 240) + 1;
            balClose240 = sctOpeningDao.sctMonthBalClose200(monthMst, 240);
            maxMonthOpenCard240 = dao.sctMonthCloseMax200(monthMst, 240);
            balCard240 = sctOpeningDao.sctMonthBalClose200(monthMst, 240) - maxMonthOpenCard240;
            TotalBalCard240 = dao.sctMonthTotalCard200(monthMst, 240) + balCard240;
        }

        //card260
        int TotalCard260 = Integer.parseInt(mBinding.teiClosing260.getText().toString()) - Integer.parseInt(mBinding.teiOpening260.getText().toString());
        if (TotalCard260 == 0) {
            TotalCard260 = 1;
        } else {
            TotalCard260 = Integer.parseInt(mBinding.teiClosing260.getText().toString()) - Integer.parseInt(mBinding.teiOpening260.getText().toString()) + 1;
        }
        if (mBinding.teiClosing260.getText().toString().equals("0") && mBinding.teiOpening260.getText().toString().equals("0")) {
            TotalCard260 = 0;
        }
        int maxOpen260 = dao.sctMonthOpen200(monthMst, 260);
        int maxClose260 = dao.sctMonthClose200(monthMst, 260);
        int maxTotalCard260 = dao.sctMonthTotalCard200(monthMst, 260);
        int maxTotalSales260 = dao.sctMonthTotalAmount200(monthMst, 260);
        if (dao.sctMonthOpen200(monthMst, 260) != 0) {
            balOpen260 = dao.sctMonthCloseMax200(monthMst, 260) + 1;
            balClose260 = sctOpeningDao.sctMonthBalClose200(monthMst, 260);
            maxMonthOpenCard260 = dao.sctMonthCloseMax200(monthMst, 260);
            balCard260 = sctOpeningDao.sctMonthBalClose200(monthMst, 260) - maxMonthOpenCard260;
            TotalBalCard260 = dao.sctMonthTotalCard200(monthMst, 260) + balCard260;
        }
        //card280
        int TotalCard280 = Integer.parseInt(mBinding.teiClosing280.getText().toString()) - Integer.parseInt(mBinding.teiOpening280.getText().toString());
        if (TotalCard280 == 0) {
            TotalCard280 = 1;
        } else {
            TotalCard280 = Integer.parseInt(mBinding.teiClosing280.getText().toString()) - Integer.parseInt(mBinding.teiOpening280.getText().toString()) + 1;
        }
        if (mBinding.teiClosing280.getText().toString().equals("0") && mBinding.teiOpening280.getText().toString().equals("0")) {
            TotalCard280 = 0;
        }
        int maxOpen280 = dao.sctMonthOpen200(monthMst, 280);
        int maxClose280 = dao.sctMonthClose200(monthMst, 280);
        int maxTotalCard280 = dao.sctMonthTotalCard200(monthMst, 280);
        int maxTotalSales280 = dao.sctMonthTotalAmount200(monthMst, 280);
        if (dao.sctMonthOpen200(monthMst, 280) != 0) {
            balOpen280 = dao.sctMonthCloseMax200(monthMst, 280) + 1;
            balClose280 = sctOpeningDao.sctMonthBalClose200(monthMst, 280);
            maxMonthOpenCard280 = dao.sctMonthCloseMax200(monthMst, 280);
            balCard280 = sctOpeningDao.sctMonthBalClose200(monthMst, 280) - maxMonthOpenCard280;
            TotalBalCard280 = dao.sctMonthTotalCard200(monthMst, 280) + balCard280;
        }
        //card300
        int TotalCard300 = Integer.parseInt(mBinding.teiClosing300.getText().toString()) - Integer.parseInt(mBinding.teiOpening300.getText().toString());
        if (TotalCard300 == 0) {
            TotalCard300 = 1;
        } else {
            TotalCard300 = Integer.parseInt(mBinding.teiClosing300.getText().toString()) - Integer.parseInt(mBinding.teiOpening300.getText().toString()) + 1;
        }
        if (mBinding.teiClosing300.getText().toString().equals("0") && mBinding.teiOpening300.getText().toString().equals("0")) {
            TotalCard300 = 0;
        }
        int maxOpen300 = dao.sctMonthOpen200(monthMst, 300);
        int maxClose300 = dao.sctMonthClose200(monthMst, 300);
        int maxTotalCard300 = dao.sctMonthTotalCard200(monthMst, 300);
        int maxTotalSales300 = dao.sctMonthTotalAmount200(monthMst, 300);
        if (dao.sctMonthOpen200(monthMst, 300) != 0) {
            balOpen300 = dao.sctMonthCloseMax200(monthMst, 300) + 1;
            balClose300 = sctOpeningDao.sctMonthBalClose200(monthMst, 300);
            maxMonthOpenCard300 = dao.sctMonthCloseMax200(monthMst, 300);
            balCard300 = sctOpeningDao.sctMonthBalClose200(monthMst, 300) - maxMonthOpenCard300;
            TotalBalCard300 = dao.sctMonthTotalCard200(monthMst, 300) + balCard300;
        }
        //card320
        int TotalCard320 = Integer.parseInt(mBinding.teiClosing320.getText().toString()) - Integer.parseInt(mBinding.teiOpening320.getText().toString());
        if (TotalCard320 == 0) {
            TotalCard320 = 1;
        } else {
            TotalCard320 = Integer.parseInt(mBinding.teiClosing320.getText().toString()) - Integer.parseInt(mBinding.teiOpening320.getText().toString()) + 1;
        }
        if (mBinding.teiClosing320.getText().toString().equals("0") && mBinding.teiOpening320.getText().toString().equals("0")) {
            TotalCard320 = 0;
        }
        int maxOpen320 = dao.sctMonthOpen200(monthMst, 320);
        int maxClose320 = dao.sctMonthClose200(monthMst, 320);
        int maxTotalCard320 = dao.sctMonthTotalCard200(monthMst, 320);
        int maxTotalSales320 = dao.sctMonthTotalAmount200(monthMst, 320);
        if (dao.sctMonthOpen200(monthMst, 320) != 0) {
            balOpen320 = dao.sctMonthCloseMax200(monthMst, 320) + 1;
            balClose320 = sctOpeningDao.sctMonthBalClose200(monthMst, 320);
            maxMonthOpenCard320 = dao.sctMonthCloseMax200(monthMst, 320);
            balCard320 = sctOpeningDao.sctMonthBalClose200(monthMst, 320) - maxMonthOpenCard320;
            TotalBalCard320 = dao.sctMonthTotalCard200(monthMst, 320) + balCard320;
        }
        //340
        int TotalCard340 = Integer.parseInt(mBinding.teiClosing340.getText().toString()) - Integer.parseInt(mBinding.teiOpening340.getText().toString());
        if (TotalCard340 == 0) {
            TotalCard340 = 1;
        } else {
            TotalCard340 = Integer.parseInt(mBinding.teiClosing340.getText().toString()) - Integer.parseInt(mBinding.teiOpening340.getText().toString()) + 1;

        }
        if (mBinding.teiClosing340.getText().toString().equals("0") && mBinding.teiOpening340.getText().toString().equals("0")) {
            TotalCard340 = 0;
        }
        int maxOpen340 = dao.sctMonthOpen200(monthMst, 340);
        int maxClose340 = dao.sctMonthClose200(monthMst, 340);
        int maxTotalCard340 = dao.sctMonthTotalCard200(monthMst, 340);
        int maxTotalSales340 = dao.sctMonthTotalAmount200(monthMst, 340);
        if (dao.sctMonthOpen200(monthMst, 340) != 0) {
            balOpen340 = dao.sctMonthCloseMax200(monthMst, 340) + 1;
            balClose340 = sctOpeningDao.sctMonthBalClose200(monthMst, 340);
            maxMonthOpenCard340 = dao.sctMonthCloseMax200(monthMst, 340);
            balCard340 = sctOpeningDao.sctMonthBalClose200(monthMst, 340) - maxMonthOpenCard340;
            TotalBalCard340 = dao.sctMonthTotalCard200(monthMst, 340) + balCard340;
        }
      //360
        int TotalCard360 = Integer.parseInt(mBinding.teiClosing360.getText().toString()) - Integer.parseInt(mBinding.teiOpening360.getText().toString());
        if (TotalCard360 == 0) {
            TotalCard360 = 1;
        } else {
            TotalCard360 = Integer.parseInt(mBinding.teiClosing360.getText().toString()) - Integer.parseInt(mBinding.teiOpening360.getText().toString()) + 1;
        }
        if (mBinding.teiClosing360.getText().toString().equals("0") && mBinding.teiOpening360.getText().toString().equals("0")) {
            TotalCard360 = 0;
        }
        int maxOpen360 = dao.sctMonthOpen200(monthMst, 360);
        int maxClose360 = dao.sctMonthClose200(monthMst, 360);
        int maxTotalCard360 = dao.sctMonthTotalCard200(monthMst, 360);
        int maxTotalSales360 = dao.sctMonthTotalAmount200(monthMst, 360);
        if (dao.sctMonthOpen200(monthMst, 360) != 0) {
            balOpen360 = dao.sctMonthCloseMax200(monthMst, 360) + 1;
            balClose360 = sctOpeningDao.sctMonthBalClose200(monthMst, 360);
            maxMonthOpenCard360 = dao.sctMonthCloseMax200(monthMst, 360);
            balCard360 = sctOpeningDao.sctMonthBalClose200(monthMst, 360) - maxMonthOpenCard360;
            TotalBalCard360 = dao.sctMonthTotalCard200(monthMst, 360) + balCard360;
        }
        //380
        int TotalCard380 = Integer.parseInt(mBinding.teiClosing380.getText().toString()) - Integer.parseInt(mBinding.teiOpening380.getText().toString());
        if (TotalCard380 == 0) {
            TotalCard380 = 1;
        } else {
            TotalCard380 = Integer.parseInt(mBinding.teiClosing380.getText().toString()) - Integer.parseInt(mBinding.teiOpening380.getText().toString()) + 1;
        }
        if (mBinding.teiClosing380.getText().toString().equals("0") && mBinding.teiOpening360.getText().toString().equals("0")) {
            TotalCard380 = 0;
        }
        int maxOpen380 = dao.sctMonthOpen200(monthMst, 380);
        int maxClose380 = dao.sctMonthClose200(monthMst, 380);
        int maxTotalCard380 = dao.sctMonthTotalCard200(monthMst, 380);
        int maxTotalSales380 = dao.sctMonthTotalAmount200(monthMst, 380);
        if (dao.sctMonthOpen200(monthMst, 380) != 0) {
            balOpen380 = dao.sctMonthCloseMax200(monthMst, 380) + 1;
            balClose380 = sctOpeningDao.sctMonthBalClose200(monthMst, 380);
            maxMonthOpenCard380 = dao.sctMonthCloseMax200(monthMst, 380);
            balCard380 = sctOpeningDao.sctMonthBalClose200(monthMst, 380) - maxMonthOpenCard380;
            TotalBalCard380 = dao.sctMonthTotalCard200(monthMst, 380) + balCard380;
        }
       //400
        int TotalCard400 = Integer.parseInt(mBinding.teiClosing400.getText().toString()) - Integer.parseInt(mBinding.teiOpening400.getText().toString());
        if (TotalCard400 == 0) {
            TotalCard400 = 1;
        } else {
            TotalCard400 = Integer.parseInt(mBinding.teiClosing400.getText().toString()) - Integer.parseInt(mBinding.teiOpening400.getText().toString()) + 1;
        }
        if (mBinding.teiClosing400.getText().toString().equals("0") && mBinding.teiOpening400.getText().toString().equals("0")) {
            TotalCard400 = 0;
        }
        int maxOpen400 = dao.sctMonthOpen200(monthMst, 400);
        int maxClose400 = dao.sctMonthClose200(monthMst, 400);
        int maxTotalCard400 = dao.sctMonthTotalCard200(monthMst, 400);
        int maxTotalSales400 = dao.sctMonthTotalAmount200(monthMst, 400);
        if (dao.sctMonthOpen200(monthMst, 400) != 0) {
            balOpen400 = dao.sctMonthCloseMax200(monthMst, 400) + 1;
            balClose400 = sctOpeningDao.sctMonthBalClose200(monthMst, 400);
            maxMonthOpenCard400 = dao.sctMonthCloseMax200(monthMst, 400);
            balCard400 = sctOpeningDao.sctMonthBalClose200(monthMst, 400) - maxMonthOpenCard400;
            TotalBalCard400 = dao.sctMonthTotalCard200(monthMst, 400) + balCard400;
        }

        SctOpeningClosing SctOpeningClosing100 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening100.getText().toString()), Integer.parseInt(mBinding.teiClosing100.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue100.getText().toString()), spare100, key100, TotalCard100, monthMst, yearMst, maxOpen100, maxClose100,
                maxTotalCard100, maxTotalSales100, balOpen100, balClose100, balCard100, TotalBalCard100);

        SctOpeningClosing SctOpeningClosing120 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening120.getText().toString()), Integer.parseInt(mBinding.teiClosing120.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue120.getText().toString()), spare120, key120, TotalCard120, monthMst, yearMst, maxOpen120, maxClose120,
                maxTotalCard120, maxTotalSales120, balOpen120, balClose120, balCard120, TotalBalCard120);

        SctOpeningClosing SctOpeningClosing140 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening140.getText().toString()), Integer.parseInt(mBinding.teiClosing140.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue140.getText().toString()), spare140, key140, TotalCard140, monthMst, yearMst, maxOpen140, maxClose140,
                maxTotalCard140, maxTotalSales140, balOpen140, balClose140, balCard140, TotalBalCard140);

        SctOpeningClosing SctOpeningClosing160 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening160.getText().toString()), Integer.parseInt(mBinding.teiClosing160.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue160.getText().toString()), spare160, key160, TotalCard160, monthMst, yearMst, maxOpen160, maxClose160,
                maxTotalCard160, maxTotalSales160, balOpen160, balClose320, balCard160, TotalBalCard160);

        SctOpeningClosing SctOpeningClosing180 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening180.getText().toString()), Integer.parseInt(mBinding.teiClosing180.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue180.getText().toString()), spare180, key180, TotalCard180, monthMst, yearMst, maxOpen180, maxClose180,
                maxTotalCard180, maxTotalSales180, balOpen180, balClose180, balCard180, TotalBalCard180);

        SctOpeningClosing SctOpeningClosing200 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening200.getText().toString()), Integer.parseInt(mBinding.teiClosing200.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue200.getText().toString()), spare200, key200, TotalCard200, monthMst, yearMst, maxOpen200, maxClose200,
                maxTotalCard200, maxTotalSales200, balOpen200, balClose200, balCard200, TotalBalCard200);

        SctOpeningClosing SctOpeningClosing220 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening220.getText().toString()), Integer.parseInt(mBinding.teiClosing220.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue220.getText().toString()), spare220, key220, TotalCard220, monthMst, yearMst, maxOpen220, maxClose220,
                maxTotalCard220, maxTotalSales220, balOpen220, balClose220, balCard220, TotalBalCard220);

        SctOpeningClosing SctOpeningClosing240 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening240.getText().toString()), Integer.parseInt(mBinding.teiClosing240.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue240.getText().toString()), spare240, key240, TotalCard240, monthMst, yearMst, maxOpen240, maxClose240,
                maxTotalCard240, maxTotalSales240, balOpen240, balClose240, balCard240, TotalBalCard240);

        SctOpeningClosing SctOpeningClosing260 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening260.getText().toString()), Integer.parseInt(mBinding.teiClosing260.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue260.getText().toString()), spare260, key260, TotalCard260, monthMst, yearMst, maxOpen260, maxClose260,
                maxTotalCard260, maxTotalSales260, balOpen260, balClose260, balCard260, TotalBalCard260);

        SctOpeningClosing SctOpeningClosing280 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening280.getText().toString()), Integer.parseInt(mBinding.teiClosing280.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue280.getText().toString()), spare280, key280, TotalCard280, monthMst, yearMst, maxOpen280, maxClose280,
                maxTotalCard280, maxTotalSales280, balOpen280, balClose280, balCard280, TotalBalCard280);

        SctOpeningClosing SctOpeningClosing300 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening300.getText().toString()), Integer.parseInt(mBinding.teiClosing300.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue300.getText().toString()), spare300, key300, TotalCard300, monthMst, yearMst, maxOpen300, maxClose300,
                maxTotalCard300, maxTotalSales300, balOpen300, balClose300, balCard300, TotalBalCard300);

        SctOpeningClosing SctOpeningClosing320 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening320.getText().toString()), Integer.parseInt(mBinding.teiClosing320.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue320.getText().toString()), spare320, key320, TotalCard320, monthMst, yearMst, maxOpen320, maxClose320,
                maxTotalCard320, maxTotalSales320, balOpen320, balClose320, balCard320, TotalBalCard320);

        SctOpeningClosing SctOpeningClosing340 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening340.getText().toString()), Integer.parseInt(mBinding.teiClosing340.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue340.getText().toString()), spare340, key340, TotalCard340, monthMst, yearMst, maxOpen340, maxClose340,
                maxTotalCard340, maxTotalSales340, balOpen340, balClose340, balCard340, TotalBalCard340);

        SctOpeningClosing SctOpeningClosing360 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening360.getText().toString()), Integer.parseInt(mBinding.teiClosing360.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue360.getText().toString()), spare360, key360, TotalCard360, monthMst, yearMst, maxOpen360, maxClose360,
                maxTotalCard360, maxTotalSales360, balOpen360, balClose360, balCard360, TotalBalCard360);

        SctOpeningClosing SctOpeningClosing380 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening380.getText().toString()), Integer.parseInt(mBinding.teiClosing380.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue380.getText().toString()), spare380, key380, TotalCard380, monthMst, yearMst, maxOpen380, maxClose380,
                maxTotalCard380, maxTotalSales380, balOpen380, balClose380, balCard380, TotalBalCard380);

        SctOpeningClosing SctOpeningClosing400 = new SctOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening400.getText().toString()), Integer.parseInt(mBinding.teiClosing400.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue400.getText().toString()), spare400, key400, TotalCard400, monthMst, yearMst, maxOpen400, maxClose400,
                maxTotalCard400, maxTotalSales400, balOpen400, balClose400, balCard400, TotalBalCard400);


        List<SctOpeningClosing> SctOpeningClosingList = new ArrayList<>();
        SctOpeningClosingList.add(SctOpeningClosing100);
        SctOpeningClosingList.add(SctOpeningClosing120);
        SctOpeningClosingList.add(SctOpeningClosing140);
        SctOpeningClosingList.add(SctOpeningClosing160);
        SctOpeningClosingList.add(SctOpeningClosing180);
        SctOpeningClosingList.add(SctOpeningClosing200);
        SctOpeningClosingList.add(SctOpeningClosing220);
        SctOpeningClosingList.add(SctOpeningClosing240);
        SctOpeningClosingList.add(SctOpeningClosing260);
        SctOpeningClosingList.add(SctOpeningClosing280);
        SctOpeningClosingList.add(SctOpeningClosing300);
        SctOpeningClosingList.add(SctOpeningClosing320);
        SctOpeningClosingList.add(SctOpeningClosing340);
        SctOpeningClosingList.add(SctOpeningClosing360);
        SctOpeningClosingList.add(SctOpeningClosing380);
        SctOpeningClosingList.add(SctOpeningClosing400);
        updateSctMonthEntryToDb(SctOpeningClosingList);

    }

    private void sctEntry() {
        long Date = Long.valueOf(new SimpleDateFormat("ddMMyyyy").format(new Date()));
        String monthMst = mBinding.monthMst.getText().toString();
        String yearMst = mBinding.yearMst.getText().toString();
        String dateMst = mAppClass.getCurrentDateTime();
        spare100 = preferences.getInt("sctSpare100", 0);
        spare120 = preferences.getInt("sctSpare120", 0);
        spare140 = preferences.getInt("sctSpare140", 0);
        spare160 = preferences.getInt("sctSpare160", 0);
        spare180 = preferences.getInt("sctSpare180", 0);
        spare200 = preferences.getInt("sctSpare200", 0);
        spare220 = preferences.getInt("sctSpare220", 0);
        spare240 = preferences.getInt("sctSpare240", 0);
        spare260 = preferences.getInt("sctSpare260", 0);
        spare280 = preferences.getInt("sctSpare280", 0);
        spare300 = preferences.getInt("sctSpare300", 0);
        spare320 = preferences.getInt("sctSpare320", 0);
        spare340 = preferences.getInt("sctSpare340", 0);
        spare360 = preferences.getInt("sctSpare360", 0);
        spare380 = preferences.getInt("sctSpare380", 0);
        spare400 = preferences.getInt("sctSpare400", 0);

        key100 = preferences.getString("sctKey100", "");
        key120 = preferences.getString("sctKey120", "");
        key140 = preferences.getString("sctKey140", "");
        key160 = preferences.getString("sctKey160", "");
        key180 = preferences.getString("sctKey180", "");
        key200 = preferences.getString("sctKey200", "");
        key220 = preferences.getString("sctKey220", "");
        key240 = preferences.getString("sctKey240", "");
        key260 = preferences.getString("sctKey260", "");
        key280 = preferences.getString("sctKey280", "");
        key300 = preferences.getString("sctKey300", "");
        key320 = preferences.getString("sctKey320", "");
        key340 = preferences.getString("sctKey340", "");
        key360 = preferences.getString("sctKey360", "");
        key380 = preferences.getString("sctKey380", "");
        key400 = preferences.getString("sctKey400", "");

        //card100
        int TotalCard100 = Integer.parseInt(mBinding.teiClosing100.getText().toString()) - Integer.parseInt(mBinding.teiOpening100.getText().toString());
        if (TotalCard100 == 0) {
            TotalCard100 = 1;
        } else {
            TotalCard100 = Integer.parseInt(mBinding.teiClosing100.getText().toString()) - Integer.parseInt(mBinding.teiOpening100.getText().toString()) + 1;
        }
        if (mBinding.teiClosing100.getText().toString().equals("0") && mBinding.teiOpening100.getText().toString().equals("0")) {
            TotalCard100 = 0;
        }
        int totalAmount100 = TotalCard100 * Integer.parseInt(mBinding.teiCardValue100.getText().toString());
        //card120
        int TotalCard120 = Integer.parseInt(mBinding.teiClosing120.getText().toString()) - Integer.parseInt(mBinding.teiOpening120.getText().toString());
        if (TotalCard120 == 0) {
            TotalCard120 = 1;
        } else {
            TotalCard120 = Integer.parseInt(mBinding.teiClosing120.getText().toString()) - Integer.parseInt(mBinding.teiOpening120.getText().toString()) + 1;
        }
        if (mBinding.teiClosing120.getText().toString().equals("0") && mBinding.teiOpening120.getText().toString().equals("0")) {
            TotalCard120 = 0;
        }
        int totalAmount120 = TotalCard120 * Integer.parseInt(mBinding.teiCardValue120.getText().toString());
        //card140
        int TotalCard140 = Integer.parseInt(mBinding.teiClosing140.getText().toString()) - Integer.parseInt(mBinding.teiOpening140.getText().toString());
        if (TotalCard140 == 0) {
            TotalCard140 = 1;
        } else {
            TotalCard140 = Integer.parseInt(mBinding.teiClosing140.getText().toString()) - Integer.parseInt(mBinding.teiOpening140.getText().toString()) + 1;
        }
        if (mBinding.teiClosing140.getText().toString().equals("0") && mBinding.teiOpening140.getText().toString().equals("0")) {
            TotalCard140 = 0;
        }
        int totalAmount140 = TotalCard140 * Integer.parseInt(mBinding.teiCardValue140.getText().toString());
        //card160
        int TotalCard160 = Integer.parseInt(mBinding.teiClosing160.getText().toString()) - Integer.parseInt(mBinding.teiOpening160.getText().toString());
        if (TotalCard160 == 0) {
            TotalCard160 = 1;
        } else {
            TotalCard160 = Integer.parseInt(mBinding.teiClosing160.getText().toString()) - Integer.parseInt(mBinding.teiOpening160.getText().toString()) + 1;
        }
        if (mBinding.teiClosing160.getText().toString().equals("0") && mBinding.teiOpening160.getText().toString().equals("0")) {
            TotalCard160 = 0;
        }
        int totalAmount160 = TotalCard160 * Integer.parseInt(mBinding.teiCardValue160.getText().toString());
        //card360
        int TotalCard180 = Integer.parseInt(mBinding.teiClosing180.getText().toString()) - Integer.parseInt(mBinding.teiOpening180.getText().toString());
        if (TotalCard180 == 0) {
            TotalCard180 = 1;
        } else {
            TotalCard180 = Integer.parseInt(mBinding.teiClosing180.getText().toString()) - Integer.parseInt(mBinding.teiOpening180.getText().toString()) + 1;
        }
        if (mBinding.teiClosing180.getText().toString().equals("0") && mBinding.teiOpening180.getText().toString().equals("0")) {
            TotalCard180 = 0;
        }
        int totalAmount180 = TotalCard180 * Integer.parseInt(mBinding.teiCardValue180.getText().toString());
        //card200
        int TotalCard200 = Integer.parseInt(mBinding.teiClosing200.getText().toString()) - Integer.parseInt(mBinding.teiOpening200.getText().toString());
        if (TotalCard200 == 0) {
            TotalCard200 = 1;
        } else {
            TotalCard200 = Integer.parseInt(mBinding.teiClosing200.getText().toString()) - Integer.parseInt(mBinding.teiOpening200.getText().toString()) + 1;
        }
        if (mBinding.teiClosing200.getText().toString().equals("0") && mBinding.teiOpening200.getText().toString().equals("0")) {
            TotalCard200 = 0;
        }
        int totalAmount200 = TotalCard200 * Integer.parseInt(mBinding.teiCardValue200.getText().toString());
        //card220
        int TotalCard220 = Integer.parseInt(mBinding.teiClosing220.getText().toString()) - Integer.parseInt(mBinding.teiOpening220.getText().toString());
        if (TotalCard220 == 0) {
            TotalCard220 = 1;
        } else {
            TotalCard220 = Integer.parseInt(mBinding.teiClosing220.getText().toString()) - Integer.parseInt(mBinding.teiOpening220.getText().toString()) + 1;
        }
        if (mBinding.teiClosing220.getText().toString().equals("0") && mBinding.teiOpening220.getText().toString().equals("0")) {
            TotalCard220 = 0;
        }
        int totalAmount220 = TotalCard220 * Integer.parseInt(mBinding.teiCardValue220.getText().toString());
        //card240
        int TotalCard240 = Integer.parseInt(mBinding.teiClosing240.getText().toString()) - Integer.parseInt(mBinding.teiOpening240.getText().toString());
        if (TotalCard240 == 0) {
            TotalCard240 = 1;
        } else {
            TotalCard240 = Integer.parseInt(mBinding.teiClosing240.getText().toString()) - Integer.parseInt(mBinding.teiOpening240.getText().toString()) + 1;
        }
        if (mBinding.teiClosing240.getText().toString().equals("0") && mBinding.teiOpening240.getText().toString().equals("0")) {
            TotalCard240 = 0;
        }
        int totalAmount240 = TotalCard240 * Integer.parseInt(mBinding.teiCardValue240.getText().toString());
        //card260
        int TotalCard260 = Integer.parseInt(mBinding.teiClosing260.getText().toString()) - Integer.parseInt(mBinding.teiOpening260.getText().toString());
        if (TotalCard260 == 0) {
            TotalCard260 = 1;
        } else {
            TotalCard260 = Integer.parseInt(mBinding.teiClosing260.getText().toString()) - Integer.parseInt(mBinding.teiOpening260.getText().toString()) + 1;
        }
        if (mBinding.teiClosing260.getText().toString().equals("0") && mBinding.teiOpening260.getText().toString().equals("0")) {
            TotalCard260 = 0;
        }
        int totalAmount260 = TotalCard260 * Integer.parseInt(mBinding.teiCardValue260.getText().toString());

        //card280
        int TotalCard280 = Integer.parseInt(mBinding.teiClosing280.getText().toString()) - Integer.parseInt(mBinding.teiOpening280.getText().toString());
        if (TotalCard280 == 0) {
            TotalCard280 = 1;
        } else {
            TotalCard280 = Integer.parseInt(mBinding.teiClosing280.getText().toString()) - Integer.parseInt(mBinding.teiOpening280.getText().toString()) + 1;
        }
        if (mBinding.teiClosing280.getText().toString().equals("0") && mBinding.teiOpening280.getText().toString().equals("0")) {
            TotalCard280 = 0;
        }
        int totalAmount280 = TotalCard280 * Integer.parseInt(mBinding.teiCardValue280.getText().toString());
        //card300
        int TotalCard300 = Integer.parseInt(mBinding.teiClosing300.getText().toString()) - Integer.parseInt(mBinding.teiOpening300.getText().toString());
        if (TotalCard300 == 0) {
            TotalCard300 = 1;
        } else {
            TotalCard300 = Integer.parseInt(mBinding.teiClosing300.getText().toString()) - Integer.parseInt(mBinding.teiOpening300.getText().toString()) + 1;
        }
        if (mBinding.teiClosing300.getText().toString().equals("0") && mBinding.teiOpening300.getText().toString().equals("0")) {
            TotalCard300 = 0;
        }
        int totalAmount300 = TotalCard300 * Integer.parseInt(mBinding.teiCardValue300.getText().toString());
        //card320
        int TotalCard320 = Integer.parseInt(mBinding.teiClosing320.getText().toString()) - Integer.parseInt(mBinding.teiOpening320.getText().toString());
        if (TotalCard320 == 0) {
            TotalCard320 = 1;
        } else {
            TotalCard320 = Integer.parseInt(mBinding.teiClosing320.getText().toString()) - Integer.parseInt(mBinding.teiOpening320.getText().toString()) + 1;
        }
        if (mBinding.teiClosing320.getText().toString().equals("0") && mBinding.teiOpening320.getText().toString().equals("0")) {
            TotalCard320 = 0;
        }
        int totalAmount320 = TotalCard320 * Integer.parseInt(mBinding.teiCardValue320.getText().toString());
        //card340
        int TotalCard340 = Integer.parseInt(mBinding.teiClosing340.getText().toString()) - Integer.parseInt(mBinding.teiOpening340.getText().toString());
        if (TotalCard340 == 0) {
            TotalCard340 = 1;
        } else {
            TotalCard340 = Integer.parseInt(mBinding.teiClosing340.getText().toString()) - Integer.parseInt(mBinding.teiOpening340.getText().toString()) + 1;
        }
        if (mBinding.teiClosing340.getText().toString().equals("0") && mBinding.teiOpening340.getText().toString().equals("0")) {
            TotalCard340 = 0;
        }
        int totalAmount340 = TotalCard340 * Integer.parseInt(mBinding.teiCardValue340.getText().toString());
        //card360
        int TotalCard360 = Integer.parseInt(mBinding.teiClosing360.getText().toString()) - Integer.parseInt(mBinding.teiOpening360.getText().toString());
        if (TotalCard360 == 0) {
            TotalCard360 = 1;
        } else {
            TotalCard360 = Integer.parseInt(mBinding.teiClosing360.getText().toString()) - Integer.parseInt(mBinding.teiOpening360.getText().toString()) + 1;
        }
        if (mBinding.teiClosing360.getText().toString().equals("0") && mBinding.teiOpening360.getText().toString().equals("0")) {
            TotalCard360 = 0;
        }
        int totalAmount360 = TotalCard360 * Integer.parseInt(mBinding.teiCardValue360.getText().toString());
        //card380
        int TotalCard380 = Integer.parseInt(mBinding.teiClosing380.getText().toString()) - Integer.parseInt(mBinding.teiOpening380.getText().toString());
        if (TotalCard380 == 0) {
            TotalCard380 = 1;
        } else {
            TotalCard380 = Integer.parseInt(mBinding.teiClosing380.getText().toString()) - Integer.parseInt(mBinding.teiOpening380.getText().toString()) + 1;
        }
        if (mBinding.teiClosing380.getText().toString().equals("0") && mBinding.teiOpening380.getText().toString().equals("0")) {
            TotalCard380 = 0;
        }
        int totalAmount380 = TotalCard380 * Integer.parseInt(mBinding.teiCardValue380.getText().toString());
        //card400
        int TotalCard400 = Integer.parseInt(mBinding.teiClosing400.getText().toString()) - Integer.parseInt(mBinding.teiOpening400.getText().toString());
        if (TotalCard400 == 0) {
            TotalCard400 = 1;
        } else {
            TotalCard400 = Integer.parseInt(mBinding.teiClosing400.getText().toString()) - Integer.parseInt(mBinding.teiOpening400.getText().toString()) + 1;
        }
        if (mBinding.teiClosing400.getText().toString().equals("0") && mBinding.teiOpening400.getText().toString().equals("0")) {
            TotalCard400 = 0;
        }
        int totalAmount400 = TotalCard400 * Integer.parseInt(mBinding.teiCardValue400.getText().toString());

        SctEntity entity100 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue100.getText().toString()), spare100, key100,
                Integer.parseInt(mBinding.teiOpening100.getText().toString()), Integer.parseInt(mBinding.teiClosing100.getText().toString()), monthMst, yearMst, TotalCard100, totalAmount100, dateMst);

        SctEntity entity120 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue120.getText().toString()), spare120, key120,
                Integer.parseInt(mBinding.teiOpening120.getText().toString()), Integer.parseInt(mBinding.teiClosing120.getText().toString()), monthMst, yearMst, TotalCard120, totalAmount120, dateMst);

        SctEntity entity140 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue140.getText().toString()), spare140, key140,
                Integer.parseInt(mBinding.teiOpening140.getText().toString()), Integer.parseInt(mBinding.teiClosing140.getText().toString()), monthMst, yearMst, TotalCard140, totalAmount140, dateMst);

        SctEntity entity160 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue160.getText().toString()), spare160, key160,
                Integer.parseInt(mBinding.teiOpening160.getText().toString()), Integer.parseInt(mBinding.teiClosing160.getText().toString()), monthMst, yearMst, TotalCard160, totalAmount160, dateMst);

        SctEntity entity180 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue180.getText().toString()), spare180, key180,
                Integer.parseInt(mBinding.teiOpening180.getText().toString()), Integer.parseInt(mBinding.teiClosing180.getText().toString()), monthMst, yearMst, TotalCard180, totalAmount180, dateMst);

        SctEntity entity200 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue200.getText().toString()), spare200, key200,
                Integer.parseInt(mBinding.teiOpening200.getText().toString()), Integer.parseInt(mBinding.teiClosing200.getText().toString()), monthMst, yearMst, TotalCard200, totalAmount200, dateMst);

        SctEntity entity220 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue220.getText().toString()), spare220, key220,
                Integer.parseInt(mBinding.teiOpening220.getText().toString()), Integer.parseInt(mBinding.teiClosing220.getText().toString()), monthMst, yearMst, TotalCard220, totalAmount220, dateMst);

        SctEntity entity240 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue240.getText().toString()), spare240, key240,
                Integer.parseInt(mBinding.teiOpening240.getText().toString()), Integer.parseInt(mBinding.teiClosing240.getText().toString()), monthMst, yearMst, TotalCard240, totalAmount240, dateMst);

        SctEntity entity260 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue260.getText().toString()), spare260, key260,
                Integer.parseInt(mBinding.teiOpening260.getText().toString()), Integer.parseInt(mBinding.teiClosing260.getText().toString()), monthMst, yearMst, TotalCard260, totalAmount260, dateMst);

        SctEntity entity280 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue280.getText().toString()), spare280, key280,
                Integer.parseInt(mBinding.teiOpening280.getText().toString()), Integer.parseInt(mBinding.teiClosing280.getText().toString()), monthMst, yearMst, TotalCard280, totalAmount280, dateMst);

        SctEntity entity300 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue300.getText().toString()), spare300, key300,
                Integer.parseInt(mBinding.teiOpening300.getText().toString()), Integer.parseInt(mBinding.teiClosing300.getText().toString()), monthMst, yearMst, TotalCard300, totalAmount300, dateMst);

        SctEntity entity320 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue320.getText().toString()), spare320, key320,
                Integer.parseInt(mBinding.teiOpening320.getText().toString()), Integer.parseInt(mBinding.teiClosing320.getText().toString()), monthMst, yearMst, TotalCard320, totalAmount320, dateMst);

        SctEntity entity340 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue340.getText().toString()), spare340, key340,
                Integer.parseInt(mBinding.teiOpening340.getText().toString()), Integer.parseInt(mBinding.teiClosing340.getText().toString()), monthMst, yearMst, TotalCard340, totalAmount340, dateMst);

        SctEntity entity360 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue360.getText().toString()), spare360, key360,
                Integer.parseInt(mBinding.teiOpening360.getText().toString()), Integer.parseInt(mBinding.teiClosing360.getText().toString()), monthMst, yearMst, TotalCard360, totalAmount360, dateMst);

        SctEntity entity380 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue380.getText().toString()), spare380, key380,
                Integer.parseInt(mBinding.teiOpening380.getText().toString()), Integer.parseInt(mBinding.teiClosing380.getText().toString()), monthMst, yearMst, TotalCard380, totalAmount380, dateMst);

        SctEntity entity400 = new SctEntity(Date, Integer.parseInt(mBinding.teiCardValue400.getText().toString()), spare400, key400,
                Integer.parseInt(mBinding.teiOpening400.getText().toString()), Integer.parseInt(mBinding.teiClosing400.getText().toString()), monthMst, yearMst, TotalCard400, totalAmount400, dateMst);

        List<SctEntity> entryList = new ArrayList<>();
        entryList.add(entity100);
        entryList.add(entity120);
        entryList.add(entity140);
        entryList.add(entity160);
        entryList.add(entity180);
        entryList.add(entity200);
        entryList.add(entity220);
        entryList.add(entity240);
        entryList.add(entity260);
        entryList.add(entity280);
        entryList.add(entity300);
        entryList.add(entity320);
        entryList.add(entity340);
        entryList.add(entity360);
        entryList.add(entity380);
        entryList.add(entity400);
        updateSctEntryToDb(entryList);


    }

    public void updateSctEntryToDb(List<SctEntity> entryList) {
        dao.insertSctEntry(entryList.toArray(new SctEntity[0]));
        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
    }

    public void updateSctMonthEntryToDb(List<SctOpeningClosing> entryList) {
        sctOpeningDao.insertSctMonth(entryList.toArray(new SctOpeningClosing[0]));
        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
    }

    private boolean validation() {
        if (mBinding.teiOpening100.getText().toString().isEmpty()) {
            mBinding.teiOpening100.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing100.getText().toString().isEmpty()) {
            mBinding.teiClosing100.setError(" Closing is Empty");
            return false;
        } else if ((Integer.parseInt(mBinding.teiOpening100.getText().toString())
                > Integer.parseInt(mBinding.teiClosing100.getText().toString()))) {
            mBinding.teiOpening100.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening120.getText().toString().isEmpty()) {
            mBinding.teiOpening120.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing120.getText().toString().isEmpty()) {
            mBinding.teiClosing120.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening120.getText().toString())
                > Integer.parseInt(mBinding.teiClosing120.getText().toString())) {
            mBinding.teiOpening120.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening140.getText().toString().isEmpty()) {
            mBinding.teiOpening140.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing140.getText().toString().isEmpty()) {
            mBinding.teiClosing140.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening140.getText().toString())
                > Integer.parseInt(mBinding.teiClosing140.getText().toString())) {
            mBinding.teiOpening140.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening160.getText().toString().isEmpty()) {
            mBinding.teiOpening160.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing160.getText().toString().isEmpty()) {
            mBinding.teiClosing160.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening160.getText().toString())
                > Integer.parseInt(mBinding.teiClosing160.getText().toString())) {
            mBinding.teiOpening160.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening180.getText().toString().isEmpty()) {
            mBinding.teiOpening180.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing180.getText().toString().isEmpty()) {
            mBinding.teiClosing180.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening180.getText().toString())
                > Integer.parseInt(mBinding.teiClosing180.getText().toString())) {
            mBinding.teiOpening360.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening200.getText().toString().isEmpty()) {
            mBinding.teiOpening200.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing200.getText().toString().isEmpty()) {
            mBinding.teiClosing200.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening200.getText().toString())
                > Integer.parseInt(mBinding.teiClosing200.getText().toString())) {
            mBinding.teiOpening200.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening220.getText().toString().isEmpty()) {
            mBinding.teiOpening220.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing220.getText().toString().isEmpty()) {
            mBinding.teiClosing220.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening220.getText().toString())
                > Integer.parseInt(mBinding.teiClosing220.getText().toString())) {
            mBinding.teiOpening220.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening240.getText().toString().isEmpty()) {
            mBinding.teiOpening400.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing240.getText().toString().isEmpty()) {
            mBinding.teiClosing240.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening240.getText().toString())
                > Integer.parseInt(mBinding.teiClosing240.getText().toString())) {
            mBinding.teiOpening240.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening260.getText().toString().isEmpty()) {
            mBinding.teiOpening260.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing260.getText().toString().isEmpty()) {
            mBinding.teiClosing260.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening260.getText().toString())
                > Integer.parseInt(mBinding.teiClosing260.getText().toString())) {
            mBinding.teiOpening260.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening280.getText().toString().isEmpty()) {
            mBinding.teiOpening280.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing280.getText().toString().isEmpty()) {
            mBinding.teiClosing280.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening280.getText().toString())
                > Integer.parseInt(mBinding.teiClosing280.getText().toString())) {
            mBinding.teiOpening280.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening300.getText().toString().isEmpty()) {
            mBinding.teiOpening300.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing300.getText().toString().isEmpty()) {
            mBinding.teiClosing300.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening300.getText().toString())
                > Integer.parseInt(mBinding.teiClosing300.getText().toString())) {
            mBinding.teiOpening300.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening320.getText().toString().isEmpty()) {
            mBinding.teiOpening320.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing320.getText().toString().isEmpty()) {
            mBinding.teiClosing320.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening320.getText().toString())
                > Integer.parseInt(mBinding.teiClosing320.getText().toString())) {
            mBinding.teiOpening320.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening340.getText().toString().isEmpty()) {
            mBinding.teiOpening340.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing340.getText().toString().isEmpty()) {
            mBinding.teiClosing340.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening340.getText().toString())
                > Integer.parseInt(mBinding.teiClosing340.getText().toString())) {
            mBinding.teiOpening340.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening360.getText().toString().isEmpty()) {
            mBinding.teiOpening360.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing360.getText().toString().isEmpty()) {
            mBinding.teiClosing360.setError(" Closing is Empty");
            return false;

        } else if (Integer.parseInt(mBinding.teiOpening360.getText().toString())
                > Integer.parseInt(mBinding.teiClosing360.getText().toString())) {
            mBinding.teiOpening360.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening380.getText().toString().isEmpty()) {
            mBinding.teiOpening380.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing380.getText().toString().isEmpty()) {
            mBinding.teiClosing380.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening380.getText().toString())
                > Integer.parseInt(mBinding.teiClosing380.getText().toString())) {
            mBinding.teiOpening380.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening400.getText().toString().isEmpty()) {
            mBinding.teiOpening400.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing400.getText().toString().isEmpty()) {
            mBinding.teiClosing400.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening400.getText().toString())
                > Integer.parseInt(mBinding.teiClosing400.getText().toString())) {
            mBinding.teiOpening400.setError("Opening is Greater than Closing");
            return false;
        }

        return true;
    }
}
