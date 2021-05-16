package com.tnstc.buspass.callback;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public interface ItemClickListener {
    public void OnItemClick(View v, int pos);

    public void OnItemLongClick(View v, int pos);

    void OnItemClickDate(View v, int adapterPosition, List<String> currentDateAndDay, ConstraintLayout constraintLayout);
}
