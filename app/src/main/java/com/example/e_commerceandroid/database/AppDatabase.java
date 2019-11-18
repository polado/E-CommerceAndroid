package com.example.e_commerceandroid.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.e_commerceandroid.models.ProductModel;

@Database(entities = {ProductModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
