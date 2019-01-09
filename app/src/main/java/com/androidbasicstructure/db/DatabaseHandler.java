package com.androidbasicstructure.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.androidbasicstructure.models.User;

/**
 * Created by Himangi on 8/1/19
 */
@Database(entities = { User.class }, version = 1)
public abstract class DatabaseHandler extends RoomDatabase {

    private static final String DB_NAME = "sampledatabase.db";
    private static volatile DatabaseHandler instance;

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static DatabaseHandler create(final Context context) {
        return Room.databaseBuilder(
                context,
                DatabaseHandler.class,
                DB_NAME)
                .allowMainThreadQueries().build();
    }

    public abstract UserDao getUserDao();
}