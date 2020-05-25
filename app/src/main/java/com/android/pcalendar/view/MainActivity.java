package com.android.pcalendar.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.pcalendar.controller.ButtonDateInteractionClickListener;
import com.android.pcalendar.database.MDatesDatabase;
import com.android.pcalendar.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.Date;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private MDatesDatabase database;
    private DecoratorMarksManager decoratorMarksManager;
    private TextView textViewDayCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                MDatesDatabase.class, "mdates")
                .allowMainThreadQueries()
                .build();

        Button buttonInicio =  findViewById(R.id.button_marcar_inicio);
        Button buttonEliminar = findViewById(R.id.buttom_eliminar_marca);
        calendarView = findViewById(R.id.calendarView);
        textViewDayCount = findViewById(R.id.textViewDayCount);

        this.decoratorMarksManager = new DecoratorMarksManager(calendarView);

        calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2000, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .commit();

        calendarView.setSelectedDate(LocalDate.now());

        this.updateView();

        buttonInicio.setOnClickListener(
                new ButtonDateInteractionClickListener(calendarView,MainActivity.this) {
                    @Override
                    public void onClick(View v) {
                        super.onClick(v);

                        CalendarDay daySelected = calendarView.getSelectedDate();
                        Date date = new Date(daySelected.getYear(), daySelected.getMonth(), daySelected.getDay());
                        database.mDateDao().addNewCycleStart(date);
                        updateView();
                    }

                });


        buttonEliminar.setOnClickListener(
                new ButtonDateInteractionClickListener(calendarView,MainActivity.this){
                    @Override
                    public void onClick(View v){
                        super.onClick(v);

                        CalendarDay daySelected = calendarView.getSelectedDate();
                        Date date = new Date(daySelected.getYear(),daySelected.getMonth(),daySelected.getDay());
                        database.mDateDao().deleteCycleStartFrom(date);
                        updateView();
                    }
                });

    }



    private void updateView(){

        LocalDate dateToday = LocalDate.now();
        calendarView.addDecorator(new CurrentDayDecorator(dateToday, MainActivity.this));

        List<Date> dates = database.mDateDao().getAllMDates();
        decoratorMarksManager.addDecorator(new CycleStartDecorator(dates, MainActivity.this));

        Date lastDate = database.mDateDao().getLastDateRegistered();
        textViewDayCount.setText(Integer.toString(daysFrom(lastDate)));
    }

    private int daysFrom(Date date){
        if(date == null){ return 0; }

        CalendarDay today = CalendarDay.today();
        Date todayDate = new Date(today.getYear(),today.getMonth(),today.getDay());

        return (int)( (todayDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
    }

}
