package com.android.pcalendar.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.pcalendar.controller.ButtonDateInteractionClickListener;
import com.android.pcalendar.database.MDatesDatabase;
import com.android.pcalendar.R;
import com.android.pcalendar.model.PCalculator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.Calendar;
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
        Button buttonEliminar = findViewById(R.id.button_eliminar_marca);
        Button buttonEstimarCiclo = findViewById(R.id.button_estimar_ciclo);
        calendarView = findViewById(R.id.calendarView);
        textViewDayCount = findViewById(R.id.textViewDayCount);

        this.decoratorMarksManager = new DecoratorMarksManager(calendarView);

        calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2000, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .commit();

        calendarView.setSelectedDate(LocalDate.now());
        calendarView.setSelectionColor(Color.parseColor("#6666ff"));

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

        buttonEstimarCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay daySelected = calendarView.getSelectedDate();
                LocalDate date = daySelected.getDate();

                cyclePredictionDecoration(date);

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

    private void cyclePredictionDecoration(LocalDate startDate){

        Context context = MainActivity.this;
        PCalculator pCalculator = new PCalculator(startDate);

        List<LocalDate> nextPeriod = pCalculator.getNextPeriodDates(
                PCalculator.CYCLE_DURATION,PCalculator.PERIOD_DURATION);
        calendarView.addDecorator(new CycleEventDecorator(nextPeriod,context.getDrawable(R.drawable.period_phase_indicator)));

        List<LocalDate> ovulationPeriod = pCalculator.getOvulationDatesFromDuration(PCalculator.CYCLE_DURATION);
        calendarView.addDecorator(new CycleEventDecorator(ovulationPeriod,context.getDrawable(R.drawable.ovulation_phase_indicator)));

        List<LocalDate> preMenstrualPeriod = pCalculator.getPreMenstrualDates(PCalculator.CYCLE_DURATION);
        calendarView.addDecorator(new CycleEventDecorator(preMenstrualPeriod,context.getDrawable(R.drawable.premenstrual_phase_indicator)));

        List<LocalDate> actualMenstrualPeriod = pCalculator.getActualPeriodDates(PCalculator.PERIOD_DURATION);
        calendarView.addDecorator(new CycleEventDecorator(actualMenstrualPeriod,context.getDrawable(R.drawable.period_phase_indicator)));

    }
}
