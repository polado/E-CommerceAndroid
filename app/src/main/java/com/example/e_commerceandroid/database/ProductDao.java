package com.example.e_commerceandroid.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_commerceandroid.models.ProductModel;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM productModel")
    Observable<List<ProductModel>> getAll();

    @Query("SELECT * FROM productModel WHERE id = (:productID)")
    List<ProductModel> getProduct(int productID);

    @Insert
    void insert(ProductModel product);

    @Delete
    void delete(ProductModel product);

    @Update
    void update(ProductModel product);
}
