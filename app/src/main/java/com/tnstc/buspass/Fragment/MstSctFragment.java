package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.MstSctEntryBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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

    public int spare200, spare240, spare280, spare320, spare360, spare400, spare440, spare480,
            spare520, spare560, spare600, spare640, spare680, spare720, spare760;

    public String key200, key240, key280, key320, key360, key400, key440, key480,
            key520, key560, key600, key640, key680, key720, key760;

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
        mBinding.mstSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MstEntry();
            }
        });

    }

    private void MstEntry() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR)+now.get(Calendar.MONTH) + 1;
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int sec=year+month+day+hour+minute+second+second;
        String monthMst = mBinding.monthMst.getText().toString();
        String yearMst = mBinding.yearMst.getText().toString();
        String dateMst = mBinding.date.getText().toString();

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


        MstEntity entity200 = new MstEntity(sec,Integer.parseInt(mBinding.teiCardValue.getText().toString()), spare200, key200,
                Integer.parseInt(mBinding.teiOpening.getText().toString()), Integer.parseInt(mBinding.teiClosing.getText().toString()), monthMst, yearMst, dateMst);

      /*  MstEntity entity240 = new MstEntity(Integer.parseInt(mBinding.teiCardValue240.getText().toString()), spare240, key240,
                Integer.parseInt(mBinding.teiOpening240.getText().toString()), Integer.parseInt(mBinding.teiClosing240.getText().toString()));

        MstEntity entity280 = new MstEntity(Integer.parseInt(mBinding.teiCardValue280.getText().toString()), spare280, key280,
                Integer.parseInt(mBinding.teiOpening280.getText().toString()), Integer.parseInt(mBinding.teiClosing280.getText().toString()));

        MstEntity entity320 = new MstEntity(Integer.parseInt(mBinding.teiCardValue320.getText().toString()), spare320, key320,
                Integer.parseInt(mBinding.teiOpening.getText().toString()), Integer.parseInt(mBinding.teiClosing.getText().toString()));

        MstEntity entity360 = new MstEntity(Integer.parseInt(mBinding.teiCardValue360.getText().toString()), spare360, key360,
                Integer.parseInt(mBinding.teiOpening360.getText().toString()), Integer.parseInt(mBinding.teiClosing360.getText().toString()));

        MstEntity entity400 = new MstEntity(Integer.parseInt(mBinding.teiCardValue400.getText().toString()), spare400, key400,
                Integer.parseInt(mBinding.teiOpening400.getText().toString()), Integer.parseInt(mBinding.teiClosing400.getText().toString()));

        MstEntity entity440 = new MstEntity(Integer.parseInt(mBinding.teiCardValue440.getText().toString()), spare440, key440,
                Integer.parseInt(mBinding.teiOpening440.getText().toString()), Integer.parseInt(mBinding.teiClosing440.getText().toString()));

        MstEntity entity480 = new MstEntity(Integer.parseInt(mBinding.teiCardValue480.getText().toString()), spare480, key480,
                Integer.parseInt(mBinding.teiOpening480.getText().toString()), Integer.parseInt(mBinding.teiClosing480.getText().toString()));

        MstEntity entity520 = new MstEntity(Integer.parseInt(mBinding.teiCardValue520.getText().toString()), spare520, key520,
                Integer.parseInt(mBinding.teiOpening520.getText().toString()), Integer.parseInt(mBinding.teiClosing520.getText().toString()));

        MstEntity entity560 = new MstEntity(Integer.parseInt(mBinding.teiCardValue560.getText().toString()), spare560, key560,
                Integer.parseInt(mBinding.teiOpening560.getText().toString()), Integer.parseInt(mBinding.teiClosing560.getText().toString()));

        MstEntity entity600 = new MstEntity(Integer.parseInt(mBinding.teiCardValue600.getText().toString()), spare600, key600,
                Integer.parseInt(mBinding.teiOpening.getText().toString()), Integer.parseInt(mBinding.teiClosing.getText().toString()));

        MstEntity entity640 = new MstEntity(Integer.parseInt(mBinding.teiCardValue640.getText().toString()), spare640, key640,
                Integer.parseInt(mBinding.teiOpening640.getText().toString()), Integer.parseInt(mBinding.teiClosing640.getText().toString()));

        MstEntity entity680 = new MstEntity(Integer.parseInt(mBinding.teiCardValue680.getText().toString()), spare680, key680,
                Integer.parseInt(mBinding.teiOpening680.getText().toString()), Integer.parseInt(mBinding.teiClosing680.getText().toString()));

        MstEntity entity720 = new MstEntity(Integer.parseInt(mBinding.teiCardValue720.getText().toString()), spare720, key720,
                Integer.parseInt(mBinding.teiOpening720.getText().toString()), Integer.parseInt(mBinding.teiClosing720.getText().toString()));

        MstEntity entity760 = new MstEntity(Integer.parseInt(mBinding.teiCardValue760.getText().toString()), spare760, key760,
                Integer.parseInt(mBinding.teiOpening760.getText().toString()), Integer.parseInt(mBinding.teiClosing760.getText().toString()));*/
        List<MstEntity> entryList = new ArrayList<>();
        entryList.add(entity200);
       /* entryList.add(entity240);
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
        entryList.add(entity760);*/
        updateMstEntryToDb(entryList);

    }

    public void updateMstEntryToDb(List<MstEntity> entryList) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(getContext());
        MstDao dao = db.mstDao();
        dao.insertMstEntry(entryList.toArray(new MstEntity[0]));
        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
    }
}
