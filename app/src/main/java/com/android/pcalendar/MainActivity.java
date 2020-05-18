package com.android.pcalendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Date> dates;
    private MDatesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                MDatesDatabase.class, "mdates")
                .allowMainThreadQueries()
                .build();

        Button buttonInicio = findViewById(R.id.button_marcar_inicio);
        Button buttonEliminar = findViewById(R.id.buttom_eliminar_marca);
        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2000, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .commit();

        dates = database.mDateDao().getAllMDates();
        materialCalendarView.addDecorator(new
                InicioCicloDecorator(Color.GREEN,dates, MainActivity.this));

        buttonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay daySelected = materialCalendarView.getSelectedDate();

                if (daySelected == null){
                    // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // 2. Chain together various setter methods to set the dialog characteristics
//                    builder.setMessage(R.string.dialog_message)
//                            .setTitle(R.string.dialog_title);
                    builder.setMessage("Primero debe seleccionar una fecha")
                            .setTitle("Información");


                    // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
                else {
                    Date date = new Date(daySelected.getYear(),daySelected.getMonth(),daySelected.getDay());

                    database.mDateDao().addNewCycleStart(date);

                    dates = database.mDateDao().getAllMDates();
                    materialCalendarView.addDecorator(new
                            InicioCicloDecorator(Color.GREEN,dates, MainActivity.this));
                }
            }
        });


        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay daySelected = materialCalendarView.getSelectedDate();

                if (daySelected == null){
                    // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // 2. Chain together various setter methods to set the dialog characteristics
//                    builder.setMessage(R.string.dialog_message)
//                            .setTitle(R.string.dialog_title);
                    builder.setMessage("Primero debe seleccionar una fecha")
                            .setTitle("Información");


                    // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }
                else {
                    Date date = new Date(daySelected.getYear(),daySelected.getMonth(),daySelected.getDay());

                    database.mDateDao().deleteCycleStartFrom(date);

                    dates = database.mDateDao().getAllMDates();

                    materialCalendarView.addDecorator(new RemoveMarkDecoretor(daySelected,MainActivity.this));
                    materialCalendarView.addDecorator(new
                            InicioCicloDecorator(Color.GREEN,dates, MainActivity.this));
                }
            }

        });
    }
}
