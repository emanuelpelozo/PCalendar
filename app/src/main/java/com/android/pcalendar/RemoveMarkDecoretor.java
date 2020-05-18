package com.android.pcalendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class RemoveMarkDecoretor implements DayViewDecorator {

    private final CalendarDay day;
    private Drawable drawable;

    public RemoveMarkDecoretor(CalendarDay day, Activity context) {

        this.day = day;
        this.drawable = context.getDrawable(R.drawable.remove_mark_background);

    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return this.day.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}
