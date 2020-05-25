package com.android.pcalendar.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.android.pcalendar.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class CycleEventDecorator implements DayViewDecorator {

    private final HashSet<LocalDate> dates;
    private Drawable drawable;

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

//        Drawable wrappedDrawable = DrawableCompat.wrap(this.drawable);
//        DrawableCompat.setTint(wrappedDrawable, this.color);
        view.setBackgroundDrawable(drawable);


    }
}
