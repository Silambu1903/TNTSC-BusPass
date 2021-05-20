package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.view.ActionMode;

import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.PassEntryAdapter;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.PassEntryListBinding;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassEntryListFragment extends Fragment implements ItemClickListener {
    PassEntryListBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    PassEntryAdapter passEntryAdapter;
    public List<PassEntity> passEntityList;
    BaseActivity mActivity;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector detector;
    private float mScaleFactor = 1.f;
    private float mScale = 1f;
    int txt;
    private ActionMode actionMode;
    boolean mMultiSelect = false;
    TnstcBusPassDB db;
    PassDao dao;
    String currentMonth;
    String currentYear;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.pass_entry_list, container, false);
        return mBinding.getRoot();
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
        passEntityList = new ArrayList<>();
        db = TnstcBusPassDB.getDatabase(mContext);
        dao = db.passDao();
        currentMonth = String.valueOf(DateFormat.format("MMMM", new Date()));
        currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        passEntityList = dao.currentMonth(currentMonth, currentYear);
        passEntryAdapter = new PassEntryAdapter(getContext(), passEntityList, this);
        mBinding.entryList.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.entryList.setAdapter(passEntryAdapter);
        totalAndDailyEntry();

        pitchZoom();
        mBinding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleDetector.onTouchEvent(event);
                detector.onTouchEvent(event);
                return detector.onTouchEvent(event);
            }
        });


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

    private void excelWorkBookWrite() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet(currentMonth);
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
            file = new File(mediaStorageDir + File.separator, "TNSTCBusPass" + currentMonth + currentYear + ".xls");
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

    private void totalAndDailyEntry() {
        txt = dao.monthWiseTotal(currentMonth, currentYear);
        mBinding.txtTotAmount.setText(txt + "");
        int dailytxt = dao.getDailyTotalAmount(mAppClass.getCurrentDateTime(), mAppClass.getCurrentDateTime());
        mBinding.dailtAmt.setText(dailytxt + "");
        int TotalSales = dao.getMonthWiseTotalSales(currentMonth, currentYear);
        mBinding.txtToalScalesAm.setText(TotalSales + "");
        int DailySales = dao.getDailySales(mAppClass.getCurrentDateTime(), mAppClass.getCurrentDateTime());
        mBinding.txtDailySalesAmount.setText(DailySales + "");
    }


    private void pitchZoom() {
        detector = new GestureDetector(getContext(), new GestureListener());

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                if (mScale > 10f)
                    mScale = 10f;

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f / prevScale, 0.5f / mScale, 0.5f / prevScale, 0.5f / mScale, detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                mBinding.horiznotalscroll.startAnimation(scaleAnimation);
                return true;
            }
        });
    }

    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos) {
        //startActionMode(pos);
        dao.delete(passEntityList.get(pos));
        passEntityList.remove(passEntityList.get(pos));
        passEntryAdapter.notifyDataSetChanged();
        totalAndDailyEntry();


    }

    @Override
    public void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout) {

    }

    @Override
    public void OnItemDate(int adapterPosition, List<DutyEntity> dutyEntities) {

    }


    private void startActionMode(int pos) {
        ActionMode.Callback passEntryDelete = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                actionMode = actionMode;
                mMultiSelect = true;
                menu.add(R.string.selectAll);
                menu.add(R.string.delete).setIcon(R.drawable.ic_baseline_delete_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                actionMode = actionMode;
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = actionMode;
                mMultiSelect = false;

                actionMode.finish();
            }
        };
        ((AppCompatActivity) mContext).startSupportActionMode(passEntryDelete);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View windowDecorView = requireActivity().getWindow().getDecorView();
        windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


}
