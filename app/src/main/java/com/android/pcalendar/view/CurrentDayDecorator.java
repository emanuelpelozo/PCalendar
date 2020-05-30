package com.android.pcalendar.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.android.pcalendar.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

public class CurrentDayDecorator implements DayViewDecorator {

    private LocalDate today;
    private Drawable drawable;
    private int color;

    public CurrentDayDecorator(LocalDate today, Activity context){
        this.today = today;
        this.drawable = context.getDrawable(R.drawable.today_circle_selector);
        this.color = ContextCompat.getColor(context, R.color.colorTodayMark);

    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return (today.getMonthValue() == day.getMonth()
                && today.getYear() == day.getYear()
                && today.getDayOfMonth() == day.getDay());
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new DotSpan(20, this.color));


    }
}
