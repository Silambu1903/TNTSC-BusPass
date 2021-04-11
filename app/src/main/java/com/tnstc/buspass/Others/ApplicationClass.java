package com.tnstc.buspass.Others;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.tnstc.buspass.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationClass extends Application {

    public static String[] FORM_BUS_LIST = {"AMBUR", "VELLORE", "MADHANUR", "VANIYAMBADI", "PALIKONDA", "KRISHNAGIRI", "THIRUPATHUR", "WALAJA"};
    public static String[] NEW_OLD_LIST = {"NEW", "OLD"};
    public static String[] EXP_DEL = {"EXPRESS", "DELUXE"};
    public static String[] MONTH = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

    public int spare200 = 5, spare240 = 6, spare280 = 7, spare320 = 8, spare360 = 9, spare400 = 10, spare440 = 11, spare480 = 12,
            spare520 = 13, spare560 = 14, spare600 = 15, spare640 = 16, spare680 = 17, spare720 = 18, spare760 = 19;

    public String key200 = "ST", key240 = "UV", key280 = "BA", key320 = "BB", key360 = "BC", key400 = "BD", key440 = "BE", key480 = "TSK",
            key520 = "BG", key560 = "BH", key600 = "BI", key640 = "AA", key680 = "BK", key720 = "BL", key760 = "BM";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SaveSpareKey();
    }

    private void SaveSpareKey() {
        editor = preferences.edit();
        editor.putInt("spare200", spare200).apply();
        editor.putInt("spare240", spare240).apply();
        editor.putInt("spare280", spare280).apply();
        editor.putInt("spare320", spare320).apply();
        editor.putInt("spare360", spare360).apply();
        editor.putInt("spare400", spare400).apply();
        editor.putInt("spare440", spare440).apply();
        editor.putInt("spare480", spare480).apply();
        editor.putInt("spare520", spare520).apply();
        editor.putInt("spare560", spare560).apply();
        editor.putInt("spare600", spare600).apply();
        editor.putInt("spare640", spare640).apply();
        editor.putInt("spare680", spare680).apply();
        editor.putInt("spare640", spare640).apply();
        editor.putInt("spare680", spare680).apply();
        editor.putInt("spare720", spare720).apply();
        editor.putInt("spare760", spare760).apply();
        editor.putString("key200", key200).apply();
        editor.putString("key240", key240).apply();
        editor.putString("key280", key280).apply();
        editor.putString("key320", key320).apply();
        editor.putString("key360", key360).apply();
        editor.putString("key400", key400).apply();
        editor.putString("key440", key440).apply();
        editor.putString("key480", key480).apply();
        editor.putString("key520", key520).apply();
        editor.putString("key560", key560).apply();
        editor.putString("key600", key600).apply();
        editor.putString("key640", key640).apply();
        editor.putString("key680", key680).apply();
        editor.putString("key720", key720).apply();
        editor.putString("key760", key760).apply();


    }

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

    public Integer getCurrentDateTimeSec() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yyyyHH:mm:ss");
        Date date = new Date();
        return Integer.valueOf(simpleDateFormat.format(date));
    }

    public void showSnackBar(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.root), message, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
