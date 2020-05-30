package com.android.pcalendar.view;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorRes;

import com.android.pcalendar.R;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class DecoratorMarksManager {

    private DayViewDecorator lastDecorator;
    private MaterialCalendarView calendarView;
    private List<CycleEventDecorator> cycleEventDecorators;

    public DecoratorMarksManager(MaterialCalendarView calendarView){

        this.calendarView = calendarView;
        this.lastDecorator = null;
        this.cycleEventDecorators = new ArrayList<>();
    }

    public void addDecorator(DayViewDecorator newDecorator){
        calendarView.removeDecorator(lastDecorator);
        calendarView.addDecorator(newDecorator);
        lastDecorator = newDecorator;
    }

    public void addCalendarEstimationDecoration(Context context,
                                                List<LocalDate> nextPeriod,
                                                List<LocalDate> ovulationPeriod,
                                                List<LocalDate> preMenstrualPeriod,
                                                List<LocalDate> actualMenstrualPeriod){

        this.addCycleEventDecorator(new CycleEventDecorator(nextPeriod,context.getDrawable(R.drawable.period_phase_indicator)));
        this.addCycleEventDecorator(new CycleEventDecorator(ovulationPeriod,context.getDrawable(R.drawable.ovulation_phase_indicator)));
        this.addCycleEventDecorator(new CycleEventDecorator(preMenstrualPeriod,context.getDrawable(R.drawable.premenstrual_phase_indicator)));
        this.addCycleEventDecorator(new CycleEventDecorator(actualMenstrualPeriod,context.getDrawable(R.drawable.period_phase_indicator)));


    }

    private void addCycleEventDecorator(CycleEventDecorator decorator){
        calendarView.addDecorator(decorator);
        cycleEventDecorators.add(decorator);
    }

    public void deleteCalendarEstimationDecoration(){
        for (DayViewDecorator decorator: cycleEventDecorators) {
            calendarView.removeDecorator(decorator);
        }

    }
}
