package com.tnstc.buspass.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.ActivityBaseBinding;

public class BaseActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    ActivityBaseBinding mBinding;
    NavController navController;
    AppBarConfiguration mAppBarConfiguration;
    public Context mContext;
    ApplicationClass appClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        mBinding = DataBindingUtil.setContentView(BaseActivity.this, R.layout.activity_base);
        mContext = this;
        appClass = (ApplicationClass) getApplication();
        setSupportActionBar(mBinding.toolbar);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.dashboardFragment)
                .build();
        setUpNavigationUI(mAppBarConfiguration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }

    }

    public void setUpNavigationUI(AppBarConfiguration appBarConfiguration) {
        navController.setGraph(R.navigation.navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
