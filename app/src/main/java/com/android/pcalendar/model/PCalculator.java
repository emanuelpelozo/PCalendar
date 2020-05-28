package com.android.pcalendar.model;

import android.os.Build;


import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PCalculator {

    static final int CONSTANT_OV = 14;
    static final int CONSTANT_PREV = 2;
    public static final int MIN_PERIOD_DURATION = 2;
    public static final int MAX_PERIOD_DURATION = 7;
    public static final int MIN_CYCLE_DURATION = 21;
    public static final int MAX_CYCLE_DURATION = 35;
    public static final int STD_CYCLE_DURATION = 28;
    public static final int STD_PERIOD_DURATION = 5;
    public static final int OVULATION_DURATION = 4;
    public static final int PREMENSTRUAL_DURATION = 2;
    private final LocalDate start;

    public PCalculator(LocalDate start){
        this.start = start;
    }

    public List<LocalDate> getNextPeriodDates(int cycleDuration, int periodDuration){

        List<LocalDate> list = new ArrayList<>();

        for (int i = 0; i < periodDuration; i++ ){

            long movAmount = cycleDuration + i;
            list.add(this.start.plusDays(movAmount));
        }

        return list;
    }

    public List<LocalDate> getActualPeriodDates(int periodDuration){

        List<LocalDate> list = new ArrayList<>();

        for (int i = 0; i < periodDuration; i++ ){

            int movAmount =  i;
            list.add(this.start.plusDays(movAmount));

        }

        return list;
    }


    public List<LocalDate> getOvulationDatesFromDuration(int cycleDuration){

        //The ovulation days starts 14 days before the next cycle start (CONSTANT_OV)
        return getDatesGoingBackFromNewCycle(cycleDuration, CONSTANT_OV ,OVULATION_DURATION);
    }

    public List<LocalDate> getPreMenstrualDates(int cycleDuration){

        //The pre menstrual days starts 2 days before the next cycle start (CONSTANT_PREV)
        return getDatesGoingBackFromNewCycle(cycleDuration, CONSTANT_PREV, PREMENSTRUAL_DURATION);
    }

    private List<LocalDate> getDatesGoingBackFromNewCycle(int cycleDuration, int amountRollBack, int daysCant){
        LocalDate nextPeriod = this.start.plusDays(cycleDuration );
        List<LocalDate> list = new ArrayList<>();

        for (int i = 0; i < daysCant; i++ ){

            int movAmount = amountRollBack - i;
            list.add(nextPeriod.minusDays(movAmount));
        }

        return list;
    }

}
