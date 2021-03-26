package com.tnstc.buspass.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tnstc.buspass.Activity.BaseActivity;
import com.tnstc.buspass.Adapter.PassEntryAdapter;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.databinding.PassEntryListBinding;

import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PassEntryListFragment extends Fragment implements ItemClickListener {
    PassEntryListBinding mBinding;
    ApplicationClass mAppClass;
    Context mContext;
    PassEntryAdapter passEntryAdapter;
    public List<PassEntity> passEntityList;
    BaseActivity mActivity;
    private ScaleGestureDetector mScaleDetector;
    GestureDetector detector;
    private float mScaleFactor = 1.f;
    private float mScale = 1f;
    int txt;
    private ActionMode actionMode;
    boolean mMultiSelect = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.pass_entry_list, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mAppClass = (ApplicationClass) getActivity().getApplicationContext();
        mContext = getContext();
        mActivity = (BaseActivity) getActivity();
        mActivity.getSupportActionBar().hide();
        passEntityList = new ArrayList<>();
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(mContext);
        PassDao dao = db.passDao();
        passEntityList = dao.getAllList();
        passEntryAdapter = new PassEntryAdapter(getContext(), passEntityList, this);
        mBinding.entryList.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.entryList.setAdapter(passEntryAdapter);
        txt = dao.getTotalAmount();
        mBinding.txtTotAmount.setText(txt + "");

        pitchZoom();
        mBinding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleDetector.onTouchEvent(event);
                detector.onTouchEvent(event);
                return detector.onTouchEvent(event);
            }
        });


    }

    private void deleteUsers(String... UIDs) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(mContext);
        PassDao dao = db.passDao();
        dao.deleteEmployees(UIDs);
    }

    private void pitchZoom() {
        detector = new GestureDetector(getContext(), new GestureListener());

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                if (mScale > 10f)
                    mScale = 10f;

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f / prevScale, 0.5f / mScale, 0.5f / prevScale, 0.5f / mScale, detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                mBinding.horiznotalscroll.startAnimation(scaleAnimation);
                return true;
            }
        });
    }

    @Override
    public void OnItemClick(View v, int pos) {

    }

    @Override
    public void OnItemLongClick(View v, int pos) {

        passEntityList.remove(pos);
        passEntryAdapter.notifyDataSetChanged();
    }

    private void startActionMode(int pos) {
        ActionMode.Callback passEntryDelete = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                actionMode = actionMode;
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Toast.makeText(mContext, "sdsda", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        };
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity.getSupportActionBar().show();
    }


}
