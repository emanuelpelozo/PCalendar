package com.android.pcalendar.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.pcalendar.controller.ButtonDateInteractionClickListener;
import com.android.pcalendar.database.MDatesDatabase;
import com.android.pcalendar.R;
import com.android.pcalendar.model.PCalculator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private MDatesDatabase database;
    private DecoratorMarksManager decoratorMarksManager;
    private TextView textViewDayCount;
    private LinearLayout button_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                MDatesDatabase.class, "mdates")
                .allowMainThreadQueries()
                .build();


        button_bar = findViewById(R.id.button_bar);
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
//        CharSequence[] months = {"Ene","Feb","Marzo","Abril","Mayo","JUn","H","H","H","H","H","H"};
//        calendarView.setTitleMonths(months);
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

        buttonEstimarCiclo.setOnClickListener(v -> {
            List<Integer> cycleOptions = IntStream
                    .rangeClosed(PCalculator.MIN_CYCLE_DURATION,PCalculator.MAX_CYCLE_DURATION)
                    .boxed().collect(Collectors.toList());
            List<Integer> periodOptions = IntStream
                    .rangeClosed(PCalculator.MIN_PERIOD_DURATION,PCalculator.MAX_PERIOD_DURATION)
                    .boxed().collect(Collectors.toList());

            SpinerDialog spinerDialog = new SpinerDialog(MainActivity.this, cycleOptions, periodOptions, new SpinerDialog.DialogListener() {
                @Override
                public void ready(int cycleElection, int periodElection) {
                    cyclePredictionDecoration(cycleElection, periodElection);
                    createBarCalendarEstimation();

                }

            });

            spinerDialog.show();

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

    private void cyclePredictionDecoration( int cycleDuration, int periodDuration){

        CalendarDay daySelected = calendarView.getSelectedDate();
        LocalDate startDate = daySelected.getDate();

        Context context = MainActivity.this;
        PCalculator pCalculator = new PCalculator(startDate);

        List<LocalDate> nextPeriod = pCalculator.getNextPeriodDates(cycleDuration,periodDuration);
        List<LocalDate> ovulationPeriod = pCalculator.getOvulationDatesFromDuration(cycleDuration);
        List<LocalDate> preMenstrualPeriod = pCalculator.getPreMenstrualDates(cycleDuration);
        List<LocalDate> actualMenstrualPeriod = pCalculator.getActualPeriodDates(periodDuration);

        this.decoratorMarksManager.addCalendarEstimationDecoration(context,nextPeriod,ovulationPeriod,preMenstrualPeriod,actualMenstrualPeriod);
    }


    private void createBarCalendarEstimation(){

        LinearLayout activity_main = findViewById(R.id.main_activity);

        button_bar.setVisibility(View.GONE);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.button_bar_calendar_estimation, activity_main,true);

        Button buttonLimpiarEstimacion = findViewById(R.id.button_limpiar_estimacion);
        buttonLimpiarEstimacion.setOnClickListener(v -> {

            activity_main.removeView(findViewById(R.id.button_bar_calendar_estimation));
            button_bar.setVisibility(View.VISIBLE);
            decoratorMarksManager.deleteCalendarEstimationDecoration();
        });

        Button buttonInfo = findViewById(R.id.button_info);

        buttonInfo.setOnClickListener(l->{

            InfoDialog infoDialog = new InfoDialog(MainActivity.this);
            infoDialog.show();
        });


    }
}
