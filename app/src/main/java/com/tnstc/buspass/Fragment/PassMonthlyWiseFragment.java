package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.users_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_download) {
            if (!(passEntityList ==null)){
                excelWorkBookWrite();
            }else {
                mAppClass.showSnackBar(getContext(),"PassDetail is Empty");
            }

        }else if (item.getItemId() == R.id.action_open) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                    +  File.separator + "TNSTC BUS PASS DETAILS" + File.separator);
            intent.setDataAndType(uri, "*/*");
            startActivity(Intent.createChooser(intent, "TNSTC BUS PASS DETAILS"));
        } else {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.onSupportNavigateUp();
        }
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        View windowDecorView = requireActivity().getWindow().getDecorView();
        windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
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

    private void excelWorkBookWrite() {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet(month);
        firstSheet.setFitToPage(true);
        HSSFRow row = firstSheet.createRow(0);
        HSSFCell cell1 = row.createCell(0);
        firstSheet.setColumnWidth(0, 25 * 60);
        HSSFCell cell2 = row.createCell(1);
        firstSheet.setColumnWidth(1, 25 * 60);
        HSSFCell cell3 = row.createCell(2);
        firstSheet.setColumnWidth(2, 25 * 70);
        HSSFCell cell4 = row.createCell(3);
        firstSheet.setColumnWidth(3, 25 * 100);
        HSSFCell cell5 = row.createCell(4);
        firstSheet.setColumnWidth(4, 25 * 100);
        HSSFCell cell6 = row.createCell(5);
        firstSheet.setColumnWidth(5, 25 * 216);
        HSSFCell cell7 = row.createCell(6);
        firstSheet.setColumnWidth(6, 25 * 100);
        HSSFCell cell8 = row.createCell(7);
        firstSheet.setColumnWidth(7, 25 * 100);
        HSSFCell cell9 = row.createCell(8);
        firstSheet.setColumnWidth(8, 25 * 100);
        HSSFCell cell10 = row.createCell(9);
        firstSheet.setColumnWidth(9, 25 * 100);
        HSSFCell cell11 = row.createCell(10);
        firstSheet.setColumnWidth(10, 25 * 100);
        HSSFCell cell12 = row.createCell(11);
        firstSheet.setColumnWidth(11, 25 * 130);
        cell1.setCellValue(new HSSFRichTextString("S.NO"));
        cell2.setCellValue(new HSSFRichTextString("ID.NO"));
        cell3.setCellValue(new HSSFRichTextString("REP NO"));
        cell4.setCellValue(new HSSFRichTextString("NEW/OLD"));
        cell5.setCellValue(new HSSFRichTextString("DATE"));
        cell6.setCellValue(new HSSFRichTextString("NAME"));
        cell7.setCellValue(new HSSFRichTextString("FORM"));
        cell8.setCellValue(new HSSFRichTextString("TO"));
        cell9.setCellValue(new HSSFRichTextString("BUSFARE"));
        cell10.setCellValue(new HSSFRichTextString("AMOUNT"));
        cell11.setCellValue(new HSSFRichTextString("EXP/DEL"));
        cell12.setCellValue(new HSSFRichTextString("CELLNUMBER"));
        for (int i = 0; i < passEntityList.size(); i++) {
            HSSFRow rowA = firstSheet.createRow(i + 1);
            rowA.createCell(0).setCellValue(passEntityList.get(i).getSno());
            rowA.createCell(1).setCellValue(passEntityList.get(i).getiNo());
            rowA.createCell(2).setCellValue(passEntityList.get(i).getRepNo());
            rowA.createCell(3).setCellValue(passEntityList.get(i).getNewOld());
            rowA.createCell(4).setCellValue(passEntityList.get(i).getDate());
            rowA.createCell(5).setCellValue(passEntityList.get(i).getName());
            rowA.createCell(6).setCellValue(passEntityList.get(i).getFromArea());
            rowA.createCell(7).setCellValue(passEntityList.get(i).getToArea());
            rowA.createCell(8).setCellValue(passEntityList.get(i).getBusFare());
            rowA.createCell(9).setCellValue(passEntityList.get(i).getAmount());
            rowA.createCell(10).setCellValue(passEntityList.get(i).getExpDel());
            rowA.createCell(11).setCellValue(passEntityList.get(i).getCellNumber());

        }
        File file;
        FileOutputStream fos = null;
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/TNSTC BUS PASS DETAILS");
            if (!mediaStorageDir.mkdirs()) {
                mediaStorageDir.mkdirs();
            }
            file = new File(mediaStorageDir + File.separator, "TNSTCBusPassMonthly" + month + year + ".xls");
            fos = new FileOutputStream(file);
            workbook.write(fos);
            mAppClass.showSnackBar(getContext(), "Excel Sheet Download Successfully");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        View windowDecorView = requireActivity().getWindow().getDecorView();
        windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
