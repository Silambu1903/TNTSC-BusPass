package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.PassentryBinding;

import java.util.ArrayList;
import java.util.List;

import static com.tnstc.buspass.Others.ApplicationClass.EXP_DEL;
import static com.tnstc.buspass.Others.ApplicationClass.FORM_BUS_LIST;
import static com.tnstc.buspass.Others.ApplicationClass.NEW_OLD_LIST;

public class PassEntryFragment extends Fragment {
    PassentryBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.passentry, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mContext = getContext();
        mBinding.textView.setText(mAppClass.getCurrentDateTime());
        adapterForAutoComplete();
        totalAmount();


        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPassEntryDetails();
            }
        });
    }

    private void totalAmount() {
        mBinding.teiBusFare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mBinding.teiBusFare.getText().toString().equals("")) {
                    mBinding.txtTotalAmount.setText("0");
                } else {
                    String busFare = (mBinding.teiBusFare.getText().toString());
                    String totalAmount = String.valueOf(Integer.parseInt(busFare) * 40);
                    mBinding.txtTotalAmount.setText(totalAmount);
                }
            }
        });
    }


    private void adapterForAutoComplete() {
        ArrayAdapter busList = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, FORM_BUS_LIST);
        ArrayAdapter newOldList = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, NEW_OLD_LIST);
        ArrayAdapter expDel = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, EXP_DEL);
        mBinding.actForm.setAdapter(busList);
        mBinding.actTo.setAdapter(busList);
        mBinding.actNewOld.setAdapter(newOldList);
        mBinding.actExpDel.setAdapter(expDel);
    }

    private void GetPassEntryDetails() {
        mBinding.teiSno.getText().toString();
        mBinding.teiIno.getText().toString();
        mBinding.teiRepno.getText().toString();
        mBinding.actNewOld.getText().toString();
        mBinding.textView.getText().toString();
        mBinding.teiName.getText().toString();
        mBinding.actForm.getText().toString();
        mBinding.actTo.getText().toString();
        mBinding.teiBusFare.getText().toString();
        mBinding.txtTotalAmount.getText().toString();
        mBinding.actExpDel.getText().toString();
        mBinding.teiCellNumber.getText().toString();

        PassEntity entry = new PassEntity(Integer.parseInt(mBinding.teiSno.getText().toString()),
                Integer.parseInt(mBinding.teiIno.getText().toString()),
                Integer.parseInt(mBinding.teiRepno.getText().toString()),
                mBinding.actNewOld.getText().toString(),
                mBinding.textView.getText().toString(),
                mBinding.teiName.getText().toString(),
                mBinding.actForm.getText().toString(),
                mBinding.actTo.getText().toString(),
                Integer.parseInt(mBinding.teiBusFare.getText().toString()),
                Integer.parseInt(mBinding.txtTotalAmount.getText().toString()),
                mBinding.actExpDel.getText().toString(),
                mBinding.teiCellNumber.getText().toString());
        List<PassEntity> entryList = new ArrayList<>();
        entryList.add(entry);
        updatePassEntryToDb(entryList);
    }

    public void updatePassEntryToDb(List<PassEntity> entryList) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(getContext());
        PassDao dao = db.passDao();
        dao.insert(entryList.toArray(new PassEntity[0]));
        Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show();
    }
}
