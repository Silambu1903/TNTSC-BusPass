package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.DAOs.MstOpeningDao;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.MstSctEntryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MstSctFragment extends Fragment {
    MstSctEntryBinding mBinding;
    BaseActivity mActivity;
    private SharedPreferences preferences;
    Context mContext;
    ApplicationClass mAppClass;
    TnstcBusPassDB db;
    MstDao dao;
    MstOpeningDao daoOpenClose;
    String monthMst;
    public int spare200, spare240, spare280, spare320, spare360, spare400, spare440, spare480,
            spare520, spare560, spare600, spare640, spare680, spare720, spare760;

    public String key200, key240, key280, key320, key360, key400, key440, key480,
            key520, key560, key600, key640, key680, key720, key760;

    int balOpen200, balClose200, TotalBalCard200, balCard200, balCurrentOpen200, maxMonthOpenCard200,
            balOpen240, balClose240, TotalBalCard240, balCard240, balCurrentOpen240, maxMonthOpenCard240,
            balOpen280, balClose280, TotalBalCard280, balCard280, balCurrentOpen280, maxMonthOpenCard280,
            balOpen320, balClose320, TotalBalCard320, balCard320, balCurrentOpen320, maxMonthOpenCard320,
            balOpen360, balClose360, TotalBalCard360, balCard360, balCurrentOpen360, maxMonthOpenCard360,
            balOpen400, balClose400, TotalBalCard400, balCard400, balCurrentOpen400, maxMonthOpenCard400,
            balOpen440, balClose440, TotalBalCard440, balCard440, balCurrentOpen440, maxMonthOpenCard440,
            balOpen480, balClose480, TotalBalCard480, balCard480, balCurrentOpen480, maxMonthOpenCard480,
            balOpen520, balClose520, TotalBalCard520, balCard520, balCurrentOpen520, maxMonthOpenCard520,
            balOpen560, balClose560, TotalBalCard560, balCard560, balCurrentOpen560, maxMonthOpenCard560,
            balOpen600, balClose600, TotalBalCard600, balCard600, balCurrentOpen600, maxMonthOpenCard600,
            balOpen640, balClose640, TotalBalCard640, balCard640, balCurrentOpen640, maxMonthOpenCard640,
            balOpen680, balClose680, TotalBalCard680, balCard680, balCurrentOpen680, maxMonthOpenCard680,
            balOpen720, balClose720, TotalBalCard720, balCard720, balCurrentOpen720, maxMonthOpenCard720,
            balOpen760, balClose760, TotalBalCard760, balCard760, balCurrentOpen760, maxMonthOpenCard760;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.mst_sct_entry, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mContext = getContext();
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mBinding.monthMst.setText((String) android.text.format.DateFormat.format("MMMM", new Date()));
        mBinding.yearMst.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        mBinding.date.setText(mAppClass.getCurrentDateTime());
        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.mstDao();
        daoOpenClose = db.OpeningDao();
        mBinding.mstSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daoOpenClose.mstMonthEmptyCheck(mBinding.monthMst.getText().toString()) != null) {
                    mBinding.monthlyDataRadio.setChecked(false);
                }
                if (mBinding.monthlyDataRadio.isChecked()) {
                    if (validation()) {
                        mstMonthEntry();
                    }
                } else {
                    if (validation()
                            && daoOpenClose.mstMonthEmptyCheck(mBinding.monthMst.getText().toString()) != null) {
                        MstEntry();
                    } else {
                        mAppClass.showSnackBar(getContext(), mBinding.monthMst.getText().toString() + " Data is Empty");
                    }
                }

            }
        });

    }


    private void mstMonthEntry() {
        long Date = Long.valueOf(new SimpleDateFormat("MMyyyy").format(new Date()));
        String monthMst = mBinding.monthMst.getText().toString();
        String yearMst = mBinding.yearMst.getText().toString();
        spare200 = preferences.getInt("spare200", 0);
        spare240 = preferences.getInt("spare240", 0);
        spare280 = preferences.getInt("spare280", 0);
        spare320 = preferences.getInt("spare320", 0);
        spare360 = preferences.getInt("spare360", 0);
        spare400 = preferences.getInt("spare400", 0);
        spare440 = preferences.getInt("spare440", 0);
        spare480 = preferences.getInt("spare480", 0);
        spare520 = preferences.getInt("spare520", 0);
        spare560 = preferences.getInt("spare560", 0);
        spare600 = preferences.getInt("spare600", 0);
        spare640 = preferences.getInt("spare640", 0);
        spare680 = preferences.getInt("spare680", 0);
        spare720 = preferences.getInt("spare720", 0);
        spare760 = preferences.getInt("spare760", 0);
        key200 = preferences.getString("key200", "");
        key240 = preferences.getString("key240", "");
        key280 = preferences.getString("key280", "");
        key320 = preferences.getString("key320", "");
        key360 = preferences.getString("key360", "");
        key400 = preferences.getString("key400", "");
        key440 = preferences.getString("key440", "");
        key480 = preferences.getString("key480", "");
        key520 = preferences.getString("key520", "");
        key560 = preferences.getString("key560", "");
        key600 = preferences.getString("key600", "");
        key640 = preferences.getString("key640", "");
        key680 = preferences.getString("key680", "");
        key720 = preferences.getString("key720", "");
        key760 = preferences.getString("key760", "");

        //card200
        int TotalCard200 = Integer.parseInt(mBinding.teiClosing.getText().toString()) - Integer.parseInt(mBinding.teiOpening.getText().toString());
        if (TotalCard200 == 0) {
            TotalCard200 = 1;
        } else {
            TotalCard200 = Integer.parseInt(mBinding.teiClosing.getText().toString()) - Integer.parseInt(mBinding.teiOpening.getText().toString()) + 1;
        }
        if (mBinding.teiClosing.getText().toString().equals("0") && mBinding.teiOpening.getText().toString().equals("0")) {
            TotalCard200 = 0;
        }
        int maxOpen200 = dao.mstMonthOpen200(monthMst, 200);
        int maxClose200 = dao.mstMonthClose200(monthMst, 200);
        int maxTotalCard = dao.mstMonthTotalCard200(monthMst, 200);
        int maxTotalSales = dao.mstMonthTotalAmount200(monthMst, 200);
        if (dao.mstMonthOpen200(monthMst, 200) != 0) {
            balOpen200 = dao.mstMonthCloseMax200(monthMst, 200) + 1;
            balClose200 = daoOpenClose.mstMonthBalClose200(monthMst, 200);
            maxMonthOpenCard200 = dao.mstMonthCloseMax200(monthMst, 200);
            balCard200 = daoOpenClose.mstMonthBalClose200(monthMst, 200) - maxMonthOpenCard200;
            TotalBalCard200 = dao.mstMonthTotalCard200(monthMst, 200) + balCard200;
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
        int maxOpen240 = dao.mstMonthOpen200(monthMst, 240);
        int maxClose240 = dao.mstMonthClose200(monthMst, 240);
        int maxTotalCard240 = dao.mstMonthTotalCard200(monthMst, 240);
        int maxTotalSales240 = dao.mstMonthTotalAmount200(monthMst, 240);
        if (dao.mstMonthOpen200(monthMst, 240) != 0) {
            balOpen240 = dao.mstMonthCloseMax200(monthMst, 240) + 1;
            balClose240 = daoOpenClose.mstMonthBalClose200(monthMst, 240);
            maxMonthOpenCard240 = dao.mstMonthCloseMax200(monthMst, 240);
            balCard240 = daoOpenClose.mstMonthBalClose200(monthMst, 240) - maxMonthOpenCard240;
            TotalBalCard240 = dao.mstMonthTotalCard200(monthMst, 240) + balCard240;
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
        int maxOpen280 = dao.mstMonthOpen200(monthMst, 280);
        int maxClose280 = dao.mstMonthClose200(monthMst, 280);
        int maxTotalCard280 = dao.mstMonthTotalCard200(monthMst, 280);
        int maxTotalSales280 = dao.mstMonthTotalAmount200(monthMst, 280);
        if (dao.mstMonthOpen200(monthMst, 280) != 0) {
            balOpen280 = dao.mstMonthCloseMax200(monthMst, 280) + 1;
            balClose280 = daoOpenClose.mstMonthBalClose200(monthMst, 280);
            maxMonthOpenCard280 = dao.mstMonthCloseMax200(monthMst, 280);
            balCard280 = daoOpenClose.mstMonthBalClose200(monthMst, 280) - maxMonthOpenCard280;
            TotalBalCard280 = dao.mstMonthTotalCard200(monthMst, 280) + balCard280;
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
        int maxOpen320 = dao.mstMonthOpen200(monthMst, 320);
        int maxClose320 = dao.mstMonthClose200(monthMst, 320);
        int maxTotalCard320 = dao.mstMonthTotalCard200(monthMst, 320);
        int maxTotalSales320 = dao.mstMonthTotalAmount200(monthMst, 320);
        if (dao.mstMonthOpen200(monthMst, 320) != 0) {
            balOpen320 = dao.mstMonthCloseMax200(monthMst, 320) + 1;
            balClose320 = daoOpenClose.mstMonthBalClose200(monthMst, 320);
            maxMonthOpenCard320 = dao.mstMonthCloseMax200(monthMst, 320);
            balCard320 = daoOpenClose.mstMonthBalClose200(monthMst, 320) - maxMonthOpenCard320;
            TotalBalCard320 = dao.mstMonthTotalCard200(monthMst, 320) + balCard320;
        }
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
        int maxOpen360 = dao.mstMonthOpen200(monthMst, 360);
        int maxClose360 = dao.mstMonthClose200(monthMst, 360);
        int maxTotalCard360 = dao.mstMonthTotalCard200(monthMst, 360);
        int maxTotalSales360 = dao.mstMonthTotalAmount200(monthMst, 360);
        if (dao.mstMonthOpen200(monthMst, 360) != 0) {
            balOpen360 = dao.mstMonthCloseMax200(monthMst, 360) + 1;
            balClose360 = daoOpenClose.mstMonthBalClose200(monthMst, 360);
            maxMonthOpenCard360 = dao.mstMonthCloseMax200(monthMst, 360);
            balCard360 = daoOpenClose.mstMonthBalClose200(monthMst, 360) - maxMonthOpenCard360;
            TotalBalCard360 = dao.mstMonthTotalCard200(monthMst, 360) + balCard360;
        }
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
        int maxOpen400 = dao.mstMonthOpen200(monthMst, 400);
        int maxClose400 = dao.mstMonthClose200(monthMst, 400);
        int maxTotalCard400 = dao.mstMonthTotalCard200(monthMst, 400);
        int maxTotalSales400 = dao.mstMonthTotalAmount200(monthMst, 400);
        if (dao.mstMonthOpen200(monthMst, 400) != 0) {
            balOpen400 = dao.mstMonthCloseMax200(monthMst, 400) + 1;
            balClose400 = daoOpenClose.mstMonthBalClose200(monthMst, 400);
            maxMonthOpenCard400 = dao.mstMonthCloseMax200(monthMst, 400);
            balCard400 = daoOpenClose.mstMonthBalClose200(monthMst, 400) - maxMonthOpenCard400;
            TotalBalCard400 = dao.mstMonthTotalCard200(monthMst, 400) + balCard400;
        }
        //card440
        int TotalCard440 = Integer.parseInt(mBinding.teiClosing440.getText().toString()) - Integer.parseInt(mBinding.teiOpening440.getText().toString());
        if (TotalCard440 == 0) {
            TotalCard440 = 1;
        } else {
            TotalCard440 = Integer.parseInt(mBinding.teiClosing440.getText().toString()) - Integer.parseInt(mBinding.teiOpening440.getText().toString()) + 1;
        }
        if (mBinding.teiClosing440.getText().toString().equals("0") && mBinding.teiOpening440.getText().toString().equals("0")) {
            TotalCard440 = 0;
        }
        int maxOpen440 = dao.mstMonthOpen200(monthMst, 440);
        int maxClose440 = dao.mstMonthClose200(monthMst, 440);
        int maxTotalCard440 = dao.mstMonthTotalCard200(monthMst, 440);
        int maxTotalSales440 = dao.mstMonthTotalAmount200(monthMst, 440);
        if (dao.mstMonthOpen200(monthMst, 440) != 0) {
            balOpen440 = dao.mstMonthCloseMax200(monthMst, 440) + 1;
            balClose440 = daoOpenClose.mstMonthBalClose200(monthMst, 440);
            maxMonthOpenCard440 = dao.mstMonthCloseMax200(monthMst, 440);
            balCard440 = daoOpenClose.mstMonthBalClose200(monthMst, 440) - maxMonthOpenCard440;
            TotalBalCard440 = dao.mstMonthTotalCard200(monthMst, 440) + balCard440;
        }
        //card480
        int TotalCard480 = Integer.parseInt(mBinding.teiClosing480.getText().toString()) - Integer.parseInt(mBinding.teiOpening480.getText().toString());
        if (TotalCard480 == 0) {
            TotalCard480 = 1;
        } else {
            TotalCard480 = Integer.parseInt(mBinding.teiClosing480.getText().toString()) - Integer.parseInt(mBinding.teiOpening480.getText().toString()) + 1;
        }
        if (mBinding.teiClosing480.getText().toString().equals("0") && mBinding.teiOpening480.getText().toString().equals("0")) {
            TotalCard480 = 0;
        }
        int maxOpen480 = dao.mstMonthOpen200(monthMst, 480);
        int maxClose480 = dao.mstMonthClose200(monthMst, 480);
        int maxTotalCard480 = dao.mstMonthTotalCard200(monthMst, 480);
        int maxTotalSales480 = dao.mstMonthTotalAmount200(monthMst, 480);
        if (dao.mstMonthOpen200(monthMst, 480) != 0) {
            balOpen480 = dao.mstMonthCloseMax200(monthMst, 480) + 1;
            balClose480 = daoOpenClose.mstMonthBalClose200(monthMst, 480);
            maxMonthOpenCard480 = dao.mstMonthCloseMax200(monthMst, 480);
            balCard480 = daoOpenClose.mstMonthBalClose200(monthMst, 480) - maxMonthOpenCard480;
            TotalBalCard480 = dao.mstMonthTotalCard200(monthMst, 480) + balCard480;
        }

        //card520
        int TotalCard520 = Integer.parseInt(mBinding.teiClosing520.getText().toString()) - Integer.parseInt(mBinding.teiOpening520.getText().toString());
        if (TotalCard520 == 0) {
            TotalCard520 = 1;
        } else {
            TotalCard520 = Integer.parseInt(mBinding.teiClosing520.getText().toString()) - Integer.parseInt(mBinding.teiOpening520.getText().toString()) + 1;
        }
        if (mBinding.teiClosing520.getText().toString().equals("0") && mBinding.teiOpening520.getText().toString().equals("0")) {
            TotalCard520 = 0;
        }
        int maxOpen520 = dao.mstMonthOpen200(monthMst, 520);
        int maxClose520 = dao.mstMonthClose200(monthMst, 520);
        int maxTotalCard520 = dao.mstMonthTotalCard200(monthMst, 520);
        int maxTotalSales520 = dao.mstMonthTotalAmount200(monthMst, 520);
        if (dao.mstMonthOpen200(monthMst, 520) != 0) {
            balOpen520 = dao.mstMonthCloseMax200(monthMst, 520) + 1;
            balClose520 = daoOpenClose.mstMonthBalClose200(monthMst, 520);
            maxMonthOpenCard520 = dao.mstMonthCloseMax200(monthMst, 520);
            balCard520 = daoOpenClose.mstMonthBalClose200(monthMst, 520) - maxMonthOpenCard520;
            TotalBalCard520 = dao.mstMonthTotalCard200(monthMst, 520) + balCard520;
        }
        //card560
        int TotalCard560 = Integer.parseInt(mBinding.teiClosing560.getText().toString()) - Integer.parseInt(mBinding.teiOpening560.getText().toString());
        if (TotalCard560 == 0) {
            TotalCard560 = 1;
        } else {
            TotalCard560 = Integer.parseInt(mBinding.teiClosing560.getText().toString()) - Integer.parseInt(mBinding.teiOpening560.getText().toString()) + 1;
        }
        if (mBinding.teiClosing560.getText().toString().equals("0") && mBinding.teiOpening560.getText().toString().equals("0")) {
            TotalCard560 = 0;
        }
        int maxOpen560 = dao.mstMonthOpen200(monthMst, 560);
        int maxClose560 = dao.mstMonthClose200(monthMst, 560);
        int maxTotalCard560 = dao.mstMonthTotalCard200(monthMst, 560);
        int maxTotalSales560 = dao.mstMonthTotalAmount200(monthMst, 560);
        if (dao.mstMonthOpen200(monthMst, 560) != 0) {
            balOpen560 = dao.mstMonthCloseMax200(monthMst, 560) + 1;
            balClose560 = daoOpenClose.mstMonthBalClose200(monthMst, 560);
            maxMonthOpenCard560 = dao.mstMonthCloseMax200(monthMst, 560);
            balCard560 = daoOpenClose.mstMonthBalClose200(monthMst, 560) - maxMonthOpenCard560;
            TotalBalCard560 = dao.mstMonthTotalCard200(monthMst, 560) + balCard560;
        }
        //card600
        int TotalCard600 = Integer.parseInt(mBinding.teiClosing600.getText().toString()) - Integer.parseInt(mBinding.teiOpening600.getText().toString());
        if (TotalCard600 == 0) {
            TotalCard600 = 1;
        } else {
            TotalCard600 = Integer.parseInt(mBinding.teiClosing600.getText().toString()) - Integer.parseInt(mBinding.teiOpening600.getText().toString()) + 1;
        }
        if (mBinding.teiClosing600.getText().toString().equals("0") && mBinding.teiOpening600.getText().toString().equals("0")) {
            TotalCard600 = 0;
        }
        int maxOpen600 = dao.mstMonthOpen200(monthMst, 600);
        int maxClose600 = dao.mstMonthClose200(monthMst, 600);
        int maxTotalCard600 = dao.mstMonthTotalCard200(monthMst, 600);
        int maxTotalSales600 = dao.mstMonthTotalAmount200(monthMst, 600);
        if (dao.mstMonthOpen200(monthMst, 600) != 0) {
            balOpen600 = dao.mstMonthCloseMax200(monthMst, 600) + 1;
            balClose600 = daoOpenClose.mstMonthBalClose200(monthMst, 600);
            maxMonthOpenCard600 = dao.mstMonthCloseMax200(monthMst, 600);
            balCard600 = daoOpenClose.mstMonthBalClose200(monthMst, 600) - maxMonthOpenCard600;
            TotalBalCard600 = dao.mstMonthTotalCard200(monthMst, 600) + balCard600;
        }
        //card640
        int TotalCard640 = Integer.parseInt(mBinding.teiClosing640.getText().toString()) - Integer.parseInt(mBinding.teiOpening640.getText().toString());
        if (TotalCard640 == 0) {
            TotalCard640 = 1;
        } else {
            TotalCard640 = Integer.parseInt(mBinding.teiClosing640.getText().toString()) - Integer.parseInt(mBinding.teiOpening640.getText().toString()) + 1;
        }
        if (mBinding.teiClosing640.getText().toString().equals("0") && mBinding.teiOpening640.getText().toString().equals("0")) {
            TotalCard640 = 0;
        }
        int maxOpen640 = dao.mstMonthOpen200(monthMst, 640);
        int maxClose640 = dao.mstMonthClose200(monthMst, 640);
        int maxTotalCard640 = dao.mstMonthTotalCard200(monthMst, 640);
        int maxTotalSales640 = dao.mstMonthTotalAmount200(monthMst, 640);
        if (dao.mstMonthOpen200(monthMst, 640) != 0) {
            balOpen640 = dao.mstMonthCloseMax200(monthMst, 640) + 1;
            balClose640 = daoOpenClose.mstMonthBalClose200(monthMst, 640);
            maxMonthOpenCard640 = dao.mstMonthCloseMax200(monthMst, 640);
            balCard640 = daoOpenClose.mstMonthBalClose200(monthMst, 640) - maxMonthOpenCard640;
            TotalBalCard640 = dao.mstMonthTotalCard200(monthMst, 600) + balCard640;
        }

        int TotalCard680 = Integer.parseInt(mBinding.teiClosing680.getText().toString()) - Integer.parseInt(mBinding.teiOpening680.getText().toString());
        if (TotalCard680 == 0) {
            TotalCard680 = 1;
        } else {
            TotalCard680 = Integer.parseInt(mBinding.teiClosing680.getText().toString()) - Integer.parseInt(mBinding.teiOpening680.getText().toString()) + 1;

        }
        if (mBinding.teiClosing680.getText().toString().equals("0") && mBinding.teiOpening680.getText().toString().equals("0")) {
            TotalCard680 = 0;
        }
        int maxOpen680 = dao.mstMonthOpen200(monthMst, 680);
        int maxClose680 = dao.mstMonthClose200(monthMst, 680);
        int maxTotalCard680 = dao.mstMonthTotalCard200(monthMst, 680);
        int maxTotalSales680 = dao.mstMonthTotalAmount200(monthMst, 680);
        if (dao.mstMonthOpen200(monthMst, 680) != 0) {
            balOpen680 = dao.mstMonthCloseMax200(monthMst, 680) + 1;
            balClose680 = daoOpenClose.mstMonthBalClose200(monthMst, 680);
            maxMonthOpenCard680 = dao.mstMonthCloseMax200(monthMst, 680);
            balCard680 = daoOpenClose.mstMonthBalClose200(monthMst, 680) - maxMonthOpenCard680;
            TotalBalCard680 = dao.mstMonthTotalCard200(monthMst, 680) + balCard680;
        }

        int TotalCard720 = Integer.parseInt(mBinding.teiClosing720.getText().toString()) - Integer.parseInt(mBinding.teiOpening720.getText().toString());
        if (TotalCard720 == 0) {
            TotalCard720 = 1;
        } else {
            TotalCard720 = Integer.parseInt(mBinding.teiClosing720.getText().toString()) - Integer.parseInt(mBinding.teiOpening720.getText().toString()) + 1;
        }
        if (mBinding.teiClosing720.getText().toString().equals("0") && mBinding.teiOpening720.getText().toString().equals("0")) {
            TotalCard720 = 0;
        }
        int maxOpen720 = dao.mstMonthOpen200(monthMst, 720);
        int maxClose720 = dao.mstMonthClose200(monthMst, 720);
        int maxTotalCard720 = dao.mstMonthTotalCard200(monthMst, 720);
        int maxTotalSales720 = dao.mstMonthTotalAmount200(monthMst, 720);
        if (dao.mstMonthOpen200(monthMst, 720) != 0) {
            balOpen720 = dao.mstMonthCloseMax200(monthMst, 720) + 1;
            balClose720 = daoOpenClose.mstMonthBalClose200(monthMst, 720);
            maxMonthOpenCard720 = dao.mstMonthCloseMax200(monthMst, 720);
            balCard720 = daoOpenClose.mstMonthBalClose200(monthMst, 720) - maxMonthOpenCard720;
            TotalBalCard720 = dao.mstMonthTotalCard200(monthMst, 720) + balCard720;
        }

        int TotalCard760 = Integer.parseInt(mBinding.teiClosing760.getText().toString()) - Integer.parseInt(mBinding.teiOpening760.getText().toString());
        if (TotalCard760 == 0) {
            TotalCard760 = 1;
        } else {
            TotalCard760 = Integer.parseInt(mBinding.teiClosing760.getText().toString()) - Integer.parseInt(mBinding.teiOpening760.getText().toString()) + 1;
        }
        if (mBinding.teiClosing760.getText().toString().equals("0") && mBinding.teiOpening760.getText().toString().equals("0")) {
            TotalCard760 = 0;
        }
        int maxOpen760 = dao.mstMonthOpen200(monthMst, 760);
        int maxClose760 = dao.mstMonthClose200(monthMst, 760);
        int maxTotalCard760 = dao.mstMonthTotalCard200(monthMst, 760);
        int maxTotalSales760 = dao.mstMonthTotalAmount200(monthMst, 760);
        if (dao.mstMonthOpen200(monthMst, 760) != 0) {
            balOpen760 = dao.mstMonthCloseMax200(monthMst, 760) + 1;
            balClose760 = daoOpenClose.mstMonthBalClose200(monthMst, 760);
            maxMonthOpenCard760 = dao.mstMonthCloseMax200(monthMst, 760);
            balCard760 = daoOpenClose.mstMonthBalClose200(monthMst, 760) - maxMonthOpenCard760;
            TotalBalCard760 = dao.mstMonthTotalCard200(monthMst, 760) + balCard760;
        }

        MstOpeningClosing MstOpeningClosing200 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening.getText().toString()), Integer.parseInt(mBinding.teiClosing.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue.getText().toString()), spare200, key200, TotalCard200, monthMst, yearMst, maxOpen200, maxClose200, maxTotalCard, maxTotalSales, balOpen200, balClose200, balCard200, TotalBalCard200);

        MstOpeningClosing MstOpeningClosing240 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening240.getText().toString()), Integer.parseInt(mBinding.teiClosing240.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue240.getText().toString()), spare240, key240, TotalCard240, monthMst, yearMst, maxOpen240, maxClose240, maxTotalCard240, maxTotalSales240, balOpen240, balClose240, balCard240, TotalBalCard240);

        MstOpeningClosing MstOpeningClosing280 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening280.getText().toString()), Integer.parseInt(mBinding.teiClosing280.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue280.getText().toString()), spare280, key280, TotalCard280, monthMst, yearMst, maxOpen280, maxClose280, maxTotalCard280, maxTotalSales280, balOpen280, balClose280, balCard280, TotalBalCard280);

        MstOpeningClosing MstOpeningClosing320 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening320.getText().toString()), Integer.parseInt(mBinding.teiClosing320.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue320.getText().toString()), spare320, key320, TotalCard320, monthMst, yearMst, maxOpen320, maxClose320, maxTotalCard320, maxTotalSales320, balOpen320, balClose320, balCard320, TotalBalCard320);

        MstOpeningClosing MstOpeningClosing360 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening360.getText().toString()), Integer.parseInt(mBinding.teiClosing360.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue360.getText().toString()), spare360, key360, TotalCard360, monthMst, yearMst, maxOpen360, maxClose360, maxTotalCard360, maxTotalSales360, balOpen360, balClose360, balCard360, TotalBalCard360);

        MstOpeningClosing MstOpeningClosing400 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening400.getText().toString()), Integer.parseInt(mBinding.teiClosing400.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue400.getText().toString()), spare400, key400, TotalCard400, monthMst, yearMst, maxOpen400, maxClose400, maxTotalCard400, maxTotalSales400, balOpen400, balClose400, balCard400, TotalBalCard400);

        MstOpeningClosing MstOpeningClosing440 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening440.getText().toString()), Integer.parseInt(mBinding.teiClosing440.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue440.getText().toString()), spare440, key440, TotalCard440, monthMst, yearMst, maxOpen440, maxClose440, maxTotalCard440, maxTotalSales440, balOpen440, balClose440, balCard440, TotalBalCard440);

        MstOpeningClosing MstOpeningClosing480 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening480.getText().toString()), Integer.parseInt(mBinding.teiClosing480.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue480.getText().toString()), spare480, key480, TotalCard480, monthMst, yearMst, maxOpen480, maxClose480, maxTotalCard480, maxTotalSales480, balOpen480, balClose480, balCard480, TotalBalCard480);

        MstOpeningClosing MstOpeningClosing520 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening520.getText().toString()), Integer.parseInt(mBinding.teiClosing520.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue520.getText().toString()), spare520, key520, TotalCard520, monthMst, yearMst, maxOpen520, maxClose520, maxTotalCard520, maxTotalSales520, balOpen520, balClose520, balCard520, TotalBalCard520);

        MstOpeningClosing MstOpeningClosing560 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening560.getText().toString()), Integer.parseInt(mBinding.teiClosing560.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue560.getText().toString()), spare560, key560, TotalCard560, monthMst, yearMst, maxOpen560, maxClose560, maxTotalCard560, maxTotalSales560, balOpen560, balClose560, balCard560, TotalBalCard560);

        MstOpeningClosing MstOpeningClosing600 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening600.getText().toString()), Integer.parseInt(mBinding.teiClosing600.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue600.getText().toString()), spare600, key600, TotalCard600, monthMst, yearMst, maxOpen600, maxClose600, maxTotalCard600, maxTotalSales600, balOpen600, balClose600, balCard600, TotalBalCard600);

        MstOpeningClosing MstOpeningClosing640 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening640.getText().toString()), Integer.parseInt(mBinding.teiClosing640.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue640.getText().toString()), spare640, key640, TotalCard640, monthMst, yearMst, maxOpen640, maxClose640, maxTotalCard640, maxTotalSales640, balOpen640, balClose640, balCard640, TotalBalCard640);

        MstOpeningClosing MstOpeningClosing680 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening680.getText().toString()), Integer.parseInt(mBinding.teiClosing680.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue680.getText().toString()), spare680, key680, TotalCard680, monthMst, yearMst, maxOpen680, maxClose680, maxTotalCard680, maxTotalSales680, balOpen680, balClose680, balCard680, TotalBalCard680);

        MstOpeningClosing MstOpeningClosing720 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening720.getText().toString()), Integer.parseInt(mBinding.teiClosing720.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue720.getText().toString()), spare720, key720, TotalCard720, monthMst, yearMst, maxOpen720, maxClose720, maxTotalCard720, maxTotalSales720, balOpen720, balClose720, balCard720, TotalBalCard720);

        MstOpeningClosing MstOpeningClosing760 = new MstOpeningClosing(Date, Integer.parseInt(mBinding.teiOpening760.getText().toString()), Integer.parseInt(mBinding.teiClosing760.getText().toString()),
                Integer.parseInt(mBinding.teiCardValue760.getText().toString()), spare760, key760, TotalCard760, monthMst, yearMst, maxOpen760, maxClose760, maxTotalCard760, maxTotalSales760, balOpen760, balClose760, balCard760, TotalBalCard760);
        List<MstOpeningClosing> MstOpeningClosingList = new ArrayList<>();
        MstOpeningClosingList.add(MstOpeningClosing200);
        MstOpeningClosingList.add(MstOpeningClosing240);
        MstOpeningClosingList.add(MstOpeningClosing280);
        MstOpeningClosingList.add(MstOpeningClosing320);
        MstOpeningClosingList.add(MstOpeningClosing360);
        MstOpeningClosingList.add(MstOpeningClosing400);
        MstOpeningClosingList.add(MstOpeningClosing440);
        MstOpeningClosingList.add(MstOpeningClosing480);
        MstOpeningClosingList.add(MstOpeningClosing520);
        MstOpeningClosingList.add(MstOpeningClosing560);
        MstOpeningClosingList.add(MstOpeningClosing600);
        MstOpeningClosingList.add(MstOpeningClosing640);
        MstOpeningClosingList.add(MstOpeningClosing680);
        MstOpeningClosingList.add(MstOpeningClosing720);
        MstOpeningClosingList.add(MstOpeningClosing760);
        updateMstMonthEntryToDb(MstOpeningClosingList);

    }

    private void MstEntry() {

        long Date = Long.valueOf(new SimpleDateFormat("ddMMyyyy").format(new Date()));
        monthMst = mBinding.monthMst.getText().toString();
        String yearMst = mBinding.yearMst.getText().toString();
        String dateMst = mAppClass.getCurrentDateTime();
        spare200 = preferences.getInt("spare200", 0);
        spare240 = preferences.getInt("spare240", 0);
        spare280 = preferences.getInt("spare280", 0);
        spare320 = preferences.getInt("spare320", 0);
        spare360 = preferences.getInt("spare360", 0);
        spare400 = preferences.getInt("spare400", 0);
        spare440 = preferences.getInt("spare440", 0);
        spare480 = preferences.getInt("spare480", 0);
        spare520 = preferences.getInt("spare520", 0);
        spare560 = preferences.getInt("spare560", 0);
        spare600 = preferences.getInt("spare600", 0);
        spare640 = preferences.getInt("spare640", 0);
        spare680 = preferences.getInt("spare680", 0);
        spare720 = preferences.getInt("spare720", 0);
        spare760 = preferences.getInt("spare760", 0);
        key200 = preferences.getString("key200", "");
        key240 = preferences.getString("key240", "");
        key280 = preferences.getString("key280", "");
        key320 = preferences.getString("key320", "");
        key360 = preferences.getString("key360", "");
        key400 = preferences.getString("key400", "");
        key440 = preferences.getString("key440", "");
        key480 = preferences.getString("key480", "");
        key520 = preferences.getString("key520", "");
        key560 = preferences.getString("key560", "");
        key600 = preferences.getString("key600", "");
        key640 = preferences.getString("key640", "");
        key680 = preferences.getString("key680", "");
        key720 = preferences.getString("key720", "");
        key760 = preferences.getString("key760", "");

        //card200
        int TotalCard200 = Integer.parseInt(mBinding.teiClosing.getText().toString()) - Integer.parseInt(mBinding.teiOpening.getText().toString());
        if (TotalCard200 == 0) {
            TotalCard200 = 1;
        } else {
            TotalCard200 = Integer.parseInt(mBinding.teiClosing.getText().toString()) - Integer.parseInt(mBinding.teiOpening.getText().toString()) + 1;
        }
        if (mBinding.teiClosing.getText().toString().equals("0") && mBinding.teiOpening.getText().toString().equals("0")) {
            TotalCard200 = 0;
        }
        int totalAmount200 = TotalCard200 * Integer.parseInt(mBinding.teiCardValue.getText().toString());
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
        //card440
        int TotalCard440 = Integer.parseInt(mBinding.teiClosing440.getText().toString()) - Integer.parseInt(mBinding.teiOpening440.getText().toString());
        if (TotalCard440 == 0) {
            TotalCard440 = 1;
        } else {
            TotalCard440 = Integer.parseInt(mBinding.teiClosing440.getText().toString()) - Integer.parseInt(mBinding.teiOpening440.getText().toString()) + 1;
        }
        if (mBinding.teiClosing440.getText().toString().equals("0") && mBinding.teiOpening440.getText().toString().equals("0")) {
            TotalCard440 = 0;
        }
        int totalAmount440 = TotalCard440 * Integer.parseInt(mBinding.teiCardValue440.getText().toString());
        //card480
        int TotalCard480 = Integer.parseInt(mBinding.teiClosing480.getText().toString()) - Integer.parseInt(mBinding.teiOpening480.getText().toString());
        if (TotalCard480 == 0) {
            TotalCard480 = 1;
        } else {
            TotalCard480 = Integer.parseInt(mBinding.teiClosing480.getText().toString()) - Integer.parseInt(mBinding.teiOpening480.getText().toString()) + 1;
        }
        if (mBinding.teiClosing480.getText().toString().equals("0") && mBinding.teiOpening480.getText().toString().equals("0")) {
            TotalCard480 = 0;
        }
        int totalAmount480 = TotalCard480 * Integer.parseInt(mBinding.teiCardValue480.getText().toString());
        //card520
        int TotalCard520 = Integer.parseInt(mBinding.teiClosing520.getText().toString()) - Integer.parseInt(mBinding.teiOpening520.getText().toString());
        if (TotalCard520 == 0) {
            TotalCard520 = 1;
        } else {
            TotalCard520 = Integer.parseInt(mBinding.teiClosing520.getText().toString()) - Integer.parseInt(mBinding.teiOpening520.getText().toString()) + 1;
        }
        if (mBinding.teiClosing520.getText().toString().equals("0") && mBinding.teiOpening520.getText().toString().equals("0")) {
            TotalCard520 = 0;
        }
        int totalAmount520 = TotalCard520 * Integer.parseInt(mBinding.teiCardValue520.getText().toString());

        //card560
        int TotalCard560 = Integer.parseInt(mBinding.teiClosing560.getText().toString()) - Integer.parseInt(mBinding.teiOpening560.getText().toString());
        if (TotalCard560 == 0) {
            TotalCard560 = 1;
        } else {
            TotalCard560 = Integer.parseInt(mBinding.teiClosing560.getText().toString()) - Integer.parseInt(mBinding.teiOpening560.getText().toString()) + 1;
        }
        if (mBinding.teiClosing560.getText().toString().equals("0") && mBinding.teiOpening560.getText().toString().equals("0")) {
            TotalCard560 = 0;
        }
        int totalAmount560 = TotalCard560 * Integer.parseInt(mBinding.teiCardValue560.getText().toString());
        //card600
        int TotalCard600 = Integer.parseInt(mBinding.teiClosing600.getText().toString()) - Integer.parseInt(mBinding.teiOpening600.getText().toString());
        if (TotalCard600 == 0) {
            TotalCard600 = 1;
        } else {
            TotalCard600 = Integer.parseInt(mBinding.teiClosing600.getText().toString()) - Integer.parseInt(mBinding.teiOpening600.getText().toString()) + 1;
        }
        if (mBinding.teiClosing600.getText().toString().equals("0") && mBinding.teiOpening600.getText().toString().equals("0")) {
            TotalCard600 = 0;
        }
        int totalAmount600 = TotalCard600 * Integer.parseInt(mBinding.teiCardValue600.getText().toString());
        //card640
        int TotalCard640 = Integer.parseInt(mBinding.teiClosing640.getText().toString()) - Integer.parseInt(mBinding.teiOpening640.getText().toString());
        if (TotalCard640 == 0) {
            TotalCard640 = 1;
        } else {
            TotalCard640 = Integer.parseInt(mBinding.teiClosing640.getText().toString()) - Integer.parseInt(mBinding.teiOpening640.getText().toString()) + 1;
        }
        if (mBinding.teiClosing640.getText().toString().equals("0") && mBinding.teiOpening640.getText().toString().equals("0")) {
            TotalCard640 = 0;
        }
        int totalAmount640 = TotalCard640 * Integer.parseInt(mBinding.teiCardValue640.getText().toString());
        //card680
        int TotalCard680 = Integer.parseInt(mBinding.teiClosing680.getText().toString()) - Integer.parseInt(mBinding.teiOpening680.getText().toString());
        if (TotalCard680 == 0) {
            TotalCard680 = 1;
        } else {
            TotalCard680 = Integer.parseInt(mBinding.teiClosing680.getText().toString()) - Integer.parseInt(mBinding.teiOpening680.getText().toString()) + 1;
        }
        if (mBinding.teiClosing680.getText().toString().equals("0") && mBinding.teiOpening680.getText().toString().equals("0")) {
            TotalCard680 = 0;
        }
        int totalAmount680 = TotalCard680 * Integer.parseInt(mBinding.teiCardValue680.getText().toString());
        //card720
        int TotalCard720 = Integer.parseInt(mBinding.teiClosing720.getText().toString()) - Integer.parseInt(mBinding.teiOpening720.getText().toString());
        if (TotalCard720 == 0) {
            TotalCard720 = 1;
        } else {
            TotalCard720 = Integer.parseInt(mBinding.teiClosing720.getText().toString()) - Integer.parseInt(mBinding.teiOpening720.getText().toString()) + 1;
        }
        if (mBinding.teiClosing720.getText().toString().equals("0") && mBinding.teiOpening720.getText().toString().equals("0")) {
            TotalCard720 = 0;
        }
        int totalAmount720 = TotalCard720 * Integer.parseInt(mBinding.teiCardValue720.getText().toString());
        //card760
        int TotalCard760 = Integer.parseInt(mBinding.teiClosing760.getText().toString()) - Integer.parseInt(mBinding.teiOpening760.getText().toString());
        if (TotalCard760 == 0) {
            TotalCard760 = 1;
        } else {
            TotalCard760 = Integer.parseInt(mBinding.teiClosing760.getText().toString()) - Integer.parseInt(mBinding.teiOpening760.getText().toString()) + 1;
        }
        if (mBinding.teiClosing760.getText().toString().equals("0") && mBinding.teiOpening760.getText().toString().equals("0")) {
            TotalCard760 = 0;
        }
        int totalAmount760 = TotalCard760 * Integer.parseInt(mBinding.teiCardValue760.getText().toString());

        MstEntity entity200 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue.getText().toString()), spare200, key200,
                Integer.parseInt(mBinding.teiOpening.getText().toString()), Integer.parseInt(mBinding.teiClosing.getText().toString()), monthMst, yearMst, TotalCard200, totalAmount200, dateMst);

        MstEntity entity240 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue240.getText().toString()), spare240, key240,
                Integer.parseInt(mBinding.teiOpening240.getText().toString()), Integer.parseInt(mBinding.teiClosing240.getText().toString()), monthMst, yearMst, TotalCard240, totalAmount240, dateMst);

        MstEntity entity280 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue280.getText().toString()), spare280, key280,
                Integer.parseInt(mBinding.teiOpening280.getText().toString()), Integer.parseInt(mBinding.teiClosing280.getText().toString()), monthMst, yearMst, TotalCard280, totalAmount280, dateMst);

        MstEntity entity320 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue320.getText().toString()), spare320, key320,
                Integer.parseInt(mBinding.teiOpening320.getText().toString()), Integer.parseInt(mBinding.teiClosing320.getText().toString()), monthMst, yearMst, TotalCard320, totalAmount320, dateMst);

        MstEntity entity360 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue360.getText().toString()), spare360, key360,
                Integer.parseInt(mBinding.teiOpening360.getText().toString()), Integer.parseInt(mBinding.teiClosing360.getText().toString()), monthMst, yearMst, TotalCard360, totalAmount360, dateMst);

        MstEntity entity400 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue400.getText().toString()), spare400, key400,
                Integer.parseInt(mBinding.teiOpening400.getText().toString()), Integer.parseInt(mBinding.teiClosing400.getText().toString()), monthMst, yearMst, TotalCard400, totalAmount400, dateMst);

        MstEntity entity440 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue440.getText().toString()), spare440, key440,
                Integer.parseInt(mBinding.teiOpening440.getText().toString()), Integer.parseInt(mBinding.teiClosing440.getText().toString()), monthMst, yearMst, TotalCard440, totalAmount440, dateMst);

        MstEntity entity480 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue480.getText().toString()), spare480, key480,
                Integer.parseInt(mBinding.teiOpening480.getText().toString()), Integer.parseInt(mBinding.teiClosing480.getText().toString()), monthMst, yearMst, TotalCard480, totalAmount480, dateMst);

        MstEntity entity520 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue520.getText().toString()), spare520, key520,
                Integer.parseInt(mBinding.teiOpening520.getText().toString()), Integer.parseInt(mBinding.teiClosing520.getText().toString()), monthMst, yearMst, TotalCard520, totalAmount520, dateMst);

        MstEntity entity560 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue560.getText().toString()), spare560, key560,
                Integer.parseInt(mBinding.teiOpening560.getText().toString()), Integer.parseInt(mBinding.teiClosing560.getText().toString()), monthMst, yearMst, TotalCard560, totalAmount560, dateMst);

        MstEntity entity600 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue600.getText().toString()), spare600, key600,
                Integer.parseInt(mBinding.teiOpening600.getText().toString()), Integer.parseInt(mBinding.teiClosing600.getText().toString()), monthMst, yearMst, TotalCard600, totalAmount600, dateMst);

        MstEntity entity640 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue640.getText().toString()), spare640, key640,
                Integer.parseInt(mBinding.teiOpening640.getText().toString()), Integer.parseInt(mBinding.teiClosing640.getText().toString()), monthMst, yearMst, TotalCard640, totalAmount640, dateMst);

        MstEntity entity680 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue680.getText().toString()), spare680, key680,
                Integer.parseInt(mBinding.teiOpening680.getText().toString()), Integer.parseInt(mBinding.teiClosing680.getText().toString()), monthMst, yearMst, TotalCard680, totalAmount680, dateMst);

        MstEntity entity720 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue720.getText().toString()), spare720, key720,
                Integer.parseInt(mBinding.teiOpening720.getText().toString()), Integer.parseInt(mBinding.teiClosing720.getText().toString()), monthMst, yearMst, TotalCard720, totalAmount720, dateMst);

        MstEntity entity760 = new MstEntity(Date, Integer.parseInt(mBinding.teiCardValue760.getText().toString()), spare760, key760,
                Integer.parseInt(mBinding.teiOpening760.getText().toString()), Integer.parseInt(mBinding.teiClosing760.getText().toString()), monthMst, yearMst, TotalCard760, totalAmount760, dateMst);
        List<MstEntity> entryList = new ArrayList<>();
        entryList.add(entity200);
        entryList.add(entity240);
        entryList.add(entity280);
        entryList.add(entity320);
        entryList.add(entity360);
        entryList.add(entity400);
        entryList.add(entity440);
        entryList.add(entity480);
        entryList.add(entity520);
        entryList.add(entity560);
        entryList.add(entity600);
        entryList.add(entity640);
        entryList.add(entity680);
        entryList.add(entity720);
        entryList.add(entity760);
        updateMstEntryToDb(entryList);

    }

    public void updateMstEntryToDb(List<MstEntity> entryList) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(getContext());
        MstDao dao = db.mstDao();
        dao.insertMstEntry(entryList.toArray(new MstEntity[0]));
        mAppClass.showSnackBar(getContext(), "Updated successfully");
    }

    public void updateMstMonthEntryToDb(List<MstOpeningClosing> entryList) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(getContext());
        MstOpeningDao dao = db.OpeningDao();
        dao.insertMstMonth(entryList.toArray(new MstOpeningClosing[0]));
        mAppClass.showSnackBar(getContext(), "Updated successfully");
    }

    private boolean validation() {
        if (mBinding.teiOpening.getText().toString().isEmpty()) {
            mBinding.teiOpening.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing.getText().toString().isEmpty()) {
            mBinding.teiClosing.setError(" Closing is Empty");
            return false;
        } else if ((Integer.parseInt(mBinding.teiOpening.getText().toString())
                > Integer.parseInt(mBinding.teiClosing.getText().toString()))) {
            mBinding.teiOpening.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening240.getText().toString().isEmpty()) {
            mBinding.teiOpening240.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing240.getText().toString().isEmpty()) {
            mBinding.teiClosing240.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening240.getText().toString())
                > Integer.parseInt(mBinding.teiClosing240.getText().toString())) {
            mBinding.teiOpening240.setError("Opening is Greater than Closing");
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
        } else if (mBinding.teiOpening440.getText().toString().isEmpty()) {
            mBinding.teiOpening440.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing440.getText().toString().isEmpty()) {
            mBinding.teiClosing440.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening440.getText().toString())
                > Integer.parseInt(mBinding.teiClosing440.getText().toString())) {
            mBinding.teiOpening440.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening480.getText().toString().isEmpty()) {
            mBinding.teiOpening400.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing480.getText().toString().isEmpty()) {
            mBinding.teiClosing480.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening480.getText().toString())
                > Integer.parseInt(mBinding.teiClosing480.getText().toString())) {
            mBinding.teiOpening480.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening520.getText().toString().isEmpty()) {
            mBinding.teiOpening520.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing520.getText().toString().isEmpty()) {
            mBinding.teiClosing520.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening520.getText().toString())
                > Integer.parseInt(mBinding.teiClosing520.getText().toString())) {
            mBinding.teiOpening520.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening560.getText().toString().isEmpty()) {
            mBinding.teiOpening560.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing560.getText().toString().isEmpty()) {
            mBinding.teiClosing560.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening560.getText().toString())
                > Integer.parseInt(mBinding.teiClosing560.getText().toString())) {
            mBinding.teiOpening560.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening600.getText().toString().isEmpty()) {
            mBinding.teiOpening600.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing600.getText().toString().isEmpty()) {
            mBinding.teiClosing600.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening600.getText().toString())
                > Integer.parseInt(mBinding.teiClosing600.getText().toString())) {
            mBinding.teiOpening600.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening640.getText().toString().isEmpty()) {
            mBinding.teiOpening640.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing640.getText().toString().isEmpty()) {
            mBinding.teiClosing640.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening640.getText().toString())
                > Integer.parseInt(mBinding.teiClosing640.getText().toString())) {
            mBinding.teiOpening640.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening680.getText().toString().isEmpty()) {
            mBinding.teiOpening680.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing680.getText().toString().isEmpty()) {
            mBinding.teiClosing680.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening680.getText().toString())
                > Integer.parseInt(mBinding.teiClosing680.getText().toString())) {
            mBinding.teiOpening680.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening720.getText().toString().isEmpty()) {
            mBinding.teiOpening720.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing720.getText().toString().isEmpty()) {
            mBinding.teiClosing720.setError(" Closing is Empty");
            return false;

        } else if (Integer.parseInt(mBinding.teiOpening720.getText().toString())
                > Integer.parseInt(mBinding.teiClosing720.getText().toString())) {
            mBinding.teiOpening720.setError("Opening is Greater than Closing");
            return false;
        } else if (mBinding.teiOpening760.getText().toString().isEmpty()) {
            mBinding.teiOpening760.setError("Opening  is Empty");
            return false;
        } else if (mBinding.teiClosing760.getText().toString().isEmpty()) {
            mBinding.teiClosing760.setError(" Closing is Empty");
            return false;
        } else if (Integer.parseInt(mBinding.teiOpening760.getText().toString())
                > Integer.parseInt(mBinding.teiClosing760.getText().toString())) {
            mBinding.teiOpening760.setError("Opening is Greater than Closing");
            return false;
        }

        return true;
    }

}
