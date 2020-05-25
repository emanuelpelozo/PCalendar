package com.android.pcalendar.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MDate.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class MDatesDatabase extends RoomDatabase {

    public abstract MDateDao mDateDao();
}
