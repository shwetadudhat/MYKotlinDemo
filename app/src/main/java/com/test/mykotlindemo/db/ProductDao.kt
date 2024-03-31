package com.test.mykotlindemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.mykotlindemo.Model.Product


@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(quotes: List<Product>)

    @Query("SELECT * FROM product")
    suspend fun getProductss() : List<Product>
}