package com.tnstc.buspass.Activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

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
