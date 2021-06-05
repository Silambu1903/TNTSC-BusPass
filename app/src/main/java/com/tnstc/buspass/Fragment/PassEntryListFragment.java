package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.ActionMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

import static com.tnstc.buspass.Others.ApplicationClass.PINTER_FILE_NAME;

public class PassEntryListFragment extends Fragment implements ItemClickListener {
    PassEntryListBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    PassEntryAdapter passEntryAdapter;
    public List<PassEntity> passEntityList;
    BaseActivity mActivity;
    int txt;

    TnstcBusPassDB db;
    PassDao dao;
    String currentMonth;
    String currentYear;
    int startYPosition;
    boolean mMultiSelect = false;
    private ActionMode mActionMode;
    PdfDocument mPDFDocument;
    PdfDocument.Page mPDFPage;
    Paint mPaint;
    int mStartXPosition = 10;
    int mEndXPosition;


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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.users_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_download) {
            if (!(passEntityList == null)) {
                excelWorkBookWrite();
            } else {
                mAppClass.showSnackBar(getContext(), "PassDetail is Empty");
            }

        } else if (item.getItemId() == R.id.action_open) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "TNSTC BUS PASS DETAILS" + File.separator);
            intent.setDataAndType(uri, "*/*");
            startActivity(Intent.createChooser(intent, "TNSTC BUS PASS DETAILS"));
        } else if (item.getItemId() == R.id.action_print) {
            createPDF();
        } else {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.onSupportNavigateUp();
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPDF() {
        if (passEntityList.size() <= 0) {
            return;
        }
        int itemPerPage = 10;
        List<List<PassEntity>> printablePages = splitIntoParts(passEntityList, itemPerPage);
        mPDFDocument = new PdfDocument();
        mPaint = new Paint();
        Canvas pdfPage;
        for (int i = 0; i < printablePages.size(); i++) {
            pdfPage = startPage(i + 1);
            List<PassEntity> entities = printablePages.get(i);
            for (int j = 0; j < entities.size(); j++) {
                pdfPage.drawText(String.valueOf(entities.get(j).getSno()), 18, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getiNo()), 32, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getRepNo()), 50, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getNewOld()), 66, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getDate()), 90, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getName()), 135, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getFromArea()), 180, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(i).getToArea()), 208, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getBusFare()), 230, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getAmount()), 250, startYPosition, mPaint);
                pdfPage.drawText(String.valueOf(entities.get(j).getExpDel()), 275, startYPosition, mPaint);
                pdfPage.drawLine(mStartXPosition, startYPosition + 3, mEndXPosition + 3, startYPosition + 3, mPaint);
                startYPosition += 15;
            }
            endPage(pdfPage);
        }
        File mypath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), PINTER_FILE_NAME);
        try {
            mPDFDocument.writeTo(new FileOutputStream(mypath));
            mActivity.printDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPDFDocument.close();
    }

    public List<List<PassEntity>> splitIntoParts(List<PassEntity> passEntities, int itemsPerList) {
        List<List<PassEntity>> splittedList = new ArrayList<>();
        for (int i = 0; i < passEntities.size(); i += itemsPerList) {
            splittedList.add(passEntities.subList(i, Math.min(passEntities.size(), i + itemsPerList)));
        }
        return splittedList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Canvas startPage(int pageNo) {
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(297, 210, pageNo).create();
        mPDFPage = mPDFDocument.startPage(pageInfo);
        Canvas canvas = mPDFPage.getCanvas();
        Typeface currentTypeFace = mPaint.getTypeface();
        Typeface bold = Typeface.create(currentTypeFace, Typeface.BOLD);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.google_font);
        mPaint.setTypeface(typeface);
        mPaint.setTypeface(bold);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(12.f);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("TNSTC AMBUR DEPOT", pageInfo.getPageWidth() / 2, 15, mPaint);
        mPaint.setTextSize(8.f);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.BLACK);
        canvas.drawText("STUDENT BUS PASS DETAILS -- " + currentMonth + " " + currentYear, pageInfo.getPageWidth() / 2, 28, mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(3.0f);
        mPaint.setColor(Color.BLACK);
        mEndXPosition = pageInfo.getPageWidth() - 10;
        startYPosition = 60;
        canvas.drawText("S.NO", 18, 45, mPaint);
        canvas.drawText("ID.NO", 33, 45, mPaint);
        canvas.drawText("REP.NO", 48, 45, mPaint);
        canvas.drawText("NEW/OLD", 67, 45, mPaint);
        canvas.drawText("DATE", 90, 45, mPaint);
        canvas.drawText("NAME", 133, 45, mPaint);
        canvas.drawText("FROM", 180, 45, mPaint);
        canvas.drawText("TO", 210, 45, mPaint);
        canvas.drawText("FARE", 232, 45, mPaint);
        canvas.drawText("AMOUNT", 251, 45, mPaint);
        canvas.drawText("EXP/DEL", 276, 45, mPaint);
        return canvas;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void endPage(Canvas page) {
        int endPos = startYPosition - 12;
        page.drawLine(mStartXPosition, 40, mEndXPosition + 3, 40, mPaint);
        page.drawLine(mStartXPosition, 50, mEndXPosition + 3, 50, mPaint);
        page.drawLine(10, 40, 10, endPos, mPaint);
        page.drawLine(25, 40, 25, endPos, mPaint);
        page.drawLine(40, 40, 40, endPos, mPaint);
        page.drawLine(58, 40, 58, endPos, mPaint);
        page.drawLine(75, 40, 75, endPos, mPaint);
        page.drawLine(105, 40, 105, endPos, mPaint);
        page.drawLine(165, 40, 165, endPos, mPaint);
        page.drawLine(195, 40, 195, endPos, mPaint);
        page.drawLine(223, 40, 223, endPos, mPaint);
        page.drawLine(240, 40, 240, endPos, mPaint);
        page.drawLine(260, 40, 260, endPos, mPaint);
        page.drawLine(290, 40, 290, endPos, mPaint);
        mPDFDocument.finishPage(mPDFPage);
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
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TNSTC BUS PASS DETAILS");
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


    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos, ConstraintLayout constraintLayout) {
        startActionMode(pos,constraintLayout);
        constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorItemSelected));
    }

    @Override
    public void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout) {

    }

    @Override
    public void OnItemDate(int adapterPosition, List<DutyEntity> dutyEntities) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View windowDecorView = requireActivity().getWindow().getDecorView();
        windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    public void startActionMode(int pos,ConstraintLayout constraintLayout) {
        ActionMode.Callback userDelete = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mActionMode = mode;
                mMultiSelect = true;
                menu.add(R.string.delete).setIcon(R.drawable.ic_baseline_delete_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                mActionMode = mode;
                return false;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Delete":
                        new MaterialAlertDialogBuilder(getContext()).setTitle(R.string.delete).setMessage(R.string.areYouSureWantToDelete).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dao.delete(passEntityList.get(pos));
                                passEntityList.remove(passEntityList.get(pos));
                                passEntryAdapter.notifyDataSetChanged();
                                totalAndDailyEntry();
                            }
                        }).setNegativeButton(R.string.no, null).show();

                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = mode;
                mMultiSelect = false;
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.colorTransparentWhite));
                mode.finish();

            }
        };
        ((AppCompatActivity) mContext).startSupportActionMode(userDelete);
    }
}
