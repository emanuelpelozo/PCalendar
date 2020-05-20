package com.android.pcalendar.Controller;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class ButtonDateInteractionClickListener implements View.OnClickListener {

    private MaterialCalendarView calendarView;
    private Context context;

    public ButtonDateInteractionClickListener(MaterialCalendarView calendarView, @NonNull Context context) {
        this.calendarView = calendarView;
        this.context = context;
    }


    @Override
    public void onClick(View v) {
        CalendarDay daySelected = calendarView.getSelectedDate();

        if (daySelected == null) {
            // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // 2. Chain together various setter methods to set the dialog characteristics
//                    builder.setMessage(R.string.dialog_message)
//                            .setTitle(R.string.dialog_title);
            builder.setMessage("Primero debe seleccionar una fecha")
                    .setTitle("Información");

            // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            AlertDialog dialog = builder.create();

            dialog.show();

            System.out.println("HOOOOOOOOOOOOOOLAAAAAAA");
        }
    }
}