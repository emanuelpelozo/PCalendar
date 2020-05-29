package com.android.pcalendar.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.android.pcalendar.R;

public class InfoDialog extends Dialog {

    public InfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_dialog);

        Button buttonOk = findViewById(R.id.dialogInfoOK);

        buttonOk.setOnClickListener(v -> {
            InfoDialog.this.dismiss();
        });

    }

}
