package com.android.pcalendar;

import com.android.pcalendar.model.PCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PCalculatorTest {

    private Date start;
    private Calendar cal;
    private PCalculator pCalculator;



    @Before
    public void setUp(){

        //Date 3-3-2020
        this.cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        this.start = cal.getTime();

        this.pCalculator = new PCalculator(start);
    }

    @Test
    public void getNextPeriodDateFromBaseDateReturnsSameCantOfDaysAsPeriodDuration(){
        int cycleDuration = 28;
        int periodDuration = 5;

        List<Date> list = pCalculator.getNextPeriodDates(cycleDuration, periodDuration);

        Assert.assertEquals(list.size(), periodDuration);

    }

    @Test
    public void getNextPeriodDateFromBaseDateReturnsTheCorrectFirstDate(){
        int cycleDuration = 28;
        int periodDuration = 5;


        List<Date> list = pCalculator.getNextPeriodDates(cycleDuration, periodDuration);

        //Base date (3-3-2020) + 28 days = (1-4-20)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate = cal.getTime();

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getNextPeriodDateFromBaseDateReturnsTheCorrectDateSecuence(){
        int cycleDuration = 28;
        int periodDuration = 3;


        List<Date> list = pCalculator.getNextPeriodDates(cycleDuration, periodDuration);

        //Base date (3-3-2020) + 28 days = (1-4-20)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate1 = cal.getTime();

        //Base date (3-3-2020) + 28 days = (2-4-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate2 = cal.getTime();

        //Base date (3-3-2020) + 28 days = (3-4-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate3 = cal.getTime();

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));
        Assert.assertTrue(expectedDate3.equals(list.get(2)));

    }

    @Test
    public void getActualPeriodDatesFromBaseDateReturnsSameCantOfDaysAsPeriodDuration(){

        int periodDuration = 5;

        List<Date> list = pCalculator.getActualPeriodDates( periodDuration);
        Assert.assertEquals(list.size(), periodDuration);
    }

    @Test
    public void getActualPeriodDatesFromBaseDateReturnsTheCorrectFirstDate(){

        int periodDuration = 5;

        List<Date> list = pCalculator.getActualPeriodDates(periodDuration);

        //Base date (3-3-2020)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date expectedDate = cal.getTime();

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getActualPeriodDatesFromBaseDateReturnsTheCorrectDateSecuence(){
        int periodDuration = 3;


        List<Date> list = pCalculator.getActualPeriodDates( periodDuration);

        //Base date (3-3-2020)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date expectedDate1 = cal.getTime();

        //Base date (3-3-2020) + 1 = (4-3-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate2 = cal.getTime();

        //Base date (3-3-2020) + 2 = (5-3-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate3 = cal.getTime();

        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));
        Assert.assertTrue(expectedDate3.equals(list.get(2)));

    }

    @Test
    public void getOvulationDatesFromBaseDateReturnsSameCantOfDaysAsOvulationDuration(){
        int cycleDuration = 28;

        List<Date> list = pCalculator.getOvulationDatesFromDuration(cycleDuration);

        Assert.assertEquals(list.size(), PCalculator.OVULATION_DURATION);

    }

    @Test
    public void getOvulationDatesFromBaseDateReturnsTheCorrectFirstDate(){
        int cycleDuration = 28;

        List<Date> list = pCalculator.getOvulationDatesFromDuration(cycleDuration);

        //Base date (3-3-2020) + 28 days = (1-4-20)
        //First ovulation date = (1-4-20) - 14 days = (18-3-20)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 18);
        Date expectedDate = cal.getTime();

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getOvulationDatesFromBaseDateReturnsTheCorrectDateSecuence(){
        int cycleDuration = 28;

        List<Date> list = pCalculator.getOvulationDatesFromDuration(cycleDuration);

        //Ovulation duration = 4 (PCALENDAR.OVULATION_DURATION)

        //Base date (3-3-2020) + 28 days = (1-4-20)
        //First ovulation date = (1-4-20) - 14 days = (18-3-20)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 18);
        Date expectedDate1 = cal.getTime();

        //Second ovulation date = (1-4-20) - 13 days = (19-3-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate2 = cal.getTime();

        //Third ovulation date = (1-4-20) - 12 days = (20-3-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate3 = cal.getTime();

        //Fourth ovulation date = (1-4-20) - 11 days = (21-3-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate4 = cal.getTime();


        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));
        Assert.assertTrue(expectedDate3.equals(list.get(2)));
        Assert.assertTrue(expectedDate4.equals(list.get(3)));

    }


    @Test
    public void getPreMenstrualDatesFromBaseDateReturnsSameCantOfDaysAsOvulationDuration(){
        int cycleDuration = 28;

        List<Date> list = pCalculator.getPreMenstrualDates(cycleDuration);

        Assert.assertEquals(PCalculator.PREMENSTRUAL_DURATION, list.size());

    }

    @Test
    public void getPreMenstrualDatesFromBaseDateReturnsTheCorrectFirstDate(){
        int cycleDuration = 28;

        List<Date> list = pCalculator.getPreMenstrualDates(cycleDuration);

        //Base date (3-3-2020) + 28 days = (1-4-20)
        //First pre menstrual date = (1-4-20) - 2 days = (30-3-20)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        Date expectedDate = cal.getTime();

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate.equals(list.get(0)));

    }

    @Test
    public void getPremenstrualDatesFromBaseDateReturnsTheCorrectDateSecuence(){
        int cycleDuration = 28;

        List<Date> list = pCalculator.getPreMenstrualDates(cycleDuration);

        //Ovulation duration = 2 (PCALENDAR.PREMENSTRUAL_DURATION)

        //Base date (3-3-2020) + 28 days = (1-4-20)
        //First pre menstrual date = (1-4-20) - 2 days = (30-3-20)
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        Date expectedDate1 = cal.getTime();

        //Second pre menstrual date = (1-4-20) - 2 days = (31-3-20)
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date expectedDate2 = cal.getTime();

        //First date from the list must be equals to the expected date
        Assert.assertTrue(expectedDate1.equals(list.get(0)));
        Assert.assertTrue(expectedDate2.equals(list.get(1)));

    }



}
