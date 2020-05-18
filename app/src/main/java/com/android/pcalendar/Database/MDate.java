package com.android.pcalendar.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "mdates")
@TypeConverters(DateConverter.class)
public class MDate {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public Date date;
//
//    @ColumnInfo(name = "day")
//    public int day;
//
//    @ColumnInfo(name = "month")
//    public int month;
//
//    @ColumnInfo(name = "year")
//    public int year;



}
