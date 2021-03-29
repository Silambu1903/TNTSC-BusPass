package com.tnstc.buspass.Database.DAOs;

import android.widget.EditText;

public interface TextWatcherWithInstance {
    void onTextChanged(EditText editText, CharSequence s, int start, int before, int count);
}