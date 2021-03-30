package com.tnstc.buspass.callback;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Adapter.PassEntryAdapter;

public interface TextWatcherWithInstance {
    void onTextChanged(EditText editText, PassEntryAdapter.PassEntryViewHolder holder, CharSequence s, int start, int before, int count);

}