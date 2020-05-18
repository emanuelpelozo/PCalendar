package com.android.pcalendar.view;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class DecoratorMarksManager {

    private DayViewDecorator lastDecorator;
    private MaterialCalendarView calendarView;
    public DecoratorMarksManager(MaterialCalendarView calendarView){

        this.calendarView = calendarView;
        this.lastDecorator = null;
    }

    public void addDecorator(DayViewDecorator newDecorator){
        calendarView.removeDecorator(lastDecorator);
        calendarView.addDecorator(newDecorator);
        lastDecorator = newDecorator;
    }
}
