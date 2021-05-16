package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.MstMonthWiseAdapter;
import com.tnstc.buspass.Adapter.SctMonthWiseAdapter;

import com.tnstc.buspass.Database.DAOs.MstOpeningDao;
import com.tnstc.buspass.Database.DAOs.SctDao;
import com.tnstc.buspass.Database.DAOs.SctOpeningDao;
import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.Entity.SctOpeningClosing;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.SctMonthwiseBinding;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public class SctMonthWiseFragment extends Fragment implements AdapterView.OnItemClickListener {
    SctMonthwiseBinding mBinding;
    TnstcBusPassDB db;
    SctDao dao;
    SctOpeningDao sctOpeningDao;
    Context mContext;
    private SharedPreferences preferences;
    ApplicationClass mAppClass;
    List<String> getYear;
    List<String> getMonth;
    SctMonthWiseAdapter mAdapter;
    List<SctOpeningClosing> sctOpeningClosing;
    String month;
    String year;
    String currentMonth;
    String currentYear;

    int balOpen100, balClose100, TotalBalCard100, balCard100, maxMonthOpenCard100,
            balOpen120, balClose120, TotalBalCard120, balCard120, maxMonthOpenCard120,
            balOpen140, balClose140, TotalBalCard140, balCard140, maxMonthOpenCard140,
            balOpen160, balClose160, TotalBalCard160, balCard160, maxMonthOpenCard160,
            balOpen180, balClose180, TotalBalCard180, balCard180, maxMonthOpenCard180,
            balOpen200, balClose200, TotalBalCard200, balCard200, maxMonthOpenCard200,
            balOpen220, balClose220, TotalBalCard220, balCard220, maxMonthOpenCard220,
            balOpen240, balClose240, TotalBalCard240, balCard240, maxMonthOpenCard240,
            balOpen260, balClose260, TotalBalCard260, balCard260, maxMonthOpenCard260,
            balOpen280, balClose280, TotalBalCard280, balCard280, maxMonthOpenCard280,
            balOpen300, balClose300, TotalBalCard300, balCard300, maxMonthOpenCard300,
            balOpen320, balClose320, TotalBalCard320, balCard320, maxMonthOpenCard320,
            balOpen340, balClose340, TotalBalCard340, balCard340, maxMonthOpenCard340,
            balOpen360, balClose360, TotalBalCard360, balCard360, maxMonthOpenCard360,
            balOpen380, balClose380, TotalBalCard380, balCard380, maxMonthOpenCard380,
            balOpen400, balClose400, TotalBalCard400, balCard400, maxMonthOpenCard400;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.sct_monthwise, container, false);
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
            if (!(sctOpeningClosing == null)) {
                excelWorkBookWrite();
            } else {
                mAppClass.showSnackBar(getContext(), "Please Select the Month and Year");
            }

        } else if (item.getItemId() == R.id.action_open) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "TNSTC BUS PASS DETAILS" + File.separator);
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
        mContext = getContext();
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.sctDao();
        sctOpeningDao = db.sctOpeningDao();
        sctOpeningClosing = new ArrayList<>();
        if (!sctOpeningDao.getMonthWisesct("April", "2021").isEmpty()) {
            getSctEntry();
            Calendar cal = Calendar.getInstance();
            mBinding.monthListMst.setText(new SimpleDateFormat("MMMM").format(cal.getTime()) + "");
            mBinding.yearListMst.setText((Calendar.getInstance().get(Calendar.YEAR)) + "");
            year = mBinding.yearListMst.getText().toString();
            month = mBinding.monthListMst.getText().toString();
            sctOpeningClosing = sctOpeningDao.getMonthWisesct(month, year);
            mAdapter = new SctMonthWiseAdapter(sctOpeningClosing, getContext());
            mBinding.recyclerMstMonth.setLayoutManager(new LinearLayoutManager(getContext()));
            mBinding.recyclerMstMonth.setAdapter(mAdapter);
        } else {
            getSctEntry();
        }
        AdapterForMonthYear();
        mBinding.monthListMst.setOnItemClickListener(this);
        mBinding.yearListMst.setOnItemClickListener(this);
    }


    private void excelWorkBookWrite() {
        currentMonth = String.valueOf(DateFormat.format("MMMM", new Date()));
        currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet SecondSheet = workbook.createSheet("Sct" + month);
        SecondSheet.setFitToPage(true);
        HSSFRow row = SecondSheet.createRow(0);
        HSSFCell cell1 = row.createCell(0);
        SecondSheet.setColumnWidth(0, 25 * 60);
        HSSFCell cell2 = row.createCell(1);
        SecondSheet.setColumnWidth(1, 25 * 60);
        HSSFCell cell3 = row.createCell(2);
        SecondSheet.setColumnWidth(2, 25 * 70);
        HSSFCell cell4 = row.createCell(3);
        SecondSheet.setColumnWidth(3, 25 * 100);
        HSSFCell cell5 = row.createCell(4);
        SecondSheet.setColumnWidth(4, 25 * 100);
        HSSFCell cell6 = row.createCell(5);
        SecondSheet.setColumnWidth(5, 25 * 216);
        HSSFCell cell7 = row.createCell(6);
        SecondSheet.setColumnWidth(6, 25 * 100);
        HSSFCell cell8 = row.createCell(7);
        SecondSheet.setColumnWidth(7, 25 * 100);
        HSSFCell cell9 = row.createCell(8);
        SecondSheet.setColumnWidth(8, 25 * 100);
        HSSFCell cell10 = row.createCell(9);
        SecondSheet.setColumnWidth(9, 25 * 100);
        HSSFCell cell11 = row.createCell(10);
        SecondSheet.setColumnWidth(10, 25 * 100);
        HSSFCell cell12 = row.createCell(11);
        SecondSheet.setColumnWidth(11, 25 * 130);
        HSSFCell cell13 = row.createCell(12);
        SecondSheet.setColumnWidth(12, 25 * 130);
        cell1.setCellValue(new HSSFRichTextString("CARD"));
        cell2.setCellValue(new HSSFRichTextString("KEY"));
        cell3.setCellValue(new HSSFRichTextString("OPENING NO"));
        cell4.setCellValue(new HSSFRichTextString("CLOSING NO"));
        cell5.setCellValue(new HSSFRichTextString("TOTAL CARD"));
        cell6.setCellValue(new HSSFRichTextString("OPENING NO"));
        cell7.setCellValue(new HSSFRichTextString("CLOSING NO"));
        cell8.setCellValue(new HSSFRichTextString("TOTAL CARD"));
        cell9.setCellValue(new HSSFRichTextString("SCALES AMOUNT"));
        cell10.setCellValue(new HSSFRichTextString("OPENING NO"));
        cell11.setCellValue(new HSSFRichTextString("CLOSING NO"));
        cell12.setCellValue(new HSSFRichTextString("BALANCE CARD"));
        cell13.setCellValue(new HSSFRichTextString("TOTAL CARD"));
        for (int i = 0; i < sctOpeningClosing.size(); i++) {
            HSSFRow rowA = SecondSheet.createRow(i + 1);
            rowA.createCell(0).setCellValue(sctOpeningClosing.get(i).getSctCard());
            rowA.createCell(1).setCellValue(sctOpeningClosing.get(i).getSctKey());
            rowA.createCell(2).setCellValue(sctOpeningClosing.get(i).getSctOpening());
            rowA.createCell(3).setCellValue(sctOpeningClosing.get(i).getSctClosing());
            rowA.createCell(4).setCellValue(sctOpeningClosing.get(i).getSctTotal());
            rowA.createCell(5).setCellValue(sctOpeningClosing.get(i).getSctOpenMax());
            rowA.createCell(6).setCellValue(sctOpeningClosing.get(i).getSctCloseMax());
            rowA.createCell(7).setCellValue(sctOpeningClosing.get(i).getSctTotalCard());
            rowA.createCell(8).setCellValue(sctOpeningClosing.get(i).getSctTotalAmount());
            rowA.createCell(9).setCellValue(sctOpeningClosing.get(i).getSctBalOpen());
            rowA.createCell(10).setCellValue(sctOpeningClosing.get(i).getSctBalClose());
            rowA.createCell(11).setCellValue(sctOpeningClosing.get(i).getSctBalCard());
            rowA.createCell(12).setCellValue(sctOpeningClosing.get(i).getSctBalTotalCard());

        }
        FileOutputStream fos = null;
        try {
            File file;
            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TNSTC BUS PASS DETAILS");
            if (!folder.mkdirs()) {
                folder.mkdirs();
            }
            file = new File(folder + File.separator, "TNSTCSct" + month + year + ".xls");
            fos = new FileOutputStream(file);
            workbook.write(fos);
            mAppClass.showSnackBar(getContext(), "Excel Sheet Download Successfully");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "" + e, Toast.LENGTH_SHORT).show();
            Log.e("TAG", "excelWorkBookWrite: " + e);
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


    private void AdapterForMonthYear() {
        getMonth = new ArrayList<>();
        getYear = new ArrayList<>();
        getYear = sctOpeningDao.sctGetYear();
        getMonth = sctOpeningDao.sctGetMonth();
        LinkedHashSet<String> monthSet = new LinkedHashSet<>();
        monthSet.addAll(getMonth);
        getMonth.clear();
        getMonth.addAll(monthSet);
        LinkedHashSet<String> yearSet = new LinkedHashSet<>();
        yearSet.addAll(getYear);
        getYear.clear();
        getYear.addAll(yearSet);
        ArrayAdapter yearList = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getYear);
        mBinding.yearListMst.setAdapter(yearList);
        ArrayAdapter monthList = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, getMonth);
        mBinding.monthListMst.setAdapter(monthList);
    }

    private void getSctEntry() {
        //card200
        String monthMst = String.valueOf(DateFormat.format("MMMM", new Date()));
        String yearMst = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
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

        //Card340
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

        //Card360
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

        //Card380
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

        //card400
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

        SctOpeningClosing SctOpeningClosing100 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 100),
                sctOpeningDao.sctOpening(monthMst, 100), sctOpeningDao.sctClosing(monthMst, 100),
                sctOpeningDao.sctCard(monthMst, 100), sctOpeningDao.sctSpare(monthMst, 200), sctOpeningDao.sctKey(monthMst, 100),
                sctOpeningDao.sctTotal(monthMst, 100), monthMst, yearMst, maxOpen100, maxClose100,
                maxTotalCard100, maxTotalSales100, balOpen100, balClose100, balCard100, TotalBalCard100);

        SctOpeningClosing SctOpeningClosing120 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 120),
                sctOpeningDao.sctOpening(monthMst, 120), sctOpeningDao.sctClosing(monthMst, 120),
                sctOpeningDao.sctCard(monthMst, 120), sctOpeningDao.sctSpare(monthMst, 120), sctOpeningDao.sctKey(monthMst, 120),
                sctOpeningDao.sctTotal(monthMst, 120), monthMst, yearMst, maxOpen120, maxClose120,
                maxTotalCard120, maxTotalSales120, balOpen120, balClose120, balCard120, TotalBalCard120);

        SctOpeningClosing SctOpeningClosing140 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 140),
                sctOpeningDao.sctOpening(monthMst, 140), sctOpeningDao.sctClosing(monthMst, 140),
                sctOpeningDao.sctCard(monthMst, 140), sctOpeningDao.sctSpare(monthMst, 140), sctOpeningDao.sctKey(monthMst, 140),
                sctOpeningDao.sctTotal(monthMst, 140), monthMst, yearMst, maxOpen140, maxClose140,
                maxTotalCard140, maxTotalSales140, balOpen140, balClose140, balCard140, TotalBalCard140);

        SctOpeningClosing SctOpeningClosing160 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 160),
                sctOpeningDao.sctOpening(monthMst, 160), sctOpeningDao.sctClosing(monthMst, 160),
                sctOpeningDao.sctCard(monthMst, 160), sctOpeningDao.sctSpare(monthMst, 160), sctOpeningDao.sctKey(monthMst, 160),
                sctOpeningDao.sctTotal(monthMst, 160), monthMst, yearMst, maxOpen160, maxClose160,
                maxTotalCard160, maxTotalSales160, balOpen160, balClose160, balCard160, TotalBalCard160);

        SctOpeningClosing SctOpeningClosing180 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 180),
                sctOpeningDao.sctOpening(monthMst, 180), sctOpeningDao.sctClosing(monthMst, 180),
                sctOpeningDao.sctCard(monthMst, 180), sctOpeningDao.sctSpare(monthMst, 180), sctOpeningDao.sctKey(monthMst, 180),
                sctOpeningDao.sctTotal(monthMst, 180), monthMst, yearMst, maxOpen180, maxClose180,
                maxTotalCard180, maxTotalSales180, balOpen180, balClose180, balCard180, TotalBalCard180);

        SctOpeningClosing SctOpeningClosing200 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 200),
                sctOpeningDao.sctOpening(monthMst, 200), sctOpeningDao.sctClosing(monthMst, 200),
                sctOpeningDao.sctCard(monthMst, 200), sctOpeningDao.sctSpare(monthMst, 200), sctOpeningDao.sctKey(monthMst, 200),
                sctOpeningDao.sctTotal(monthMst, 200), monthMst, yearMst, maxOpen200, maxClose200,
                maxTotalCard200, maxTotalSales200, balOpen200, balClose200, balCard200, TotalBalCard200);

        SctOpeningClosing SctOpeningClosing220 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 220),
                sctOpeningDao.sctOpening(monthMst, 220), sctOpeningDao.sctClosing(monthMst, 220),
                sctOpeningDao.sctCard(monthMst, 220), sctOpeningDao.sctSpare(monthMst, 220), sctOpeningDao.sctKey(monthMst, 220),
                sctOpeningDao.sctTotal(monthMst, 220), monthMst, yearMst, maxOpen220, maxClose220,
                maxTotalCard220, maxTotalSales220, balOpen220, balClose220, balCard220, TotalBalCard220);

        SctOpeningClosing SctOpeningClosing240 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 240),
                sctOpeningDao.sctOpening(monthMst, 240), sctOpeningDao.sctClosing(monthMst, 240),
                sctOpeningDao.sctCard(monthMst, 240), sctOpeningDao.sctSpare(monthMst, 240), sctOpeningDao.sctKey(monthMst, 240),
                sctOpeningDao.sctTotal(monthMst, 240), monthMst, yearMst, maxOpen240, maxClose240,
                maxTotalCard240, maxTotalSales240, balOpen240, balClose240, balCard240, TotalBalCard240);

        SctOpeningClosing SctOpeningClosing260 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 260),
                sctOpeningDao.sctOpening(monthMst, 260), sctOpeningDao.sctClosing(monthMst, 260),
                sctOpeningDao.sctCard(monthMst, 260), sctOpeningDao.sctSpare(monthMst, 260), sctOpeningDao.sctKey(monthMst, 260),
                sctOpeningDao.sctTotal(monthMst, 260), monthMst, yearMst, maxOpen260, maxClose260,
                maxTotalCard260, maxTotalSales260, balOpen260, balClose260, balCard260, TotalBalCard260);

        SctOpeningClosing SctOpeningClosing280 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 280),
                sctOpeningDao.sctOpening(monthMst, 280), sctOpeningDao.sctClosing(monthMst, 280),
                sctOpeningDao.sctCard(monthMst, 280), sctOpeningDao.sctSpare(monthMst, 280), sctOpeningDao.sctKey(monthMst, 280),
                sctOpeningDao.sctTotal(monthMst, 280), monthMst, yearMst, maxOpen280, maxClose280,
                maxTotalCard280, maxTotalSales280, balOpen280, balClose280, balCard280, TotalBalCard280);

        SctOpeningClosing SctOpeningClosing300 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 300),
                sctOpeningDao.sctOpening(monthMst, 300), sctOpeningDao.sctClosing(monthMst, 300),
                sctOpeningDao.sctCard(monthMst, 300), sctOpeningDao.sctSpare(monthMst, 300), sctOpeningDao.sctKey(monthMst, 300),
                sctOpeningDao.sctTotal(monthMst, 300), monthMst, yearMst, maxOpen300, maxClose300,
                maxTotalCard300, maxTotalSales300, balOpen300, balClose300, balCard300, TotalBalCard300);

        SctOpeningClosing SctOpeningClosing320 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 320),
                sctOpeningDao.sctOpening(monthMst, 320), sctOpeningDao.sctClosing(monthMst, 320),
                sctOpeningDao.sctCard(monthMst, 320), sctOpeningDao.sctSpare(monthMst, 320), sctOpeningDao.sctKey(monthMst, 320),
                sctOpeningDao.sctTotal(monthMst, 320), monthMst, yearMst, maxOpen320, maxClose320,
                maxTotalCard320, maxTotalSales320, balOpen320, balClose320, balCard320, TotalBalCard320);

        SctOpeningClosing SctOpeningClosing340 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 340),
                sctOpeningDao.sctOpening(monthMst, 340), sctOpeningDao.sctClosing(monthMst, 340),
                sctOpeningDao.sctCard(monthMst, 340), sctOpeningDao.sctSpare(monthMst, 340), sctOpeningDao.sctKey(monthMst, 340),
                sctOpeningDao.sctTotal(monthMst, 340), monthMst, yearMst, maxOpen340, maxClose340,
                maxTotalCard340, maxTotalSales340, balOpen340, balClose340, balCard340, TotalBalCard340);

        SctOpeningClosing SctOpeningClosing360 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 360),
                sctOpeningDao.sctOpening(monthMst, 360), sctOpeningDao.sctClosing(monthMst, 360),
                sctOpeningDao.sctCard(monthMst, 360), sctOpeningDao.sctSpare(monthMst, 360), sctOpeningDao.sctKey(monthMst, 360),
                sctOpeningDao.sctTotal(monthMst, 360), monthMst, yearMst, maxOpen360, maxClose360,
                maxTotalCard360, maxTotalSales360, balOpen360, balClose360, balCard360, TotalBalCard360);

        SctOpeningClosing SctOpeningClosing380 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 380),
                sctOpeningDao.sctOpening(monthMst, 380), sctOpeningDao.sctClosing(monthMst, 380),
                sctOpeningDao.sctCard(monthMst, 380), sctOpeningDao.sctSpare(monthMst, 380), sctOpeningDao.sctKey(monthMst, 380),
                sctOpeningDao.sctTotal(monthMst, 380), monthMst, yearMst, maxOpen380, maxClose380,
                maxTotalCard380, maxTotalSales380, balOpen380, balClose380, balCard380, TotalBalCard380);

        SctOpeningClosing SctOpeningClosing400 = new SctOpeningClosing(sctOpeningDao.sctMonthYear(monthMst, 400),
                sctOpeningDao.sctOpening(monthMst, 400), sctOpeningDao.sctClosing(monthMst, 400),
                sctOpeningDao.sctCard(monthMst, 400), sctOpeningDao.sctSpare(monthMst, 400), sctOpeningDao.sctKey(monthMst, 760),
                sctOpeningDao.sctTotal(monthMst, 400), monthMst, yearMst, maxOpen400, maxClose400,
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

    public void updateSctMonthEntryToDb(List<SctOpeningClosing> entryList) {
        sctOpeningDao.insertSctMonth(entryList.toArray(new SctOpeningClosing[0]));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View windowDecorView = requireActivity().getWindow().getDecorView();
        windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        year = mBinding.yearListMst.getText().toString();
        month = mBinding.monthListMst.getText().toString();
        sctOpeningClosing = new ArrayList<>();
        sctOpeningClosing = sctOpeningDao.getMonthWisesct(month, year);
        mAdapter = new SctMonthWiseAdapter(sctOpeningClosing, getContext());
        mBinding.recyclerMstMonth.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerMstMonth.setAdapter(mAdapter);

    }
}
