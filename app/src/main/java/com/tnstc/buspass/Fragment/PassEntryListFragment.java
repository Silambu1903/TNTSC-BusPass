package com.tnstc.buspass.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.PassEntryAdapter;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.PassEntryListBinding;

import java.util.ArrayList;
import java.util.List;

public class PassEntryListFragment extends Fragment {
    PassEntryListBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    PassEntryAdapter passEntryAdapter;
    List<PassEntity> passEntityList;
    BaseActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.pass_entry_list, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        mActivity.getSupportActionBar().hide();
        passEntityList = new ArrayList<>();
        mBinding.entryList.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.entryList.setAdapter(passEntryAdapter);

    }



}
