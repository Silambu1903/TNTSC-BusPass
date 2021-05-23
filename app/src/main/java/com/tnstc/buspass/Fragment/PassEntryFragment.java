package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.PassentryBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.tnstc.buspass.Others.ApplicationClass.EXP_DEL;
import static com.tnstc.buspass.Others.ApplicationClass.FORM_BUS_LIST;
import static com.tnstc.buspass.Others.ApplicationClass.NEW_OLD_LIST;

public class PassEntryFragment extends Fragment {
    PassentryBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    TnstcBusPassDB db;
    PassDao dao;
    public List<PassEntity> passEntityList;
    List<PassEntity> getIdList;
    int id;



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

        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.passDao();
        mBinding.month.setText((String) android.text.format.DateFormat.format("MMMM", new Date()));
        mBinding.year.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        mBinding.textView.setText(mAppClass.getCurrentDateTime());
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        materialDateBuilder.setTheme(R.style.Canlder);
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        initialValueset();
        adapterForAutoComplete();
        totalAmount();
        getDataFromDb();
        mBinding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");

            }
        });


        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    GetPassEntryDetails();
                }

            }
        });
    }

    private void getDataFromDb() {
        mBinding.teiIno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!mBinding.teiIno.getText().toString().equals("")) {
                    id = Integer.parseInt(mBinding.teiIno.getText().toString());
                    getIdList = new ArrayList<>();
                    getIdList = dao.getIdData(id);
                    for (int i = 0; i < getIdList.size(); i++) {
                        mBinding.teiName.setText(getIdList.get(i).getName());
                        mBinding.actForm.setText(getIdList.get(i).getFromArea());
                        mBinding.actTo.setText(getIdList.get(i).getToArea());
                        mBinding.actNewOld.setText(getIdList.get(i).getNewOld());
                        mBinding.teiBusFare.setText(getIdList.get(i).getBusFare() + "");
                        mBinding.actExpDel.setText(getIdList.get(i).getExpDel());
                        mBinding.teiCellNumber.setText(getIdList.get(i).getCellNumber());
                    }
                } else if (mBinding.teiIno.getText().toString().equals("")) {
                    mBinding.teiName.setText("");
                    mBinding.actForm.setText("");
                    mBinding.actTo.setText("");
                    mBinding.actNewOld.setText("");
                    mBinding.teiBusFare.setText("");
                    mBinding.actExpDel.setText("");
                    mBinding.teiCellNumber.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initialValueset() {
        int sno = dao.lastSno() + 1;
        mBinding.teiSno.setText(sno + "");
        int repNo = dao.lastRepno();
        if (repNo == 0) {
            mBinding.teiRepno.setText(repNo + "");
        } else {
            repNo = dao.lastRepno() + 1;
            mBinding.teiRepno.setText(repNo + "");
        }
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

    @Override
    public void onStop() {
        super.onStop();

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
        PassEntity entry = new PassEntity(Integer.parseInt(mBinding.teiSno.getText().toString()),
                Integer.parseInt(mBinding.teiIno.getText().toString()), Integer.parseInt(mBinding.teiRepno.getText().toString()),
                mBinding.actNewOld.getText().toString(), mBinding.textView.getText().toString(), mBinding.teiName.getText().toString(),
                mBinding.actForm.getText().toString(), mBinding.actTo.getText().toString(), Integer.parseInt(mBinding.teiBusFare.getText().toString()),
                Integer.parseInt(mBinding.txtTotalAmount.getText().toString()), mBinding.actExpDel.getText().toString(), mBinding.teiCellNumber.getText().toString()
                , mBinding.month.getText().toString(), mBinding.year.getText().toString());
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

    public boolean validation() {
        if (mBinding.teiSno.getText().toString().equals("")) {
            mBinding.teiSno.requestFocus();
            mBinding.teiSno.setError("Sno is empty");
            return false;
        } else if (mBinding.teiIno.getText().toString().equals("")) {
            mBinding.teiIno.requestFocus();
            mBinding.teiIno.setError("Idno is empty");
            return false;
        } else if (mBinding.teiRepno.getText().toString().equals("")) {
            mBinding.teiRepno.requestFocus();
            mBinding.teiRepno.setError("Repno is empty");
            return false;
        } else if (mBinding.actNewOld.getText().toString().equals("")) {
            mBinding.teiRepno.requestFocus();
            mBinding.teiRepno.setError("NewOld is empty");
            return false;
        } else if (mBinding.teiName.getText().toString().equals("")) {
            mBinding.teiName.requestFocus();
            mBinding.teiName.setError("Name is empty");
            return false;
        } else if (mBinding.actForm.getText().toString().equals("")) {
            mBinding.actForm.requestFocus();
            mBinding.actForm.setError("Form is empty");
            return false;
        } else if (mBinding.actTo.getText().toString().equals("")) {
            mBinding.actTo.requestFocus();
            mBinding.actTo.setError("To is empty");
            return false;
        } else if (mBinding.teiBusFare.getText().toString().equals("")) {
            mBinding.teiBusFare.requestFocus();
            mBinding.teiBusFare.setError("BusFare is empty");
            return false;
        } else if (mBinding.actExpDel.getText().toString().equals("")) {
            mBinding.actExpDel.requestFocus();
            mBinding.actExpDel.setError("ExpDel is empty");
            return false;
        } else if (mBinding.teiCellNumber.getText().toString().equals("")) {
            mBinding.teiCellNumber.requestFocus();
            mBinding.teiCellNumber.setError("CellNumber is empty");
            return false;
        }
        return true;
    }
}
