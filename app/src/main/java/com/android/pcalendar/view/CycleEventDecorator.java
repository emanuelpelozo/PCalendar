package com.android.pcalendar.view;

import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.LocalDate;

import java.util.Collection;
import java.util.HashSet;

public class CycleEventDecorator implements DayViewDecorator {

    private final HashSet<LocalDate> dates;
    private Drawable drawable;
    private int color;

    public CycleEventDecorator (Collection<LocalDate> dates, Drawable drawableIndicator) {

        this.dates = new HashSet<>(dates);
        this.drawable = drawableIndicator;

    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        LocalDate desiredDay = day.getDate();
        return dates.contains(desiredDay);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.setBackgroundDrawable(drawable);


    }
}
