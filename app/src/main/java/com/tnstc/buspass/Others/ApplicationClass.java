package com.tnstc.buspass.Others;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.tnstc.buspass.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationClass extends Application {

    public static String[] FORM_BUS_LIST = {"Ambur", "Vellore", "Madhanur","Vaniyambadi", "Palikonda", "Krishnagiri","Thirupathur","Walaja"};
    public static String[] NEW_OLD_LIST = {"NEW","OLD"};
    public static String[] EXP_DEL = {"EXPRESS","DELUXE"};


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
}
