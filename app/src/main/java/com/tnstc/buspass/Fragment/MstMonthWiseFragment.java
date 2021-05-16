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
import android.view.Window;
import android.view.WindowManager;
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
import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.DAOs.MstOpeningDao;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.MstMonthwiseBinding;

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

public class MstMonthWiseFragment extends Fragment implements AdapterView.OnItemClickListener {
    MstMonthwiseBinding mBinding;
    TnstcBusPassDB db;
    MstDao dao;
    MstOpeningDao daoOpenClose;
    Context mContext;
    ApplicationClass mAppClass;
    private SharedPreferences preferences;
    List<String> getYear;
    List<String> getMonth;
    MstMonthWiseAdapter mAdapter;
    List<MstOpeningClosing> mstOpeningClosings;
    String month;
    String year;
    String currentMonth;
    String currentYear;


    int balOpen200, balClose200, TotalBalCard200, balCard200, maxMonthOpenCard200,
            balOpen240, balClose240, TotalBalCard240, balCard240, maxMonthOpenCard240,
            balOpen280, balClose280, TotalBalCard280, balCard280, maxMonthOpenCard280,
            balOpen320, balClose320, TotalBalCard320, balCard320, maxMonthOpenCard320,
            balOpen360, balClose360, TotalBalCard360, balCard360, maxMonthOpenCard360,
            balOpen400, balClose400, TotalBalCard400, balCard400, maxMonthOpenCard400,
            balOpen440, balClose440, TotalBalCard440, balCard440, maxMonthOpenCard440,
            balOpen480, balClose480, TotalBalCard480, balCard480, maxMonthOpenCard480,
            balOpen520, balClose520, TotalBalCard520, balCard520, maxMonthOpenCard520,
            balOpen560, balClose560, TotalBalCard560, balCard560, maxMonthOpenCard560,
            balOpen600, balClose600, TotalBalCard600, balCard600, maxMonthOpenCard600,
            balOpen640, balClose640, TotalBalCard640, balCard640, maxMonthOpenCard640,
            balOpen680, balClose680, TotalBalCard680, balCard680, maxMonthOpenCard680,
            balOpen720, balClose720, TotalBalCard720, balCard720, maxMonthOpenCard720,
            balOpen760, balClose760, TotalBalCard760, balCard760, maxMonthOpenCard760;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.mst_monthwise, container, false);
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
            if (!(mstOpeningClosings ==null)) {
                excelWorkBookWrite();
            }else {
                mAppClass.showSnackBar(getContext(),"Please Select the Month and Year");
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
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.mstDao();
        daoOpenClose = db.OpeningDao();

        if (!daoOpenClose.getMonthWiseMst("April", "2021").isEmpty()) {
            getMstEntry();
            mstOpeningClosings = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            mBinding.monthListMst.setText(new SimpleDateFormat("MMMM").format(cal.getTime()) + "");
            mBinding.yearListMst.setText((Calendar.getInstance().get(Calendar.YEAR)) + "");
            year = mBinding.yearListMst.getText().toString();
            month = mBinding.monthListMst.getText().toString();
            mstOpeningClosings = daoOpenClose.getMonthWiseMst(month, year);
            mAdapter = new MstMonthWiseAdapter(mstOpeningClosings, getContext());
            mBinding.recyclerMstMonth.setLayoutManager(new LinearLayoutManager(getContext()));
            mBinding.recyclerMstMonth.setAdapter(mAdapter);
        } else {
            getMstEntry();
        }
        AdapterForMonthYear();
        mBinding.monthListMst.setOnItemClickListener(this);
        mBinding.yearListMst.setOnItemClickListener(this);

    }


    private void excelWorkBookWrite() {
        currentMonth = String.valueOf(DateFormat.format("MMMM", new Date()));
        currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet SecondSheet = workbook.createSheet("Mst" + month);
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
        for (int i = 0; i < mstOpeningClosings.size(); i++) {
            HSSFRow rowA = SecondSheet.createRow(i + 1);
            rowA.createCell(0).setCellValue(mstOpeningClosings.get(i).getMstCard());
            rowA.createCell(1).setCellValue(mstOpeningClosings.get(i).getMstKey());
            rowA.createCell(2).setCellValue(mstOpeningClosings.get(i).getMstOpening());
            rowA.createCell(3).setCellValue(mstOpeningClosings.get(i).getMstClosing());
            rowA.createCell(4).setCellValue(mstOpeningClosings.get(i).getMstTotal());
            rowA.createCell(5).setCellValue(mstOpeningClosings.get(i).getMstOpenMax());
            rowA.createCell(6).setCellValue(mstOpeningClosings.get(i).getMstCloseMax());
            rowA.createCell(7).setCellValue(mstOpeningClosings.get(i).getMstTotalCard());
            rowA.createCell(8).setCellValue(mstOpeningClosings.get(i).getMstTotalAmount());
            rowA.createCell(9).setCellValue(mstOpeningClosings.get(i).getMstBalOpen());
            rowA.createCell(10).setCellValue(mstOpeningClosings.get(i).getMstBalClose());
            rowA.createCell(11).setCellValue(mstOpeningClosings.get(i).getMstBalCard());
            rowA.createCell(12).setCellValue(mstOpeningClosings.get(i).getMstBalTotalCard());

        }
        FileOutputStream fos = null;
        try {
            File file;
            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TNSTC BUS PASS DETAILS");
            if (!folder.mkdirs()) {
                folder.mkdirs();
            }
            file = new File(folder + File.separator, "TNSTCMst" + month + year + ".xls");
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
        getYear = daoOpenClose.mstGetYear();
        getMonth = daoOpenClose.mstGetMonth();
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

    private void getMstEntry() {

        //card200
        String monthMst = String.valueOf(DateFormat.format("MMMM", new Date()));
        String yearMst = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
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

        //Card680
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

        //Card720
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

        //Caard760
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


        MstOpeningClosing MstOpeningClosing200 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 200),
                daoOpenClose.mstOpening(monthMst, 200), daoOpenClose.mstClosing(monthMst, 200),
                daoOpenClose.mstCard(monthMst, 200), daoOpenClose.mstSpare(monthMst, 200), daoOpenClose.mstKey(monthMst, 200),
                daoOpenClose.mstTotal(monthMst, 200), monthMst, yearMst, maxOpen200, maxClose200,
                maxTotalCard, maxTotalSales, balOpen200, balClose200, balCard200, TotalBalCard200);

        MstOpeningClosing MstOpeningClosing240 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 240),
                daoOpenClose.mstOpening(monthMst, 240), daoOpenClose.mstClosing(monthMst, 240),
                daoOpenClose.mstCard(monthMst, 240), daoOpenClose.mstSpare(monthMst, 240), daoOpenClose.mstKey(monthMst, 240),
                daoOpenClose.mstTotal(monthMst, 240), monthMst, yearMst, maxOpen240, maxClose240,
                maxTotalCard240, maxTotalSales240, balOpen240, balClose240, balCard240, TotalBalCard240);

        MstOpeningClosing MstOpeningClosing280 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 280),
                daoOpenClose.mstOpening(monthMst, 280), daoOpenClose.mstClosing(monthMst, 280),
                daoOpenClose.mstCard(monthMst, 280), daoOpenClose.mstSpare(monthMst, 280), daoOpenClose.mstKey(monthMst, 280),
                daoOpenClose.mstTotal(monthMst, 280), monthMst, yearMst, maxOpen280, maxClose280,
                maxTotalCard280, maxTotalSales280, balOpen280, balClose280, balCard280, TotalBalCard280);

        MstOpeningClosing MstOpeningClosing320 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 320),
                daoOpenClose.mstOpening(monthMst, 320), daoOpenClose.mstClosing(monthMst, 320),
                daoOpenClose.mstCard(monthMst, 320), daoOpenClose.mstSpare(monthMst, 320), daoOpenClose.mstKey(monthMst, 320),
                daoOpenClose.mstTotal(monthMst, 320), monthMst, yearMst, maxOpen320, maxClose320,
                maxTotalCard320, maxTotalSales320, balOpen320, balClose320, balCard320, TotalBalCard320);

        MstOpeningClosing MstOpeningClosing360 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 360),
                daoOpenClose.mstOpening(monthMst, 360), daoOpenClose.mstClosing(monthMst, 360),
                daoOpenClose.mstCard(monthMst, 360), daoOpenClose.mstSpare(monthMst, 360), daoOpenClose.mstKey(monthMst, 360),
                daoOpenClose.mstTotal(monthMst, 360), monthMst, yearMst, maxOpen360, maxClose360,
                maxTotalCard360, maxTotalSales360, balOpen360, balClose360, balCard360, TotalBalCard360);

        MstOpeningClosing MstOpeningClosing400 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 400),
                daoOpenClose.mstOpening(monthMst, 400), daoOpenClose.mstClosing(monthMst, 400),
                daoOpenClose.mstCard(monthMst, 400), daoOpenClose.mstSpare(monthMst, 400), daoOpenClose.mstKey(monthMst, 400),
                daoOpenClose.mstTotal(monthMst, 400), monthMst, yearMst, maxOpen400, maxClose400,
                maxTotalCard400, maxTotalSales400, balOpen400, balClose400, balCard400, TotalBalCard400);

        MstOpeningClosing MstOpeningClosing440 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 440),
                daoOpenClose.mstOpening(monthMst, 440), daoOpenClose.mstClosing(monthMst, 440),
                daoOpenClose.mstCard(monthMst, 440), daoOpenClose.mstSpare(monthMst, 440), daoOpenClose.mstKey(monthMst, 440),
                daoOpenClose.mstTotal(monthMst, 440), monthMst, yearMst, maxOpen440, maxClose440,
                maxTotalCard440, maxTotalSales440, balOpen440, balClose440, balCard440, TotalBalCard440);

        MstOpeningClosing MstOpeningClosing480 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 480),
                daoOpenClose.mstOpening(monthMst, 480), daoOpenClose.mstClosing(monthMst, 480),
                daoOpenClose.mstCard(monthMst, 480), daoOpenClose.mstSpare(monthMst, 480), daoOpenClose.mstKey(monthMst, 480),
                daoOpenClose.mstTotal(monthMst, 480), monthMst, yearMst, maxOpen480, maxClose480,
                maxTotalCard480, maxTotalSales480, balOpen480, balClose480, balCard480, TotalBalCard480);

        MstOpeningClosing MstOpeningClosing520 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 480),
                daoOpenClose.mstOpening(monthMst, 520), daoOpenClose.mstClosing(monthMst, 520),
                daoOpenClose.mstCard(monthMst, 520), daoOpenClose.mstSpare(monthMst, 520), daoOpenClose.mstKey(monthMst, 520),
                daoOpenClose.mstTotal(monthMst, 520), monthMst, yearMst, maxOpen520, maxClose520,
                maxTotalCard520, maxTotalSales520, balOpen520, balClose520, balCard520, TotalBalCard520);

        MstOpeningClosing MstOpeningClosing560 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 560),
                daoOpenClose.mstOpening(monthMst, 560), daoOpenClose.mstClosing(monthMst, 560),
                daoOpenClose.mstCard(monthMst, 560), daoOpenClose.mstSpare(monthMst, 560), daoOpenClose.mstKey(monthMst, 560),
                daoOpenClose.mstTotal(monthMst, 560), monthMst, yearMst, maxOpen560, maxClose560,
                maxTotalCard560, maxTotalSales560, balOpen560, balClose560, balCard560, TotalBalCard560);

        MstOpeningClosing MstOpeningClosing600 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 600),
                daoOpenClose.mstOpening(monthMst, 600), daoOpenClose.mstClosing(monthMst, 600),
                daoOpenClose.mstCard(monthMst, 600), daoOpenClose.mstSpare(monthMst, 600), daoOpenClose.mstKey(monthMst, 600),
                daoOpenClose.mstTotal(monthMst, 600), monthMst, yearMst, maxOpen600, maxClose600,
                maxTotalCard600, maxTotalSales600, balOpen600, balClose600, balCard600, TotalBalCard600);

        MstOpeningClosing MstOpeningClosing640 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 640),
                daoOpenClose.mstOpening(monthMst, 640), daoOpenClose.mstClosing(monthMst, 640),
                daoOpenClose.mstCard(monthMst, 640), daoOpenClose.mstSpare(monthMst, 640), daoOpenClose.mstKey(monthMst, 640),
                daoOpenClose.mstTotal(monthMst, 640), monthMst, yearMst, maxOpen640, maxClose640,
                maxTotalCard640, maxTotalSales640, balOpen640, balClose640, balCard640, TotalBalCard640);

        MstOpeningClosing MstOpeningClosing680 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 680),
                daoOpenClose.mstOpening(monthMst, 680), daoOpenClose.mstClosing(monthMst, 680),
                daoOpenClose.mstCard(monthMst, 680), daoOpenClose.mstSpare(monthMst, 680), daoOpenClose.mstKey(monthMst, 680),
                daoOpenClose.mstTotal(monthMst, 680), monthMst, yearMst, maxOpen680, maxClose680,
                maxTotalCard680, maxTotalSales680, balOpen680, balClose680, balCard680, TotalBalCard680);

        MstOpeningClosing MstOpeningClosing720 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 720),
                daoOpenClose.mstOpening(monthMst, 720), daoOpenClose.mstClosing(monthMst, 720),
                daoOpenClose.mstCard(monthMst, 720), daoOpenClose.mstSpare(monthMst, 720), daoOpenClose.mstKey(monthMst, 720),
                daoOpenClose.mstTotal(monthMst, 720), monthMst, yearMst, maxOpen720, maxClose720,
                maxTotalCard720, maxTotalSales720, balOpen720, balClose720, balCard720, TotalBalCard720);

        MstOpeningClosing MstOpeningClosing760 = new MstOpeningClosing(daoOpenClose.mstMonthYear(monthMst, 760),
                daoOpenClose.mstOpening(monthMst, 760), daoOpenClose.mstClosing(monthMst, 760),
                daoOpenClose.mstCard(monthMst, 760), daoOpenClose.mstSpare(monthMst, 760), daoOpenClose.mstKey(monthMst, 760),
                daoOpenClose.mstTotal(monthMst, 760), monthMst, yearMst, maxOpen760, maxClose760,
                maxTotalCard760, maxTotalSales760, balOpen760, balClose760, balCard760, TotalBalCard760);

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

    public void updateMstMonthEntryToDb(List<MstOpeningClosing> entryList) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(getContext());
        MstOpeningDao dao = db.OpeningDao();
        dao.insertMstMonth(entryList.toArray(new MstOpeningClosing[0]));

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
        mstOpeningClosings = new ArrayList<>();
        mstOpeningClosings = daoOpenClose.getMonthWiseMst(month, year);
        mAdapter = new MstMonthWiseAdapter(mstOpeningClosings, getContext());
        mBinding.recyclerMstMonth.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerMstMonth.setAdapter(mAdapter);
    }
}
