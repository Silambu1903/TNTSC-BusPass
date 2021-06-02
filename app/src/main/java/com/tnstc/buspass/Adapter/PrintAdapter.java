package com.tnstc.buspass.Adapter;

import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.tnstc.buspass.Activity.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.tnstc.buspass.Others.ApplicationClass.PINTER_FILE_NAME;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)

public class PrintAdapter extends PrintDocumentAdapter {


    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            deleteFile();
            return;
        }
        PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(PINTER_FILE_NAME).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
        callback.onLayoutFinished(pdi, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),PINTER_FILE_NAME));
            output = new FileOutputStream(destination.getFileDescriptor());
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {

        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
    }


    public void deleteFile() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),PINTER_FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
        }

    }

    @Override
    public void onFinish() {
        super.onFinish();
        deleteFile();
    }
}
