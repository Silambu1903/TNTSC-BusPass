package com.tnstc.buspass.Fragment;

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
import com.tnstc.buspass.databinding.SctDesignFragmentBinding;

public class SctDesignFragment extends Fragment {
    SctDesignFragmentBinding mBinding;
    ApplicationClass mAppClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.sct_design_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppClass = (ApplicationClass) getActivity().getApplication();
        mBinding.sctPassEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_sctentry);
            }
        });
        mBinding.sctPassListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_sct_list);
            }
        });
        mBinding.sctPassMonthlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppClass.navigate(getActivity(), R.id.action_dashboardFragment_to_sct_month);
            }
        });
    }
}
