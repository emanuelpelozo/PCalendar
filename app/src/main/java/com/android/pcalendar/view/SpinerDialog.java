package com.android.pcalendar.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.android.pcalendar.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

public class SpinerDialog extends Dialog {

    private List<Integer> cycleOptions;
    private List<Integer> periodOptions;

    private Context context;
    private MaterialSpinner cycleSpinner;
    private MaterialSpinner periodSpinner;

    public interface DialogListener {
        public void ready(int cycleElection, int periodElection);
        public void cancelled();
    }

    private DialogListener readyListener;

    public SpinerDialog(@NonNull Context context, List<Integer> cycleOptions, List<Integer> periodOptions, DialogListener listener ) {
        super(context);
        this.readyListener = listener;
        this.context = context;
        this.cycleOptions = cycleOptions;
        this.periodOptions = periodOptions;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spiner_dialog);

        cycleSpinner = findViewById(R.id.cycle_spinner);
        periodSpinner = findViewById(R.id.period_spinner);
        Button buttonOk = findViewById(R.id.dialogOK);
        Button buttonCancel = findViewById(R.id.dialogCancel);

        cycleSpinner.setItems(this.cycleOptions);
        periodSpinner.setItems(this.periodOptions);


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n1 = cycleSpinner.getSelectedIndex();
                int n2 = periodSpinner.getSelectedIndex();

                int cycleElection = cycleOptions.get(n1);
                int periodElection = periodOptions.get(n2);

                readyListener.ready(cycleElection, periodElection);
                SpinerDialog.this.dismiss();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyListener.cancelled();
                SpinerDialog.this.dismiss();
            }
        });
    }
}
