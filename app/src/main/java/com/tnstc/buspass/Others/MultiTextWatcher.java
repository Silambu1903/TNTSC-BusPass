package com.tnstc.buspass.Others;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Adapter.PassEntryAdapter;
import com.tnstc.buspass.callback.TextWatcherWithInstance;

public class MultiTextWatcher {
    private TextWatcherWithInstance callback;

    public MultiTextWatcher setCallback(TextWatcherWithInstance callback) {
        this.callback = callback;
        return this;
    }

    public MultiTextWatcher registerEditText( EditText editText ,PassEntryAdapter.PassEntryViewHolder holder ) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callback.onTextChanged(editText,holder, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        return this;
    }


}
