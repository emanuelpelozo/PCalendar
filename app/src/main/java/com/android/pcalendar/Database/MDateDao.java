package com.android.pcalendar.Database;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface MDateDao {

//    @Query("INSERT into mdates (day,month,year) VALUES (:day, :month, :year) ")
//    void addNewCycleStart(int day, int month, int year);

    @Query("INSERT into mdates (date) VALUES (:date)")
    void addNewCycleStart(Date date);

    @Query("SELECT date FROM mdates")
    List<Date>getAllMDates();

    @Query("DELETE FROM mdates WHERE date = (:date) ")
    void deleteCycleStartFrom(Date date);

    @Query("SELECT MAX(date) FROM mdates")
    Date getLastDateRegistered();
}
