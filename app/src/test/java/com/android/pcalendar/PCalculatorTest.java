package com.android.pcalendar;

import com.android.pcalendar.model.PCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PCalculatorTest {

    private LocalDate start;
    private PCalculator pCalculator;



    @Before
    public void setUp(){

        //Date 3-3-2020

        this.start = LocalDate.of(2020,3,3);

        this.pCalculator = new PCalculator(start);
    }

    @Test
    public void getNextPeriodDateFromBaseDateReturnsSameCantOfDaysAsPeriodDuration(){
        int cycleDuration = 28;
        int periodDuration = 5;

        List<LocalDate> list = pCalculator.getNextPeriodDates(cycleDuration, periodDuration);

        Assert.assertEquals(list.size(), periodDuration);

    }

    @Test
    public void getNextPeriodDateFromBaseDateReturnsTheCorrectFirstDate(){
        int cycleDuration = 28;
        int periodDuration = 5;


        List<LocalDate> list = pCalculator.getNextPeriodDates(cycleDuration, periodDuration);

        //Base date (3-3-2020) + 28 days = (31-3-20)
        LocalDate expectedDate = LocalDate.of(2020,3,31);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getNextPeriodDateFromBaseDateReturnsTheCorrectDateSecuence(){
        int cycleDuration = 28;
        int periodDuration = 3;


        List<LocalDate> list = pCalculator.getNextPeriodDates(cycleDuration, periodDuration);

        //Base date (3-3-2020) + 28 days = (31-3-20)
        LocalDate expectedDate1 = LocalDate.of(2020,3,31);

        LocalDate expectedDate2 = expectedDate1.plusDays(1);

        LocalDate expectedDate3 = expectedDate2.plusDays(1);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));
        Assert.assertTrue(expectedDate3.equals(list.get(2)));

    }

    @Test
    public void getActualPeriodDatesFromBaseDateReturnsSameCantOfDaysAsPeriodDuration(){

        int periodDuration = 5;

        List<LocalDate> list = pCalculator.getActualPeriodDates( periodDuration);
        Assert.assertEquals(list.size(), periodDuration);
    }

    @Test
    public void getActualPeriodDatesFromBaseDateReturnsTheCorrectFirstDate(){

        int periodDuration = 5;

        List<LocalDate> list = pCalculator.getActualPeriodDates(periodDuration);

        //Base date (3-3-2020)
        LocalDate expectedDate = LocalDate.of(2020,3,3);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getActualPeriodDatesFromBaseDateReturnsTheCorrectDateSecuence(){
        int periodDuration = 3;


        List<LocalDate> list = pCalculator.getActualPeriodDates( periodDuration);

        //Base date (3-3-2020)
        LocalDate expectedDate1 = LocalDate.of(2020,3,3);

        //Base date (3-3-2020) + 1 = (4-3-20)
        LocalDate expectedDate2 = expectedDate1.plusDays(1);

        //Base date (3-3-2020) + 2 = (5-3-20)
        LocalDate expectedDate3 = expectedDate2.plusDays(1);

        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));
        Assert.assertTrue(expectedDate3.equals(list.get(2)));

    }

    @Test
    public void getOvulationDatesFromBaseDateReturnsSameCantOfDaysAsOvulationDuration(){
        int cycleDuration = 28;

        List<LocalDate> list = pCalculator.getOvulationDatesFromDuration(cycleDuration);

        Assert.assertEquals(list.size(), PCalculator.OVULATION_DURATION);

    }

    @Test
    public void getOvulationDatesFromBaseDateReturnsTheCorrectFirstDate(){
        int cycleDuration = 28;

        List<LocalDate> list = pCalculator.getOvulationDatesFromDuration(cycleDuration);

        //Base date (3-3-2020) + 28 days = (31-3-20)
        //First ovulation date = (31-3-20) - 14 days = (17-3-20)
        LocalDate expectedDate = LocalDate.of(2020,3,17);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getOvulationDatesFromBaseDateReturnsTheCorrectDateSecuence(){
        int cycleDuration = 28;

        List<LocalDate> list = pCalculator.getOvulationDatesFromDuration(cycleDuration);

        //Ovulation duration = 4 (PCALENDAR.OVULATION_DURATION)

        //Base date (3-3-2020) + 28 days = (31-3-20)
        //First ovulation date = (31-3-20) - 14 days = (17-3-20)
        LocalDate expectedDate1 = LocalDate.of(2020,3,17);

        //Second ovulation date = (31-3-20) - 13 days = (18-3-20)
        LocalDate expectedDate2 = expectedDate1.plusDays(1);

        //Second ovulation date = (31-3-20) - 12 days = (19-3-20)
        LocalDate expectedDate3 = expectedDate2.plusDays(1);

        //Second ovulation date = (31-3-20) - 13 days = (20-3-20)
        LocalDate expectedDate4 = expectedDate3.plusDays(1);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));
        Assert.assertTrue(expectedDate3.equals(list.get(2)));
        Assert.assertTrue(expectedDate4.equals(list.get(3)));

    }


    @Test
    public void getPreMenstrualDatesFromBaseDateReturnsSameCantOfDaysAsOvulationDuration(){
        int cycleDuration = 28;

        List<LocalDate> list = pCalculator.getPreMenstrualDates(cycleDuration);

        Assert.assertEquals(PCalculator.PREMENSTRUAL_DURATION, list.size());

    }

    @Test
    public void getPreMenstrualDatesFromBaseDateReturnsTheCorrectFirstDate(){
        int cycleDuration = 28;

        List<LocalDate> list = pCalculator.getPreMenstrualDates(cycleDuration);

        //Base date (3-3-2020) + 28 days = (31-3-20)
        //First pre menstrual date = (31-3-20) - 2 days = (29-3-20)
        LocalDate expectedDate = LocalDate.of(2020,3,29);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getPremenstrualDatesFromBaseDateReturnsTheCorrectDateSecuence(){
        int cycleDuration = 28;

        List<LocalDate> list = pCalculator.getPreMenstrualDates(cycleDuration);

        //Ovulation duration = 2 (PCALENDAR.PREMENSTRUAL_DURATION)

        //Base date (3-3-2020) + 28 days = (31-3-20)
        //First pre menstrual date = (31-3-20) - 2 days = (29-3-20)
       LocalDate expectedDate1 = LocalDate.of(2020,3,29);

        //Second pre menstrual date = (31-3-20) - 1 days = (30-3-20)
        LocalDate expectedDate2 = expectedDate1.plusDays(1);

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));

    }



}
