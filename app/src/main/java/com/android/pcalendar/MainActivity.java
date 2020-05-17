package com.android.pcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2010, 4, 3))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .commit();
    }
}
