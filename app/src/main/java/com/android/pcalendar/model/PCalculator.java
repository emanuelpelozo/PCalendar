package com.android.pcalendar.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PCalculator {

    static final int CONSTANT_OV = -14;
    static final int CONSTANT_PREV = -2;
    public static final int CYCLE_DURATION = 28;
    public static final int PERIOD_DURATION = 5;
    public static final int OVULATION_DURATION = 4;
    public static final int PREMENSTRUAL_DURATION = CONSTANT_PREV*-1;
    private final Date start;

    public PCalculator(Date start){
        this.start = start;
    }

    public List<Date> getNextPeriodDates(int cycleDuration, int periodDuration){

        List<Date> list = new ArrayList<>();

        for (int i = 1; i <= periodDuration; i++ ){

            int movAmount = cycleDuration + i;
            list.add(getDateFrom(this.start, movAmount));
        }

        return list;
    }

    public List<Date> getActualPeriodDates(int periodDuration){

        List<Date> list = new ArrayList<>();

        for (int i = 0; i < periodDuration; i++ ){

            int movAmount =  i;
            list.add(getDateFrom(this.start, movAmount));
        }

        return list;
    }


    public List<Date> getOvulationDatesFromDuration(int cycleDuration){

        //The ovulation days starts 14 days before the next cycle start (CONSTANT_OV)
        return getDatesGoingBackFromNewCycle(cycleDuration, CONSTANT_OV ,OVULATION_DURATION);
    }

    public List<Date> getPreMenstrualDates(int cycleDuration){

        //The pre menstrual days starts 2 days before the next cycle start (CONSTANT_PREV)
        return getDatesGoingBackFromNewCycle(cycleDuration, CONSTANT_PREV, PREMENSTRUAL_DURATION);
    }

    private List<Date> getDatesGoingBackFromNewCycle(int cycleDuration, int amountRollBack, int daysCant){
        Date nextPeriod = getDateFrom(this.start, cycleDuration + 1 );
        List<Date> list = new ArrayList<>();

        for (int i = 0; i < daysCant; i++ ){

            int movAmount = amountRollBack + i;
            list.add(getDateFrom(nextPeriod, movAmount));
        }

        return list;
    }


    private Date getDateFrom(Date baseDate, int movAmount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);

        calendar.add(Calendar.DAY_OF_MONTH, movAmount);

        return calendar.getTime();
    }


}
