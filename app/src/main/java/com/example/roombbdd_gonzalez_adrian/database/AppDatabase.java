package com.example.roombbdd_gonzalez_adrian.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.roombbdd_gonzalez_adrian.dao.TasquesDao;
import com.example.roombbdd_gonzalez_adrian.dao.TagsDao;
import com.example.roombbdd_gonzalez_adrian.dao.TascaTagDao;
import com.example.roombbdd_gonzalez_adrian.entities.Tasques;
import com.example.roombbdd_gonzalez_adrian.entities.Tags;
import com.example.roombbdd_gonzalez_adrian.entities.TascaTag;

@Database(
    entities = {Tasques.class, Tags.class, TascaTag.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TasquesDao tasquesDao();
    public abstract TagsDao tagsDao();
    public abstract TascaTagDao tascaTagDao();
    
    private static volatile AppDatabase INSTANCE;
    
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    )
                    .allowMainThreadQueries()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
