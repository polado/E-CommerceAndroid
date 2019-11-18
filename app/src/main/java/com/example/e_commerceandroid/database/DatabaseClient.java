package com.example.e_commerceandroid.database;


import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient mInstance;
    private Context context;
    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;


        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "Favourites").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}