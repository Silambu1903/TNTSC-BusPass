package com.tnstc.buspass.Fragment;

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

import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.databinding.DashboardFragmentBinding;

public class DashboardFragment extends Fragment {

    DashboardFragmentBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dashboard_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_passentry);
            }
        });
        mBinding.submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_passentrylist);
            }
        });
        mBinding.submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_passmonthwiselist);
            }
        });

        mBinding.submit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_mstsctentry);
            }
        });

        mBinding.submit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(),R.id.action_dashboardFragment_to_mst_list);
            }
        });
    }


}
