package com.tnstc.buspass.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.ActivityMainBinding;

public class BaseActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    Context mContext;
    ApplicationClass mAppClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        mAppClass = (ApplicationClass) getApplication();
    }
}
