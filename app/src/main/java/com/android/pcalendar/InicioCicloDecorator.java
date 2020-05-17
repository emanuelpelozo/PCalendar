package com.android.pcalendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class InicioCicloDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<Date> dates;
    private Drawable drawable;

    public InicioCicloDecorator(int color, Collection<Date> dates, Activity context) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.drawable = context.getDrawable(R.drawable.inicio_ciclo_selector);

        System.out.println(dates.toString());

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        Date desiredDay = new Date(day.getYear(),day.getMonth(),day.getDay());

        return dates.contains(desiredDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new DotSpan(5,color));
//        view.addSpan(new ForegroundColorSpan(color));
        view.setBackgroundDrawable(drawable);


    }

}
