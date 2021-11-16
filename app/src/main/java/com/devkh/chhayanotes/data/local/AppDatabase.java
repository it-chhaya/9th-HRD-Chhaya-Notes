package com.devkh.chhayanotes.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.devkh.chhayanotes.data.model.local.Note;
import com.devkh.chhayanotes.utils.AppConstants;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /* DAO Declaration */
    public abstract NoteDao noteDao();

    /* Singleton Instance */
    private static volatile AppDatabase INSTANCE;

    /* Init Instance */
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    AppDatabase.class, AppConstants.APP_DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
