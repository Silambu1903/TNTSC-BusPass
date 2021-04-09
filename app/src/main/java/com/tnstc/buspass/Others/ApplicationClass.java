package com.tnstc.buspass.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.tnstc.buspass.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationClass extends Application {

    public static String[] FORM_BUS_LIST = {"Ambur", "Vellore", "Madhanur", "Vaniyambadi", "Palikonda", "Krishnagiri", "Thirupathur", "Walaja"};
    public static String[] NEW_OLD_LIST = {"NEW", "OLD"};
    public static String[] EXP_DEL = {"EXPRESS", "DELUXE"};
    public static String[] MONTH = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};


    public void navigate(FragmentActivity fragAct, int destinationID) {
        Navigation.findNavController((Activity) fragAct, R.id.nav_host_fragment).navigate(destinationID);
    }

    public void goBack(FragmentActivity activity) {
        Navigation.findNavController((Activity) activity, R.id.nav_host_fragment).popBackStack();
    }

    public String getCurrentDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public void showSnackBar(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.root), message, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
