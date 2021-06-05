package com.tnstc.buspass.callback;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tnstc.buspass.Database.Entity.DutyEntity;

import java.util.List;

public interface ItemClickListener {
    public void OnItemClick(View v, int pos);

    public void OnItemLongClick(View v, int pos, ConstraintLayout constraintLayout);

    void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout);

    void OnItemDate(int adapterPosition, List<DutyEntity> dutyEntities);
}
